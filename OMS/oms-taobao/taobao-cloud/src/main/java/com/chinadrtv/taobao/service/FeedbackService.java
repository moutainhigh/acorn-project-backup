/*
 * @(#)FeedbackService.java 1.0 2014-7-1下午2:08:47
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.service;

import java.util.List;

import org.slf4j.Logger;

import com.chinadrtv.taobao.common.dal.model.TradeFeedback;
import com.chinadrtv.taobao.common.dal.model.TradeFeedbackDetail;
import com.chinadrtv.taobao.controller.RawTradeFeedbackController;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.model.TradeStatusModel;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.LogisticsOrderCreateRequest;
import com.taobao.api.request.LogisticsOrderShengxianConfirmRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.response.LogisticsOrderCreateResponse;
import com.taobao.api.response.LogisticsOrderShengxianConfirmResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-7-1 下午2:08:47 
 * 
 */
public class FeedbackService {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RawTradeFeedbackController.class);

	/**
	 * <p></p>
	 * @param tradeStatusModel
	 * @return
	 */
	public void rawTradeFeedback(TradeStatusModel tradeStatusModel) throws Exception {
		
		logger.info("Feedbacking the raw trade.");

		//TradeFeedback tradeFeedback = tradeStatusModel.getTradefeedback();
		
		String status = this.queryTradeStatus(tradeStatusModel);
		
		if (!"WAIT_BUYER_CONFIRM_GOODS".equals(status) 
				&& !"TRADE_BUYER_SIGNED".equals(status)
				&& !"TRADE_FINISHED".equals(status) 
				&& !"TRADE_CLOSED".equals(status)
				&& !"TRADE_CLOSED_BY_TAOBAO".equals(status) 
				&& !"ALL_WAIT_PAY".equals(status)
				&& !"ALL_CLOSED".equals(status)) {

			//淘系订单不用创建rawTrade
			//Long tradeId = this.createRawTrade(tradeStatusModel);
			//if(-1 != tradeId && String.valueOf(tradeId).equals(tradeFeedback.getTradeId())) {
				this.confirmTrade(tradeStatusModel);
			//}
		}
        
	}

	/**
	 * <p></p>
	 * @param tradeStatusModel
	 * @throws Exception 
	 */
	private String confirmTrade(TradeStatusModel tradeStatusModel) throws Exception {
		
		TaobaoOrderConfig config = tradeStatusModel.getTaobaoOrderConfig();
		TradeFeedback tradefeedback = tradeStatusModel.getTradefeedback();
		
		TaobaoClient client = new DefaultTaobaoClient(config.getUrl(), config.getAppkey(), config.getAppSecret());
		
		LogisticsOrderShengxianConfirmRequest req=new LogisticsOrderShengxianConfirmRequest();
		req.setTid(Long.parseLong(tradefeedback.getTradeId()));
		req.setOutSid(tradefeedback.getMailId());
		//req.setSenderId(654321L);
		//req.setCancelId(123456L);
		//req.setSellerIp("192.168.1.10");
		//req.setLogisId(1233424L);
		//req.setServiceCode("TANGYU-0003");
		req.setDeliveryType(1L);
		
		LogisticsOrderShengxianConfirmResponse response = null;
		try {
			response = client.execute(req, config.getSessionKey());
		} catch (ApiException e) {
			logger.error("Feedback error: ", e);
		}
		
		if (null == response || !response.isSuccess()) {
			String message = response.getErrorCode() + " - " + response.getMsg() 
            		+ "sub msg:"+response.getSubCode() + " - "+response.getSubMsg();
            
            logger.error("Query order status error: " + message);

            throw new Exception(message);
		}
		
		return response.getShipFresh().getDeliveryName();
	}

	/**
	 * <p></p>
	 * @param tradeStatusModel
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private Long createRawTrade(TradeStatusModel tradeStatusModel) throws Exception {
		
		TaobaoOrderConfig config = tradeStatusModel.getTaobaoOrderConfig();
		TradeFeedback tradefeedback = tradeStatusModel.getTradefeedback();
		
		LogisticsOrderCreateRequest req = new LogisticsOrderCreateRequest();
		req.setLogisCompanyCode(tradefeedback.getCompanyCode());
		req.setMailNo(tradefeedback.getMailId());
		//FIXME
		req.setSenderName(tradefeedback.getSenderName());
		req.setSenderTelephone(tradefeedback.getSenderTelephone());
		req.setSenderMobilePhone(tradefeedback.getSenderMobilePhone());
		req.setSenderAddress(tradefeedback.getSenderAddress());
		req.setSenderZipCode(tradefeedback.getSenderZipCode());
		req.setReceiverName(tradefeedback.getSenderName());
		req.setReceiverTelephone(tradefeedback.getReceiverTelephone());
		req.setReceiverMobilePhone(tradefeedback.getReceiverMobilePhone());
		req.setReceiverAddress(tradefeedback.getReceiverAddress());
		req.setReceiverZipCode(tradefeedback.getReceiverZipCode());
		
		List<TradeFeedbackDetail> tradeDetails = tradefeedback.getDetails();
		StringBuffer goodsNamesSb = new StringBuffer();
		StringBuffer itemValuesSb = new StringBuffer();
		StringBuffer itemQtysSb = new StringBuffer();
		for(TradeFeedbackDetail tradeDetail : tradeDetails) {
			goodsNamesSb.append(tradeDetail.getItemName() + "|");
			itemValuesSb.append(tradeDetail.getItemPrice() + "|");
			itemQtysSb.append(tradeDetail.getItemQty() + "|");
		}
		
		String goodsNames = goodsNamesSb.toString();
		goodsNames = goodsNames.substring(0, goodsNames.length() - 1);
		String itemValues = itemValuesSb.toString();
		itemValues = itemValues.substring(0, itemValues.length() -1);
		String itemQtys = itemQtysSb.toString();
		itemQtys = itemQtys.substring(0, itemQtys.length() - 1);
		
		req.setGoodsNames(goodsNames);
		req.setGoodsQuantities(itemValues);
		req.setItemValues(itemQtys);
		req.setSellerWangwangId(tradefeedback.getSellerWangwangId());
		
		//req.setTradeId(Long.parseLong(tradefeedback.getTradeId()));
		//req.setIsConsign(true);
		//req.setShipping(1L);
		//req.setLogisType("OFFLINE");

		TaobaoClient client = new DefaultTaobaoClient(config.getUrl(), config.getAppkey(), config.getAppSecret());
		
		LogisticsOrderCreateResponse response = null;
		try {
			response = client.execute(req, config.getSessionKey());
		} catch (ApiException e) {
			logger.error("Feedback error: ", e);
		}

		if (null == response || !response.isSuccess()) {
			String message = response.getErrorCode() + " - " + response.getMsg() 
            		+ "sub msg:"+response.getSubCode() + " - "+response.getSubMsg();
            
            logger.error("Query order status error: " + message);

            throw new Exception(message);
		}
		
		return response.getTradeId();
	}

	/**
	 * <p></p>
	 * @param tradeStatusModel
	 * @return
	 * @throws Exception 
	 */
	private String queryTradeStatus(TradeStatusModel tradeStatusModel) throws Exception {
		TaobaoOrderConfig config = tradeStatusModel.getTaobaoOrderConfig();
		TradeFeedback tradefeedback = tradeStatusModel.getTradefeedback();
		
		TaobaoClient client = new DefaultTaobaoClient(config.getUrl(), config.getAppkey(), config.getAppSecret());

		long tid = Long.parseLong(tradefeedback.getTradeId());
        TradeFullinfoGetRequest queryRequest = new TradeFullinfoGetRequest();
        queryRequest.setFields("status");
        queryRequest.setTid(tid);
        TradeFullinfoGetResponse queryResponse = null;
        
        try {
        	queryResponse = client.execute(queryRequest, config.getSessionKey());
		} catch (Exception e) {
			logger.error("Query order error: ", e);
		}
        
        if (null == queryResponse || !queryResponse.isSuccess()) {
            String message = queryResponse.getErrorCode() + " - " + queryResponse.getMsg() 
            		+ "sub msg:"+queryResponse.getSubCode() + " - "+queryResponse.getSubMsg();
            
            logger.error("Query order status error: " + message);

            throw new Exception(message);
        }
        
		String status = queryResponse.getTrade().getStatus();
		
		return status;
	}


}
