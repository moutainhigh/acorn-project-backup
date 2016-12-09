package com.chinadrtv.erp.tc.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.inventory.PlubasInfo;
import com.chinadrtv.erp.model.inventory.ProductSuiteType;
import com.chinadrtv.erp.tc.core.dao.PluSplitDao;
import com.chinadrtv.erp.tc.core.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.tc.core.service.OrderSkuSplitService;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */
@Service("com.chinadrtv.erp.tc.core.service.impl.OrderSkuSplitServiceImpl")
public class OrderSkuSplitServiceImpl extends GenericServiceImpl implements OrderSkuSplitService {

    private static final Logger logger = LoggerFactory.getLogger(OrderSkuSplitServiceImpl.class);

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;

    @Autowired
    private PluSplitDao pluSplitDao;

    @Override
    protected GenericDao getGenericDao() {
        return null;
    }

    /**
     * 根据12位SCM编码返回IAGENT订单商品
     * @param scmId
     * @return  Map<String, Object>
     * @throws Exception
     */
    public Map<String, Object> findIagentProduct(String scmId) throws Exception {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        PlubasInfo pluInfo = pluSplitDao.getIagentProduct(scmId);

        if (pluInfo != null) {
            String ncFreeName = pluSplitDao.getProductTypeId(pluInfo.getNccode(), pluInfo.getNcfreename());
            resultMap.put("prodid", pluInfo.getNccode().trim());
            resultMap.put("prodtype", ncFreeName);
            resultMap.put("prodname", pluInfo.getPluname());
        } else {
            throw new Exception("查询PlubasInfo对象为NULL，由于12位商品编码" + scmId + "不存在");
        }
        return resultMap;
    }

    /**
     * 拆分商品为12位商品编码，获取订单详细列表
     * 逻辑规则：判断订单商品是否为套装商品,如果为套装商品则拆分，非套装商品则正常处理
     *
     * @param orderhist
     * @return Map
     */
    public List<Map<String, Object>> orderSkuSplit(Order orderhist) throws Exception {

        List<Map<String, Object>> sdList = new ArrayList<Map<String, Object>>(); //返回的拆分后商品数据
        Set<OrderDetail> orderdetSet = orderhist.getOrderdets();   //传入订单明细数据
        Double productMoney = 0D;   //商品金额
        if (orderdetSet.size() > 0) {
            for (OrderDetail orderdet : orderdetSet) {
                //获取商品类型
                String dsc = pluSplitDao.searchProductType(orderdet.getProducttype());
                //商品定位
                PlubasInfo plubaseinfo = pluSplitDao.searchPlubasInfoByNccodeAndNcfreename(orderdet.getProdid().trim(), dsc);
                if (plubaseinfo == null) {
                    throw new Exception("查询PlubasInfo对象为NULL，由于nccode" + orderdet.getProdid().trim()
                            + " 或者 ncfreename id " + orderdet.getProducttype().trim() + " 不匹配");
                }
                //判断是否为套装商品
                if (plubaseinfo.getIssuiteplu().equals("1")) {
                    //处理套装商品拆分
                    List<ProductSuiteType> productSuiteTypes = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(plubaseinfo.getPlucode());

                    if (productSuiteTypes.size() > 0) {     //存在组合商品
                        //订单商品数量
                        Integer spNum = null == orderdet.getSpnum() ? 0 : orderdet.getSpnum();
                        Integer upNum = null == orderdet.getUpnum() ? 0 : orderdet.getUpnum();
                        Long orderNum = new Long(spNum + upNum);
                        //订单商品单价
                        Double upPrice = null == orderdet.getUprice() ? 0 : orderdet.getUprice().doubleValue();
                        Double spPrice = null == orderdet.getSprice() ? 0 : orderdet.getSprice().doubleValue();
                        //订单总金额
                        Double orderAmt = (upPrice * upNum) + (spNum * spPrice);
                        productMoney += orderAmt;
                        //取子商品成本
                        Double orderCost = 0D;
                        for (Integer idx = 0; idx < productSuiteTypes.size(); idx++) {
                            Double prodcost = null == productSuiteTypes.get(idx).getProdCost() ? 0D : productSuiteTypes.get(idx).getProdCost().doubleValue();
                            Integer prodnum = null == productSuiteTypes.get(idx).getProdNum() ? 0 : productSuiteTypes.get(idx).getProdNum().intValue();
                            orderCost = orderCost + (prodcost * prodnum * orderNum);
                        }

                        if (orderCost > orderAmt) {
                            // 负毛利直接按子商品成本分摊  ,调用定义方法
                            this.lossProduct(productSuiteTypes, orderAmt, orderNum, orderCost,orderdet, sdList);
                        } else {
                            //按子商品的成本和算毛利   ，调用定义方法
                            this.profitProduct(productSuiteTypes, orderAmt, orderNum, orderCost,orderdet, sdList);
                        }
                    } else {
                        throw new Exception("PlubasInfo对象" + plubaseinfo.getPlucode() + "为套装商品，但没有配置套装关系。 nccode： "
                                + orderdet.getProdid().trim() + "ncfreename id：" + orderdet.getProducttype().trim());
                    }
                } else {
                    //非套装商品, 单品
                    Integer spNum = null == orderdet.getSpnum() ? 0 : orderdet.getSpnum();
                    Integer upNum = null == orderdet.getUpnum() ? 0 : orderdet.getUpnum();
                    Double upPrice = null == orderdet.getUprice() ? 0 : orderdet.getUprice().doubleValue();
                    Double spPrice = null == orderdet.getSprice() ? 0 : orderdet.getSprice().doubleValue();
                    productMoney += (upPrice * upNum) + (spPrice * spNum);
                    this.onlyProduct(plubaseinfo, orderdet, sdList);
                }
            }
        }
        //添加判断商品拆分后的汇总值是否跟原订单明细的商品金额一致
        Boolean b = this.checkProduct(sdList,productMoney);
        if(!b){
           throw new Exception("商品拆分后的汇总值跟原订单明细的商品金额不一致，请核实！");
        }
        return sdList;
    }
    //添加判断商品拆分后的汇总值是否跟原订单明细的商品金额一致
    private Boolean checkProduct(List<Map<String, Object>> sdList,Double money)
    {   Double sum = 0D;
        if(sdList.size() > 0)
        {
            for(int i=0;i<sdList.size();i++)
            {
                Double u = Double.parseDouble(sdList.get(i).get("unitprice").toString());
                Double q = Double.parseDouble(sdList.get(i).get("qty").toString());
                //数据保留小数问题
                sum = sum + u*q;
            }
            //System.out.println("原订单总金额: "+money);
            //System.out.println("拆分总金额: " + sum);
            BigDecimal bg = new BigDecimal(sum);
            Double temp = bg.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

            BigDecimal bm = new BigDecimal(money);
            Double m = bm.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

            if(temp.equals(m))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * 拆分套装商品  负毛利直接按子商品成本分摊
     *
     * @param productSuiteTypes
     * @param OrderAmt
     * @param orderNum
     * @param orderCost
     * @param orderdet
     * @param sdList
     */
    private void lossProduct(List<ProductSuiteType> productSuiteTypes, Double OrderAmt, Long orderNum, Double orderCost,OrderDetail orderdet, List<Map<String, Object>> sdList) {
        String itemId = "";
        String itemDesc = "";
        Long totalQty = 0L;
        Double unitPrice = 0D;
        Long ruid = 0L;
        int cnt = 0;
        // 负毛利直接按子商品成本分摊
        for (Integer idx = 0; idx < productSuiteTypes.size(); idx++) {
            Map<String, Object> resultMap = new HashMap<String, Object>();

            itemId = productSuiteTypes.get(idx).getProdScmId();
            itemDesc = productSuiteTypes.get(idx).getProdName();
            Integer prodnum = null == productSuiteTypes.get(idx).getProdNum() ? 0 : productSuiteTypes.get(idx).getProdNum().intValue();
            totalQty = prodnum * orderNum;

            if (OrderAmt == 0D) {
                unitPrice = 0D;
            } else {
                //按照比例计算单价
                Double prodcost = null == productSuiteTypes.get(idx).getProdCost() ? 0 : productSuiteTypes.get(idx).getProdCost().doubleValue();

                Double d = OrderAmt * ((prodcost * prodnum * orderNum) / orderCost);
                BigDecimal bg = new BigDecimal(d);
                d = bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

                unitPrice = d / (prodnum * orderNum);
            }
            //返回单品Ruid
            PlubasInfo p = pluSplitDao.getIagentProduct(itemId);
            if(p != null){
                ruid = p.getRuid();
            }
            if(cnt == 0)
            {  //返回运费
                resultMap.put("postfee",orderdet.getFreight());
                cnt++;
            }
            if(orderdet.getSoldwith().equals("3"))
            {  //判断是否为赠品
                resultMap.put("freeflag",1);
            } else {
                resultMap.put("freeflag",0);
            }
            resultMap.put("ruid",ruid);
            resultMap.put("plucode", itemId);
            resultMap.put("pluname", itemDesc);
            resultMap.put("qty", totalQty);
            resultMap.put("unitprice", unitPrice);
            resultMap.put("linenum", orderdet.getOrderdetid());
            resultMap.put("status",p!=null?p.getStatus():null);
            sdList.add(resultMap);
        }
    }

    /**
     * 组合商品拆分  按子商品的成本和算毛利
     *
     * @param productSuiteTypes
     * @param orderAmt
     * @param orderNum
     * @param orderCost
     * @param orderdet
     * @param sdList
     */
    private void profitProduct(List<ProductSuiteType> productSuiteTypes, Double orderAmt, Long orderNum, Double orderCost, OrderDetail orderdet, List<Map<String, Object>> sdList) {
        String itemId = "";
        String itemDesc = "";
        Long totalQty = 0L;
        Double unitPrice = 0D;
        Long ruid = 0L;
        //按子商品的成本和算毛利
        int cnt = 0;
        for (Integer idx = 0; idx < productSuiteTypes.size(); idx++) {
            Map<String, Object> resultMap = new HashMap<String, Object>();

            itemId = productSuiteTypes.get(idx).getProdScmId();
            itemDesc = productSuiteTypes.get(idx).getProdName();
            Integer prodnum = null == productSuiteTypes.get(idx).getProdNum() ? 0 : productSuiteTypes.get(idx).getProdNum().intValue();
            totalQty = prodnum * orderNum;

            if (orderAmt == 0D) {
                unitPrice = 0D;
            } else {
                //按照比例计算单价
                Integer ratint = null == productSuiteTypes.get(idx).getRat() ? 0 : productSuiteTypes.get(idx).getRat().intValue();
                Double rat = Double.parseDouble(ratint.toString());
                Double prodcost = null == productSuiteTypes.get(idx).getProdCost() ? 0 : productSuiteTypes.get(idx).getProdCost().doubleValue();
                //修改算法问题，应该取每个商品成本 李宇 2013-03-10
                double d = (orderAmt - orderCost) * (new Double(rat) / 100) + prodcost * prodnum * orderNum;
                BigDecimal bg = new BigDecimal(d);
                d = bg.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

                unitPrice = d / (prodnum * orderNum);
            }
            //返回单品Ruid
            PlubasInfo p = pluSplitDao.getIagentProduct(itemId);
            if(p != null){
                ruid = p.getRuid();
            }
            if (cnt == 0)
            {  //返回运费
                resultMap.put("postfee",orderdet.getFreight());
                cnt++;
            }
            if(orderdet.getSoldwith().equals("3"))
            {   //返回是否为赠品
                resultMap.put("freeflag",1);
            } else {
                resultMap.put("freeflag",0);
            }
            resultMap.put("ruid",ruid);
            resultMap.put("plucode", itemId);
            resultMap.put("pluname", itemDesc);
            resultMap.put("qty", totalQty);
            resultMap.put("linenum", orderdet.getOrderdetid());
            resultMap.put("unitprice", unitPrice);
            resultMap.put("status",p!=null?p.getStatus():null);

            sdList.add(resultMap);
        }

    }

    /**
     * 非套装商品, 单品
     *
     * @param plubaseinfo
     * @param orderdet
     * @param sdList
     */
    private void onlyProduct(PlubasInfo plubaseinfo, OrderDetail orderdet, List<Map<String, Object>> sdList) {
        String itemId = "";
        String itemDesc = "";
        Long totalQty = 0L;
        Double unitPrice = 0D;
        Long ruid = 0L;
        Double productMoney = 0D;   //商品金额
        ruid = plubaseinfo.getRuid();
        itemId = plubaseinfo.getPlucode();
        itemDesc = plubaseinfo.getPluname();
        Integer spNum = null == orderdet.getSpnum() ? 0 : orderdet.getSpnum();
        Integer upNum = null == orderdet.getUpnum() ? 0 : orderdet.getUpnum();
        totalQty = new Long(spNum + upNum);

        Double upPrice = null == orderdet.getUprice() ? 0 : orderdet.getUprice().doubleValue();
        Double spPrice = null == orderdet.getSprice() ? 0 : orderdet.getSprice().doubleValue();
        productMoney = upPrice * upNum + spPrice * spNum;
        unitPrice = productMoney / totalQty;

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("postfee",orderdet.getFreight());    //运费
        resultMap.put("ruid",ruid);
        if(orderdet.getSoldwith().equals("3"))
        {   //返回是否为赠品
            resultMap.put("freeflag",1);
        } else {
            resultMap.put("freeflag",0);
        }
        resultMap.put("plucode", itemId);
        resultMap.put("pluname", itemDesc);
        resultMap.put("qty", totalQty);
        resultMap.put("unitprice", unitPrice);
        resultMap.put("linenum", orderdet.getOrderdetid());
        resultMap.put("status",plubaseinfo.getStatus());

        sdList.add(resultMap);
    }

    /**
     * 商品拆分 OrderDetail    2013-03-11
     * @param orderdet
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> orderSkuSplit(OrderDetail orderdet) throws Exception {

        List<Map<String, Object>> sdList = new ArrayList<Map<String, Object>>(); //返回的拆分后商品数据

        Double productMoney = 0D;   //商品金额
        if (orderdet != null) {
           //获取商品类型
           String dsc = pluSplitDao.searchProductType(orderdet.getProducttype());
           //商品定位
           PlubasInfo plubaseinfo = pluSplitDao.searchPlubasInfoByNccodeAndNcfreename(orderdet.getProdid().trim(), dsc);
           if (plubaseinfo == null) {
                throw new Exception("查询PlubasInfo对象为NULL，由于nccode" + orderdet.getProdid().trim()
                     + " 或者 ncfreename id " + orderdet.getProducttype().trim() + " 不匹配");
           }
           //判断是否为套装商品
           if (plubaseinfo.getIssuiteplu().equals("1")) {
               //处理套装商品拆分
               List<ProductSuiteType> productSuiteTypes = pluSplitDao.searchProductSuiteTypeByProdSuiteScmId(plubaseinfo.getPlucode());

               if (productSuiteTypes.size() > 0) {     //存在组合商品
                   //订单商品数量
                        Integer spNum = null == orderdet.getSpnum() ? 0 : orderdet.getSpnum();
                        Integer upNum = null == orderdet.getUpnum() ? 0 : orderdet.getUpnum();
                        Long orderNum = new Long(spNum + upNum);
                        //订单商品单价
                        Double upPrice = null == orderdet.getUprice() ? 0 : orderdet.getUprice().doubleValue();
                        Double spPrice = null == orderdet.getSprice() ? 0 : orderdet.getSprice().doubleValue();
                        //订单总金额
                        Double orderAmt = upPrice * upNum + spNum * spPrice;
                        productMoney = orderAmt;
                        //取子商品成本
                        Double orderCost = 0D;
                        for (Integer idx = 0; idx < productSuiteTypes.size(); idx++) {
                            Double prodcost = null == productSuiteTypes.get(idx).getProdCost() ? 0D : productSuiteTypes.get(idx).getProdCost().doubleValue();
                            Integer prodnum = null == productSuiteTypes.get(idx).getProdNum() ? 0 : productSuiteTypes.get(idx).getProdNum().intValue();
                            orderCost = orderCost + (prodcost * prodnum * orderNum);
                        }

                        if (orderCost > orderAmt) {
                            // 负毛利直接按子商品成本分摊  ,调用定义方法
                            this.lossProduct(productSuiteTypes, orderAmt, orderNum, orderCost, orderdet, sdList);
                        } else {
                            //按子商品的成本和算毛利   ，调用定义方法
                            String lineNum = orderdet.getOrderdetid();
                            BigDecimal freight = orderdet.getFreight(); //产品运费
                            this.profitProduct(productSuiteTypes, orderAmt, orderNum, orderCost, orderdet, sdList);
                        }
                    } else {
                        throw new Exception("PlubasInfo对象" + plubaseinfo.getPlucode() + "为套装商品，但没有配置套装关系。 nccode： "
                                + orderdet.getProdid().trim() + "ncfreename id：" + orderdet.getProducttype().trim());
                    }
                } else {
                    //非套装商品, 单品
                   Integer spNum = null == orderdet.getSpnum() ? 0 : orderdet.getSpnum();
                   Integer upNum = null == orderdet.getUpnum() ? 0 : orderdet.getUpnum();
                   Double upPrice = null == orderdet.getUprice() ? 0 : orderdet.getUprice().doubleValue();
                   Double spPrice = null == orderdet.getSprice() ? 0 : orderdet.getSprice().doubleValue();
                   productMoney = (upPrice * upNum) + (spPrice * spNum);
                   this.onlyProduct(plubaseinfo, orderdet, sdList);
                }

        }
        //添加判断商品拆分后的汇总值是否跟原订单明细的商品金额一致
        Boolean b = this.checkProduct(sdList,productMoney);
        if(!b){
            throw new Exception("商品拆分后的汇总值跟原订单明细的商品金额不一致，请核实！");
        }
        return sdList;
    }
}
