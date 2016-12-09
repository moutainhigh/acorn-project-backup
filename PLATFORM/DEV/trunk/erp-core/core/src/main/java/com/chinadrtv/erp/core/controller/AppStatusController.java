/*
 * @(#)AppStatusController.java 1.0 2013-3-14下午1:48:30
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.core.service.AppStatusService;
import com.chinadrtv.erp.exception.AppStatusException;
import com.chinadrtv.erp.util.DateUtil;

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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-3-14 下午1:48:30 
 * 
 */
@Controller
public class AppStatusController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AppStatusController.class);
	@Autowired
	private AppStatusService appStatusService;
	
	
	@RequestMapping("/web_status")
	@ResponseBody
	public String web_status(HttpServletResponse response)
			throws IOException {

		String status = "200";
		
		try {
			appStatusService.checkDbConnection();
			appStatusService.checkFilePath();
			appStatusService.checkHttpConnection();
		} catch (AppStatusException e) {
			logger.error("",e);
			status = e.getMessage();
			response.setStatus(500);
		}
		return status;
	}
	
	
	@RequestMapping("/getServerTime")
	@ResponseBody
	public Map<String,String> getServerTime(HttpServletResponse response)
			throws IOException {

		Map<String,String> result = new HashMap<String, String>();
		
		try {
			result.put("serverTime", DateUtil.date2FormattedString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			logger.error("",e);
		}
		return result;
	}

}
