package com.chinadrtv.erp.tc.test;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import com.chinadrtv.erp.tc.core.dao.ShipmentHeaderDao;
import com.chinadrtv.erp.test.SpringTest;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-6
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class ProcessSplitTests extends SpringTest {

    @Autowired
    private ShipmentHeaderDao shipmentHeaderDao;
    //@Autowired
    //private PlubasInfoDao plubasInfoDao;
   /* @Autowired
    private TempOrderdetDao tempOrderdetDao;*/
    @Autowired
    private OrderhistDao orderhistDao;

    private DataSource dataSource;
    @Autowired
    @Required
    @Qualifier("dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   // @Test
    public void productSplit()
    {
       /* System.out.println("商品拆分单元测试*************");
        Orderhist orderhist = orderhistDao.getOrderHistByOrderid("997700222");

        List<Map<String, Object>> sdList = new ArrayList<Map<String, Object>>(); //返回数据
        Set<Orderdet> orderdetSet = orderhist.getOrderdets();   //传入数据

        if (orderdetSet.size() > 0) {
            List<PlubasInfo> plubasinfos = new ArrayList<PlubasInfo>();

            for (Orderdet orderdet : orderdetSet) {
                //获取商品类型
                String dsc = plubasInfoDao.searchProductType(orderdet.getProducttype());
                //商品定位
               // List<PlubasInfo> tempList  = plubasInfoDao.searchPlubasinfoByNccodeAndNcfreename(orderdet.getProdid(),dsc);
                //plubasinfos.addAll(tempList);

            }
            if(plubasinfos.size() > 0)
            {
                for(int i=0;i<plubasinfos.size();i++)
                {   //查询套装商品
                    //TODO:查询为空时怎么办？
                    if(plubasinfos.get(i).getIssuiteplu().equals("1"))
                    {


                    List<ProductSuiteType> productSuiteTypes = plubasInfoDao.searchProductSuiteTypeByProdSuiteScmId(plubasinfos.get(i).getPlucode());
                    if(productSuiteTypes.size() > 0)
                    {   //存在组合商品
                        List<Orderdet> orderdets = null;
                        for(int j=0;j<productSuiteTypes.size();j++)
                        {    //获取商品明细
                            orderdets = tempOrderdetDao.searchOrderByProdid(productSuiteTypes.get(j).getProdSuiteId());
                            if(orderdets.size() > 0)
                            {
                                for(int k=0;k<orderdets.size();k++)
                                {
                                    Integer spNum = null == orderdets.get(k).getSpnum() ? 0 : orderdets.get(k).getSpnum();
                                    Integer upNum = null == orderdets.get(k).getUpnum() ? 0 : orderdets.get(k).getUpnum();
                                    Long num = new Long(spNum + upNum);
                                    Double upPrice = null == orderdets.get(k).getUprice() ? 0 : orderdets.get(k).getUprice()
                                            .doubleValue();
                                    Double spPrice = null == orderdets.get(k).getSprice() ? 0 : orderdets.get(k).getSprice()
                                            .doubleValue();

                                    Double amt = upPrice * upNum + spNum * spPrice;
                                    //判断组合商品毛利
                                    String itemId = "";
                                    String itemDesc = "";
                                    Long totalQty = 0L;
                                    Double unitPrice = 0D;

                                    Double pronum = Double.parseDouble(String.valueOf(productSuiteTypes.get(j).getProdNum()));
                                    Double avgcsprc = plubasinfos.get(i).getAvgcsprc();
                                    Double v_csamt =  pronum*avgcsprc;

                                    if (v_csamt > amt) { // 负毛利直接按子商品成本分摊

                                        double sk = (amt *productSuiteTypes.get(j).getProdNum()  * plubasinfos.get(i).getAvgcsprc())
                                                / (plubasinfos.get(i).getAvgcsprc() * productSuiteTypes.get(j).getProdNum());
                                        double slprc = sk / (num * productSuiteTypes.get(j).getProdNum()); // 直接按子商品的成本分摊

                                        totalQty = num * productSuiteTypes.get(j).getProdNum();
                                        unitPrice = slprc;
                                        itemId = productSuiteTypes.get(j).getProdSuiteScmId();
                                        itemDesc = productSuiteTypes.get(j).getProdSuiteName();
                                    } else { // 按子商品的成本和算毛利
                                        double sk = (plubasinfos.get(j).getAvgcsprc() * productSuiteTypes.get(j).getProdNum() * num)
                                                + (amt - (plubasinfos.get(j).getAvgcsprc() * productSuiteTypes.get(j).getProdNum() * num)) * 2
                                                / 100;
                                        double slprc = sk / (num * productSuiteTypes.get(j).getProdNum()); // 按子商品的成本和算毛利

                                        totalQty = num * productSuiteTypes.get(j).getProdNum();
                                        unitPrice = slprc;
                                        itemId = productSuiteTypes.get(j).getProdSuiteScmId();
                                        itemDesc = productSuiteTypes.get(j).getProdSuiteName();
                                    }
                                    Map<String, Object> resultMap = new HashMap<String, Object>();
                                    resultMap.put("plucode", itemId);
                                    resultMap.put("pluname", itemDesc);
                                    resultMap.put("qty", totalQty);

                                    if (unitPrice.isNaN()) {
                                        unitPrice = 0D;
                                    }

                                    resultMap.put("unitprice", unitPrice);
                                    sdList.add(resultMap);   //返回组合商品拆分结果
                                }
                            }
                        }
                    }
                    }
                    else
                    {   //单品信息
                        //单品信息
                        String itemId = "";
                        String itemDesc = "";
                        Long totalQty = 0L;
                        Double unitPrice = 0D;


                            for(Orderdet orderdet:orderdetSet)
                            {
                                itemId = plubasinfos.get(i).getPlucode();
                                itemDesc = plubasinfos.get(i).getPluname();
                                Integer spNum = null == orderdet.getSpnum() ? 0 : orderdet.getSpnum();
                                Integer upNum = null == orderdet.getUpnum() ? 0 : orderdet.getUpnum();
                                Long num = new Long(spNum + upNum);
                                totalQty = num;
                                Double d1 = 0.0;
                                if (orderdet.getUprice().toString().equals("") || orderdet.getUpnum().toString().equals("")) {
                                    d1 = 0.0;
                                } else {
                                    d1 = Double.parseDouble(orderdet.getUprice().toString())
                                            * Double.parseDouble(orderdet.getUpnum().toString());
                                }
                                Double d2 = 0.0;
                                if (orderdet.getSprice().toString().equals("") || orderdet.getSpnum().toString().equals("")) {
                                    d2 = 0.0;
                                } else {
                                    d2 = Double.parseDouble(orderdet.getSprice().toString())
                                            * Double.parseDouble(orderdet.getSpnum().toString());
                                }
                                unitPrice = (d1 + d2) / num;
                                Map<String, Object> resultMap = new HashMap<String, Object>();
                                resultMap.put("plucode", itemId);
                                resultMap.put("pluname", itemDesc);
                                resultMap.put("qty", totalQty);
                                if (unitPrice.isNaN()) {
                                    unitPrice = 0D;
                                }
                                resultMap.put("unitprice", unitPrice);
                                sdList.add(resultMap);
                            }

                    }

                }

            }
        }*/
    }
}
