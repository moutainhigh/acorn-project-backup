/*
 * @(#)TradeFeedbackController.java 1.0 2014-6-18下午2:44:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.taobao.common.dal.model.TradeFeedback;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.model.TradeStatusModel;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.LogisticsOfflineSendRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.response.LogisticsOfflineSendResponse;
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
 * @since 2014-6-18 下午2:44:28 
 * 
 */
@Controller
public class TradeFeedbackController {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TradeFeedbackController.class);

	@RequestMapping(value = "/childFeedback", 
			method = RequestMethod.POST, 
			consumes = "application/json", 
			produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String childFeedback(@RequestBody TradeStatusModel tradeStatusModel) {
		TradeFeedback tradeFeedback = tradeStatusModel.getTradefeedback();
		TaobaoOrderConfig orderConfig = tradeStatusModel.getTaobaoOrderConfig();
		
		String strError = "";

		String tradeId = tradeFeedback.getTradeId();
		Long parentTradeId = Long.parseLong(tradeId.substring(0, tradeId.indexOf("-")));
		
		TaobaoClient client = new DefaultTaobaoClient(orderConfig.getUrl(), orderConfig.getAppkey(), orderConfig.getAppSecret());
		
		TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
		req.setFields("status");
		req.setTid(parentTradeId);
		TradeFullinfoGetResponse response = null;
		
		try {
			response = client.execute(req, orderConfig.getSessionKey());
		} catch (ApiException e1) {
			logger.error("post order status error : ", e1);
		}
		
		if (null != response && !response.isSuccess()) {
			logger.error("get status error:" + response.getErrorCode() + " - " + response.getMsg());
			logger.error("sub msg:" + response.getSubCode() + " - " + response.getSubMsg());
			strError = response.getErrorCode() + "-" + response.getMsg();
		} else {
			String status = response.getTrade().getStatus();
	        if ("WAIT_BUYER_CONFIRM_GOODS".equals(status) || "TRADE_BUYER_SIGNED".equals(status)
	                || "TRADE_FINISHED".equals(status) || "TRADE_CLOSED".equals(status)
	                || "TRADE_CLOSED_BY_TAOBAO".equals(status) || "ALL_WAIT_PAY".equals(status)
	                || "ALL_CLOSED".equals(status)) {
	            //b = true;
	        }else {
	        	//用第一个面单号来反馈父订单
	    		LogisticsOfflineSendRequest feedbackReq = new LogisticsOfflineSendRequest();
	    		feedbackReq.setTid(parentTradeId);
	    		//req.setSubTid(tradeFeedback.getOids());
	    		//req.setIsSplit(1L);
	    		feedbackReq.setOutSid(tradeFeedback.getMailId());
	    		feedbackReq.setCompanyCode(tradeFeedback.getCompanyCode());
	    		LogisticsOfflineSendResponse feedbackResponse = null;
	    		
	    		try {
	    			feedbackResponse = client.execute(feedbackReq, orderConfig.getSessionKey());
	    		} catch (ApiException e) {
	    			e.printStackTrace();
	    			logger.error("post api[" + orderConfig.getUrl() + "] failed ", e);
	    		}
	    		
	    		if(null == feedbackResponse || !feedbackResponse.isSuccess()) {
	    			logger.error("feed back orderid:" + tradeFeedback.getTradeId());
	    			strError = feedbackResponse.getErrorCode() + "/" + feedbackResponse.getMsg() + "," 
	    					+ feedbackResponse.getSubCode() + "/" + feedbackResponse.getSubMsg();
	    			logger.error("feed back error:" + strError);
	    		}
	        }
		}
		
		return strError;
	}
}
