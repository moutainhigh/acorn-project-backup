package com.chinadrtv.chama.service.impl;


import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.container.ExecutionContext;
import org.milyn.payload.JavaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.chama.bean.PreTradeDetailmap;
import com.chinadrtv.chama.bean.PreTrademap;
import com.chinadrtv.chama.service.ChamaCheckService;
import com.chinadrtv.chama.service.ChamaService;
import com.chinadrtv.model.constant.TradeSourceConstants;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-1
 * Time: 下午5:23
 * To change this template use File | Settings | File Templates.
 */
@Service("chamaCheckService")
public class ChamaCheckServiceImpl implements ChamaCheckService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ChamaCheckServiceImpl.class);

    private Smooks smooks = null;
    public ChamaCheckServiceImpl(){
        try{
            smooks = new Smooks("smooks/smookschama.xml");
        }catch (Exception ex){
            logger.error("smooks init error:",ex);
        }
    }
    @Autowired
     private ChamaService chamaService;

    /**
     * smooks转换xml文件到PreTrademap对象中
     * @param txml
     * @return
     */
    public List<PreTrademap> getOrderInfo(String txml) {
        if(smooks == null){
            logger.info("smooks is null");
            return null;
        }
        try{
            txml=txml.substring(1,txml.length()-1);
            //Smooks smooks = new Smooks("smooks/smookschama.xml");
            JavaResult javaResult = new JavaResult();
            ExecutionContext executionContext =smooks.createExecutionContext();
            Source source=new StreamSource(new StringReader(txml));
            // Filter the input message to the JavaResult...
            smooks.filterSource(executionContext,source,javaResult);
            //Extract the Order bean from the JavaResult using the beanId...
            List<PreTrademap> listpretrade = (List<PreTrademap>) javaResult.getBean("orderlist");
            return listpretrade;
        }catch (Exception e){
            logger.error("getOrderInfo error:",e);
            return null;
        }
    }

    /**
     * 转换listmap的值到ListPretare
     * @param trademapList
     * @return
     */
    public List<PreTradeDto> getPretrademapto(List<PreTrademap> trademapList) {

        List<PreTradeDto> tradeList=new LinkedList<PreTradeDto>();
        //转换本地mode到erp.mode
        for(int i=0;i<trademapList.size();i++){
            PreTradeDto pretrade=new PreTradeDto();
            pretrade.setTradeId(trademapList.get(i).getTradeId());
            pretrade.setOpsTradeId(trademapList.get(i).getOpsTradeId());
            pretrade.setCrdt(trademapList.get(i).getCrdt());
            pretrade.setOutCrdt(trademapList.get(i).getCrdt());
            pretrade.setTradeType(trademapList.get(i).getTradeType().split("ORD_", 0)[1]);
            pretrade.setTradeFrom(trademapList.get(i).getTradeFrom());
            pretrade.setBuyerNick(trademapList.get(i).getBuyerNick());
            pretrade.setReceiverName(trademapList.get(i).getReceiverName());
            pretrade.setReceiverMobile(trademapList.get(i).getReceiverMobile());
            pretrade.setReceiverPhone(trademapList.get(i).getReceiverPhone());
            pretrade.setReceiverProvince(trademapList.get(i).getReceiverProvince() == null ? "" : trademapList.get(i).getReceiverProvince());
            pretrade.setReceiverCity(trademapList.get(i).getReceiverCity() == null ? "" : trademapList.get(i).getReceiverCity());
            pretrade.setReceiverCounty(trademapList.get(i).getReceiverCounty() == null ? "" : trademapList.get(i).getReceiverCounty());
            pretrade.setReceiverAddress(trademapList.get(i).getReceiverAddress() == null ? "" : trademapList.get(i).getReceiverAddress());
            pretrade.setReceiverPostCode(trademapList.get(i).getReceiverPostCode() == null ? "" : trademapList.get(i).getReceiverPostCode());
            pretrade.setInvoiceComment(trademapList.get(i).getInvoiceComment());
            pretrade.setInvoiceTitle(trademapList.get(i).getInvoiceTitle());
            pretrade.setTmsCode(trademapList.get(i).getTmsCode());
            pretrade.setComisssionFee(Double.parseDouble(trademapList.get(i).getCommissionFee()));
            pretrade.setRetailerCommissionFee(Double.parseDouble(trademapList.get(i).getRetailerCommissionFee()));
            pretrade.setPlateformCommissionFee(Double.parseDouble(trademapList.get(i).getPlatformCommissionFee()));
            pretrade.setCreditFee(Double.parseDouble(trademapList.get(i).getCreditFee()));
            pretrade.setAdvFee(Double.parseDouble(trademapList.get(i).getAdvFee()));
            pretrade.setJhsFee(Double.parseDouble(trademapList.get(i).getJhsFee()));
            pretrade.setShippingFee(Double.parseDouble(trademapList.get(i).getShippingFee()));
            pretrade.setRevenue(Double.parseDouble(trademapList.get(i).getRevenue()));
            BigDecimal payment = new BigDecimal(trademapList.get(i).getPayment());
            pretrade.setPayment(Double.parseDouble(payment.toEngineeringString()));
            List<PreTradeDetail> preTradeDetailHashSet= new ArrayList<PreTradeDetail>();
            for(PreTradeDetailmap detailmap:trademapList.get(i).getPreTradeDetails()){
                PreTradeDetail preTradeDetail=new PreTradeDetail();
                preTradeDetail.setOutSkuId(detailmap.getOutSkuId());
                preTradeDetail.setQty(detailmap.getQty());
                preTradeDetail.setIsActive(true);
                preTradeDetail.setPrice(Double.parseDouble(detailmap.getPrice()));
                preTradeDetail.setPayment(Double.parseDouble(detailmap.getPrice())*detailmap.getQty());
                preTradeDetailHashSet.add(preTradeDetail);
            }

            pretrade.setPreTradeDetails(preTradeDetailHashSet);
            tradeList.add(pretrade);
        }
        return  tradeList;
    }

    /**
     * 消息转换记录数据
     * @param customerId
     * @param trademapList
     * @return
     */
    public PreTradeLotDto routeTradeInfo(String customerId, List<PreTrademap> trademapList) {
        try{
            List<PreTradeDto> tradeList=this.getPretrademapto(trademapList);

            PreTradeLotDto lot = new PreTradeLotDto();
            lot.setCrdt(new Date());
            lot.setValidCount(Long.parseLong(String.valueOf(tradeList.size())));
            lot.setErrCount(Long.parseLong("0"));
            lot.setTotalCount(Long.parseLong(String.valueOf(tradeList.size())));
            lot.setStatus(Long.parseLong("0"));  //未处理
            for(PreTradeDto trade : tradeList){
                trade.setSourceId(TradeSourceConstants.CHAMA_SOURCE_ID);
                trade.setCustomerId(customerId);
                for(PreTradeDetail tradeDetail : trade.getPreTradeDetails()){
                    tradeDetail.setTradeId(trade.getTradeId());
                    // 特殊处理：如果有自由项商品，取自由项商品ID，否则取商品ID
                    if (tradeDetail.getOutSkuId() == null) {
                        tradeDetail.setOutSkuId(tradeDetail.getOutItemId());
                    }
                    if (tradeDetail.getSkuId() == null) {
                        tradeDetail.setSkuId(tradeDetail.getItemId());
                    }
                }
                lot.getPreTrades().add(trade);
            }
            return lot;
        }catch (Exception e){
            logger.error("routeTradeInfo error:"+e.getMessage());
            return null;
        }
    }

    /**
     * 检测当前导入的茶马订单信息是否正确
     * @param tradeList
     * @return
     */
    public List<String> orderFeedback(List<PreTrademap> tradeList) {
        List<String> liststr = new LinkedList<String>();
        List<String> listerror = this.checkChama(tradeList);
        if (listerror.size() != 0) {
            StringBuffer response = new StringBuffer();
            response.append("<?xml version='1.0' encoding='UTF-8'?>");
            response.append("<ops_trades_response>");
            response.append("<ops_trade_id>" + listerror.get(1).toString() + "</ops_trade_id>");
            response.append("<agent_trade_id>mgc</agent_trade_id>");
            response.append("<result>" + ("013".equals(listerror.get(0))?"true":"false") +"</result>");
            response.append("<message_code>" + listerror.get(0).toString() + "</message_code>");
            response.append("<message>" + listerror.get(3).toString() + "</message>");
            response.append("</ops_trades_response>");
            liststr.add(0, listerror.get(0).toString());
            liststr.add(1, response.toString());
        }
        return liststr;
    }

    /**
     * 检查茶马订单的信息
     * @param tradeList
     * @return
     */
    public List<String> checkChama(List<PreTrademap> tradeList) {
        Double payment = 0.00;
        Double price = 0.00;
        List<String> liststr = new LinkedList<String>();
        for (PreTrademap preTrade : tradeList) {
            //客户名称长度
            if (preTrade.getReceiverName() == null || preTrade.getReceiverName().length() < 2) {
                liststr.add(0, "001");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "客户名称无效(长度小于2)");
                return liststr;
            }

            //地址长度无效
            if (preTrade.getReceiverAddress() == null || preTrade.getReceiverAddress().length() < 5) {
                liststr.add(0, "003");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "地址长度无效(应该大于5，小于128)");
                return liststr;
            }

            //无效省份地址
            if (preTrade.getReceiverProvince()==null || "".equals(preTrade.getReceiverProvince())) {
                liststr.add(0, "009");
                liststr.add(1, preTrade.getReceiverProvince());
                liststr.add(2, "");
                liststr.add(3, "无效省份地址");
                return liststr;
            }
            //无效城市地址
            if (preTrade.getReceiverCity()==null || "".equals(preTrade.getReceiverCity())) {
                liststr.add(0, "010");
                liststr.add(1, preTrade.getReceiverCity());
                liststr.add(2, "");
                liststr.add(3, "无效城市地址");
                return liststr;
            }

            //无效区县地址(目前未见校验)
            if (false) {
                liststr.add(0, "011");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "无效区县地址");
                return liststr;
            }

            if (preTrade.getTradeId() == null || "".equals(preTrade.getTradeId())){
                liststr.add(0, "115");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "网店订单Id为空");
                return liststr;
            }

            if (preTrade.getOpsTradeId() == null || "".equals(preTrade.getOpsTradeId())){
                liststr.add(0, "115");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "茶马订单Id为空");
                return liststr;
            }

            if (preTrade.getTradeType() == null || !preTrade.getTradeType().startsWith("ORD_")) {
                liststr.add(0, "116");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "retailer_id必须以ORD_开头");
                return liststr;
            }

            if (preTrade.getReceiverMobile() == null || "".equals(preTrade.getReceiverMobile())
                    && (preTrade.getReceiverPhone() == null || "".equals(preTrade.getReceiverPhone()))) {
                liststr.add(0, "117");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "客户手机或电话都为空");
                return liststr;
            }

            if (preTrade.getReceiverPostCode() == null || preTrade.getReceiverPostCode().length() < 6) {
                liststr.add(0, "118");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "客户邮编为空");
                return liststr;
            }

            if (preTrade.getCrdt() == null) {
                liststr.add(0, "119");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "订单付款时间为空");
                return liststr;
            }

            //订单重复
            int rowid = chamaService.getPreTradeByTradeId(preTrade.getTradeId(), preTrade.getOpsTradeId());//this.getPreTradeByTradeId(preTrade.getTradeId(), preTrade.getOpsTradeId());
            if (rowid > 0) {
                liststr.add(0, "002");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "订单重复, 该订单已经被导入过");
                return liststr;
            }

            //判断指定送货公司是否存在
            if (!chamaService.checkCompanyCode(preTrade.getTmsCode())) {
                liststr.add(0, "114");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "指定送货公司不存在");
                return liststr;
            }

            for (PreTradeDetailmap predetail : preTrade.getPreTradeDetails()) {
                try {
                    BigDecimal pr = new BigDecimal(predetail.getPrice());
                    BigDecimal qt = new BigDecimal(predetail.getQty());
                    price += Double.parseDouble(pr.multiply(qt).toEngineeringString());
                } catch (Exception e) {
                    liststr.add(0, "005");
                    liststr.add(1, preTrade.getTradeId());
                    liststr.add(2, "");
                    liststr.add(3, "无效商品单价或数量");
                    return liststr;
                }

                //商品明细数量不能为0
                if (predetail.getQty() == 0) {
                    liststr.add(0, "004");
                    liststr.add(1, preTrade.getTradeId());
                    liststr.add(2, "");
                    liststr.add(3, preTrade.getReceiverAddress().length() + "商品明细数量不能为0");
                    return liststr;
                }
            }

            //无效商品总价
            try {
                payment = Double.parseDouble(preTrade.getPayment()) - Double.parseDouble(preTrade.getShippingFee());
            } catch (Exception e) {
                liststr.add(0, "006");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "无效商品总价");
                return liststr;
            }

            //订单总价和商品明细价格合计不相等
            if (!payment.toString().equals(price.toString())) {
                liststr.add(0, "007");
                liststr.add(1, preTrade.getTradeId());
                liststr.add(2, "");
                liststr.add(3, "订单总价和商品明细价格合计不相等");
                return liststr;
            }

            //产品或套件编号不存在
            for (PreTradeDetailmap predetail : preTrade.getPreTradeDetails()) {
                if (!chamaService.checkSkuCode(predetail.getOutSkuId())) {
                    liststr.add(0, "008");
                    liststr.add(1, preTrade.getTradeId());
                    liststr.add(2, "");
                    liststr.add(3, "产品或套件编号不存在");
                    return liststr;
                }
            }

        }
        //订单导入成功
        liststr.add(0, "013");
        liststr.add(1, "");
        liststr.add(2, "");
        liststr.add(3, "订单导入成功");
        return liststr;
    }
}
