package com.chinadrtv.jingdong.service.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.milyn.Smooks;
import org.milyn.payload.JavaResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.OrderImportJDService;
import com.chinadrtv.model.constant.TradeSourceConstants;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeImportService;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.order.OrderInfo;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.domain.order.OrderSopPrintData;
import com.jd.open.api.sdk.request.order.OrderFbpGetRequest;
import com.jd.open.api.sdk.request.order.OrderFbpSearchRequest;
import com.jd.open.api.sdk.request.order.OrderGetRequest;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.request.order.OrderSopPrintDataGetRequest;
import com.jd.open.api.sdk.response.order.OrderFbpGetResponse;
import com.jd.open.api.sdk.response.order.OrderFbpSearchResponse;
import com.jd.open.api.sdk.response.order.OrderGetResponse;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.jd.open.api.sdk.response.order.OrderSopPrintDataGetResponse;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-8
 * Time: 下午2:37
 * To change this template use File | Settings | File Templates.
 */
@Service("OrderImportJDService")
public class OrderImportJDServiceImpl implements OrderImportJDService {
	
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportJDServiceImpl.class);
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String ORDER_REQUEST_SEARCH_FIELDS = "order_id" ;
    private final String ORDERR_EQUEST_FIELDS = "order_id,vender_id,pay_type,order_total_price,order_payment,order_seller_price,freight_price,seller_discount,order_state,order_state_remark,delivery_type,invoice_info,order_remark,order_start_time,order_end_time,consignee_info,item_info_list,coupon_detail_list" ;
    private final String SMOOKS_CONFIG_FILE = "smooks/smooks-order-jingdong.xml";
    private final String SMOOKS_BEAN_ORDERLIST = "orderlist" ;

    private Smooks smooks=null;
    public OrderImportJDServiceImpl() throws Exception{
        try{
            smooks = new Smooks(SMOOKS_CONFIG_FILE);
        }catch (Exception ex){
            logger.error("smooks OrderImportJDServiceImpl error:", ex);
            throw ex;
        }
    }
    @Autowired
    private PreTradeImportService preTradeLotService;

    /**
     * 调用前置订单服务保存订单信息
     * @param jingdongOrderConfigList
     * @param startDate
     * @param endDate
     */
    public void importPreOrdersInfo(List<JingdongOrderConfig> jingdongOrderConfigList, Date startDate, Date endDate) throws Exception{
		// 获取订单信息
		for (JingdongOrderConfig jdorderconfig : jingdongOrderConfigList) {
			List<PreTradeDto> preTradeDtoList = new ArrayList<PreTradeDto>();
			try {
				// 获取时间段内所有订单信息
				List<String> trades = this.getOrderList(startDate, endDate, jdorderconfig);

				for (String strXml : trades) {
					List<PreTradeDto> preTrades1 = this.json2PreTrade(strXml);
					for (PreTradeDto preTradeDto : preTrades1) {
						preTradeDtoList.add(preTradeDto);
					}
				}
				if (preTradeDtoList != null && !preTradeDtoList.isEmpty()) {
					// 更新发票信息
					this.updateOrderInvoice(preTradeDtoList, jdorderconfig);
					// 消息路由保存前置订单信息
					PreTradeLotDto preTradeLotDto = this.routeTradeInfo(preTradeDtoList, jdorderconfig);
					preTradeLotService.importPretrades(preTradeLotDto);
				}
			} catch (Exception e) {
				logger.error("service importPreOrdersInfo error:", e);
				throw e;
			}
		}

    }

     //获取时间段内所有订单信息
    private List<String> getOrderList(Date startTime, Date endTime, JingdongOrderConfig jdConfig) throws Exception {

        List<String> trades = new ArrayList<String>();
        String strXml = null;

        JdClient client = new DefaultJdClient(jdConfig.getUrl(),jdConfig.getSessionKey(), jdConfig.getAppkey(),jdConfig.getAppSecret());

        OrderSearchRequest request = new OrderSearchRequest();
        request.setStartDate(new SimpleDateFormat(DATE_FORMAT).format(startTime));
        request.setEndDate(new SimpleDateFormat(DATE_FORMAT).format(endTime));
        request.setOrderState(jdConfig.getOrderState());
        request.setPageSize(String.valueOf(jdConfig.getPageSize()));

        request.setOptionalFields(ORDER_REQUEST_SEARCH_FIELDS);

        int orderTotal = 0;
        int curPage = jdConfig.getPage();
        OrderSearchResponse response = null;
        do {
            request.setPage(String.valueOf(curPage));
            response = client.execute(request);
            //如果返回成功
            if ("0".equals(response.getCode())) {
                if (orderTotal == 0) {
                    orderTotal = response.getOrderInfoResult().getOrderTotal();
                }
                List<OrderSearchInfo> orderSearchInfos = response.getOrderInfoResult().getOrderInfoList();
                //逐个获取订单详细信息
                if (orderSearchInfos != null && !orderSearchInfos.isEmpty()) {
                    for (OrderSearchInfo orderInfo : orderSearchInfos) {
                        strXml = this.getOrderInfo(client, orderInfo.getOrderId(), jdConfig);
                        if (strXml != null){
                            trades.add(strXml);
                        }
                    }
                }
            } else {
	            logger.info("Import order error: " + jdConfig);
                throw new Exception(response.getCode() + "/" + response.getZhDesc());
            }

            curPage++;
        }while ((curPage - 1) * jdConfig.getPageSize() < orderTotal);

        return trades;
    }
    //请求获取京东订单json信息
    private String getOrderInfo(JdClient client, String orderId, JingdongOrderConfig jdConfig) throws Exception {
        String strXml = null;
        if (client == null) {
            client = new DefaultJdClient(jdConfig.getUrl(),jdConfig.getSessionKey(), jdConfig.getAppkey(), jdConfig.getAppSecret());
        }
        OrderGetRequest request = new OrderGetRequest();
        request.setOrderId(orderId);
        request.setOptionalFields(ORDERR_EQUEST_FIELDS);

        OrderGetResponse response = null;

        response = client.execute(request);
        if ("0".equals(response.getCode())) {
            OrderInfo orderInfo = response.getOrderDetailInfo().getOrderInfo();
            if (orderInfo != null) {
                if (jdConfig.getOrderState() != null || "".equals(jdConfig.getOrderState()) || jdConfig.getOrderState().equals(orderInfo.getOrderState()))
                    strXml = response.getMsg();
            }
        }

        return strXml;
    }
    private String getOrderInfo(String orderId, JingdongOrderConfig jdConfig) throws Exception {
        return getOrderInfo(null, orderId, jdConfig);
    }

    //转换json-->PreTrade
    private List<PreTradeDto> json2PreTrade(String strJson) throws Exception {
        List<PreTradeDto> preTrades = new ArrayList<PreTradeDto>() ;
        //Smooks smooks = null;
        if(smooks == null){
            logger.info("smooks is null");
            return null;
        }
        try {
            //smooks = new Smooks(SMOOKS_CONFIG_FILE);
            Source source = new StreamSource(new StringReader(strJson));
            JavaResult javaResult = new JavaResult();
            smooks.filterSource(source, javaResult);
            preTrades = (List<PreTradeDto>) javaResult.getBean(SMOOKS_BEAN_ORDERLIST);
        }catch (Exception ex){
            throw ex;
        }
        return preTrades ;
    }
    //更新发票信息
    private void updateOrderInvoice(List<PreTradeDto> preTradeList,JingdongOrderConfig jdConfig) throws Exception {
        OrderSopPrintData orderSopPrintData = null;
        String invoiceComment = "";

        JdClient client = new DefaultJdClient(jdConfig.getUrl(), jdConfig.getSessionKey(), jdConfig.getAppkey(), jdConfig.getAppSecret());
        for (int i = 0; i < preTradeList.size(); i++) {
            invoiceComment = null;
            if ("不需要开具发票".equals(preTradeList.get(i).getInvoiceTitle())) {
                invoiceComment = "0" ;
            }
            else{
                orderSopPrintData = getOrderSopPrintData(client, preTradeList.get(i).getTradeId(),jdConfig);
                if (orderSopPrintData != null) {
                    //普通发票、增值税发票）、发票抬头（个人、公司）
                    if (orderSopPrintData.getInvoiceType() != null) {
                        if ("普通发票".equals(orderSopPrintData.getInvoiceType())) {
                            invoiceComment = "1";
                        } else if ("增值税发票".equals(orderSopPrintData.getInvoiceType())) {
                            invoiceComment = "2";
                        } else
                            invoiceComment = "0";
                    }
                    preTradeList.get(i).setInvoiceTitle(orderSopPrintData.getInvoiceTitle());
                }
            }
            preTradeList.get(i).setInvoiceComment(invoiceComment);
        }
    }
    private OrderSopPrintData getOrderSopPrintData(JdClient client, String orderId,JingdongOrderConfig jdConfig) throws Exception {
        if (client == null) {
            client = new DefaultJdClient(jdConfig.getUrl(), jdConfig.getSessionKey(), jdConfig.getAppkey(), jdConfig.getAppSecret());
        }
        OrderSopPrintDataGetRequest request = new OrderSopPrintDataGetRequest();
        request.setOrderId(orderId);
        OrderSopPrintDataGetResponse response = client.execute(request);
        if ("0".equals(response.getCode())) {
            return response.getApiOrderPrintData();
        } else {
            throw new Exception(response.getCode() + "/" + response.getZhDesc());
        }
    }

    // 记录转换时的消息 订单信息存储表的list集合
    private PreTradeLotDto routeTradeInfo(List<PreTradeDto> tradeList,JingdongOrderConfig jdConfig) {
        PreTradeLotDto lot = new PreTradeLotDto();
        lot.setCrdt(new Date());
        lot.setValidCount(Long.parseLong(String.valueOf(tradeList.size())));
        lot.setErrCount(Long.parseLong("0"));
        lot.setTotalCount(Long.parseLong(String.valueOf(tradeList.size())));
        lot.setStatus(Long.parseLong("0"));  //未处理

        for (PreTradeDto trade : tradeList) {
            trade.setSourceId(TradeSourceConstants.BUY360_SOURCE_ID);
            trade.setCustomerId(jdConfig.getCustomerId());
            trade.setTradeType(jdConfig.getTradeType());
            trade.setTmsCode(jdConfig.getTmsCode());
            trade.setTradeFrom(jdConfig.getTmsName());
            for (PreTradeDetail tradeDetail : trade.getPreTradeDetails()) {
                tradeDetail.setTradeId(trade.getTradeId());
                // 特殊处理：如果有自由项商品，取自由项商品ID，否则取商品ID
                if (tradeDetail.getOutSkuId() == null) {
                    tradeDetail.setOutSkuId(tradeDetail.getOutItemId());
                }
                if (tradeDetail.getSkuId() == null) {
                    tradeDetail.setSkuId(tradeDetail.getItemId());
                }
            }
            //trade.setPreTradeLot(lot);
            lot.getPreTrades().add(trade);
        }
        return lot;
    }

	/** 
	 * <p>Title: importFBPOrder</p>
	 * <p>Description: </p>
	 * @param fbpList
	 * @param startDate
	 * @param endDate
	 * @throws Exception 
	 * @see com.chinadrtv.jingdong.service.OrderImportJDService#importFBPOrder(java.util.List, java.util.Date, java.util.Date)
	 */ 
	@Override
	public void importFBPOrder(List<JingdongOrderConfig> fbpList, Date startDate, Date endDate) throws Exception {
	   
        for(JingdongOrderConfig jdorderconfig : fbpList){
            //获取订单信息
            List<PreTradeDto> preTradeDtoList = new ArrayList<PreTradeDto>();
            
            try{
                //获取时间段内所有订单信息
                List<String> trades = this.fetchFBPOrderList(startDate,endDate,jdorderconfig);

                for(String strXml : trades){
                    List<PreTradeDto> preTrades1 = this.json2FbpPreTrade(strXml);
                    for (PreTradeDto preTradeDto : preTrades1){
                        preTradeDtoList.add(preTradeDto);
                    }
                }
                if(preTradeDtoList != null && !preTradeDtoList.isEmpty()){
                    //更新发票信息
                    //this.updateOrderInvoice(preTradeDtoList,jdorderconfig);
                    //消息路由保存前置订单信息
                    PreTradeLotDto preTradeLotDto = this.routeFbpTradeInfo(preTradeDtoList,jdorderconfig);
                    preTradeLotService.importPretrades(preTradeLotDto);
                }
            }catch (Exception e){
                logger.error("service importPreOrdersInfo error:",e);
                throw e;
            }

        }
	}
	
	 //获取时间段内所有订单信息
    private List<String> fetchFBPOrderList(Date startTime, Date endTime, JingdongOrderConfig jdConfig) throws Exception {

        List<String> trades = new ArrayList<String>();
        String strXml = null;

        JdClient client = new DefaultJdClient(jdConfig.getUrl(),jdConfig.getSessionKey(), jdConfig.getAppkey(),jdConfig.getAppSecret());

        OrderFbpSearchRequest  request = new OrderFbpSearchRequest ();
        request.setStartDate(new SimpleDateFormat(DATE_FORMAT).format(startTime));
        request.setEndDate(new SimpleDateFormat(DATE_FORMAT).format(endTime));
        //request.setOrderState(jdConfig.getOrderState());
        request.setPageSize(String.valueOf(jdConfig.getPageSize()));

        request.setOptionalFields(ORDER_REQUEST_SEARCH_FIELDS);

        int orderTotal = 0;
        int curPage = jdConfig.getPage();
        OrderFbpSearchResponse  response = null;
        do {
            request.setPage(String.valueOf(curPage));
            response = client.execute(request);
            //如果返回成功
            if ("0".equals(response.getCode())) {
                if (orderTotal == 0) {
                    orderTotal = response.getOrderInfoResult().getOrderTotal();
                }
                List<OrderSearchInfo> orderSearchInfos = response.getOrderInfoResult().getOrderInfoList();
                //逐个获取订单详细信息
                if (orderSearchInfos != null && !orderSearchInfos.isEmpty()) {
                    for (OrderSearchInfo orderInfo : orderSearchInfos) {
                        strXml = this.getFBPOrderInfo(client, orderInfo.getOrderId(), jdConfig);
                        if (strXml != null){
                            trades.add(strXml);
                        }
                    }
                }
            } else {
                throw new Exception(response.getCode() + "/" + response.getZhDesc());
            }

            curPage++;
        }while ((curPage - 1) * jdConfig.getPageSize() < orderTotal);

        return trades;
    }
    
    
    //请求获取京东订单json信息
    private String getFBPOrderInfo(JdClient client, String orderId, JingdongOrderConfig jdConfig) throws Exception {
        String strXml = null;
        if (client == null) {
            client = new DefaultJdClient(jdConfig.getUrl(),jdConfig.getSessionKey(), jdConfig.getAppkey(), jdConfig.getAppSecret());
        }
        
        OrderFbpGetRequest request=new OrderFbpGetRequest();

        request.setOrderId(orderId); 
        request.setOptionalFields(ORDERR_EQUEST_FIELDS);

        OrderFbpGetResponse response=client.execute(request);
        response = client.execute(request);
        if ("0".equals(response.getCode())) {
            OrderInfo orderInfo = response.getOrderDetailInfo().getOrderInfo();
            if (orderInfo != null) {
                if (jdConfig.getOrderState() != null || "".equals(jdConfig.getOrderState()) || jdConfig.getOrderState().equals(orderInfo.getOrderState()))
                    strXml = response.getMsg();
            }
        }

        return strXml;
    }
    
    
    //转换json-->PreTrade
    private List<PreTradeDto> json2FbpPreTrade(String strJson) throws Exception {
        List<PreTradeDto> preTrades = new ArrayList<PreTradeDto>() ;
        
        if(smooks == null){
            logger.info("smooks is null");
            return null;
        }
        
        try {
            Source source = new StreamSource(new StringReader(strJson));
            JavaResult javaResult = new JavaResult();
            smooks.filterSource(source, javaResult);
            preTrades = (List<PreTradeDto>) javaResult.getBean("fbpOrderlist");
        }catch (Exception ex){
            throw ex;
        }
        return preTrades ;
    }
    
    private PreTradeLotDto routeFbpTradeInfo(List<PreTradeDto> tradeList,JingdongOrderConfig jdConfig) {
        PreTradeLotDto lot = new PreTradeLotDto();
        lot.setCrdt(new Date());
        lot.setValidCount(Long.parseLong(String.valueOf(tradeList.size())));
        lot.setErrCount(Long.parseLong("0"));
        lot.setTotalCount(Long.parseLong(String.valueOf(tradeList.size())));
        lot.setStatus(Long.parseLong("0"));  //未处理

        for (PreTradeDto trade : tradeList) {
            trade.setSourceId(TradeSourceConstants.RETAIL_STATISTICS_SOURCE_ID);
            trade.setCustomerId(jdConfig.getCustomerId());
            trade.setTradeType(jdConfig.getTradeType());
            trade.setTmsCode(jdConfig.getTmsCode());
            trade.setTradeFrom(jdConfig.getTmsName());
            
            trade.setInvoiceTitle("");
            trade.setInvoiceComment("");
            
            String province = trade.getReceiverProvince();
            
            if(province.equals("北京") || province.equals("上海") || province.equals("天津") || province.equals("重庆")) {
            	String county = trade.getReceiverCity();
            	trade.setReceiverCity(province + "市");
            	trade.setReceiverCounty(county);
            }
            
            for (PreTradeDetail tradeDetail : trade.getPreTradeDetails()) {
                tradeDetail.setTradeId(trade.getTradeId());
                // 特殊处理：如果有自由项商品，取自由项商品ID，否则取商品ID
                if (tradeDetail.getOutSkuId() == null) {
                    tradeDetail.setOutSkuId(tradeDetail.getOutItemId());
                }
                if (tradeDetail.getSkuId() == null) {
                    tradeDetail.setSkuId(tradeDetail.getItemId());
                }
            }
            //trade.setPreTradeLot(lot);
            lot.getPreTrades().add(trade);
        }
        return lot;
    }
}
