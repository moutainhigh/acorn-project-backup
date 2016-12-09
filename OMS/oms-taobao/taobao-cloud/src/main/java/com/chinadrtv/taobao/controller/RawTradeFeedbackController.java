/*
 * @(#)RawTradeFeedbackController.java 1.0 2014-7-1下午1:35:44
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinadrtv.taobao.model.TradeStatusModel;
import com.chinadrtv.taobao.service.FeedbackService;

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
 * @since 2014-7-1 下午1:35:44 
 * 
 */
@Controller
public class RawTradeFeedbackController {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RawTradeFeedbackController.class);

	private FeedbackService feedbackService = new FeedbackService();

	@RequestMapping(value = "/rawTradeFeedback", headers = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Map<String, Object> rawTradeFeedback(@RequestBody TradeStatusModel tradeStatusModel) {
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		try {
			feedbackService.rawTradeFeedback(tradeStatusModel);
		} catch (Exception e) {
			message = e.getMessage();
			logger.error("Feedback error: ", e);
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}

}
