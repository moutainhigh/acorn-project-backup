/*
 * @(#)CustomerController.java 1.0 2013-5-22下午2:20:53
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.taskservice.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskStatus;
import com.chinadrtv.erp.marketing.core.common.CampaignTaskType;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;

@Controller
@RequestMapping(value="/task")
public class TaskController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
    @Autowired
    private CampaignBPMTaskService campaignBPMTaskService;
    
    @Autowired
    private ChangeRequestService changeRequestService;

    @RequestMapping(value="/myCampaignTask")
	public ModelAndView myCampaignTask(@RequestParam(required = true, defaultValue = "") String contactId){
		ModelAndView mav = new ModelAndView("task/myCampaignTask");

		//initialize the campaign task related queries
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String endDate = sdf.format(calendar.getTime());
		calendar.add(Calendar.MONTH, -3);
		String startDate = sdf.format(calendar.getTime());
		mav.addObject("campaignTaskTypes", CampaignTaskType.toList());
		mav.addObject("campaignTaskStatus", CampaignTaskStatus.toList());
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		//TODOX fetch the user id from the session !
		AgentUser user = SecurityHelper.getLoginUser();
		String userId = user.getUserId();
		mav.addObject("userID", userId);
		if(StringUtils.isNotBlank(contactId)) {
			mav.addObject("customerID", contactId);
		}
		
		return mav;
	}
	
	/**
	 * <p>查询我的任务</p>
	 * @param dataGrid
	 * @param obCustomerDto
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryCampaignTask")
	@ResponseBody
	public Map<String, Object> queryCampaignTask(DataGridModel dataGrid, CampaignTaskDto campaignTaskDto){
		Map<String, Object> pageMap = new HashMap<String, Object>();
		try {
			pageMap = campaignBPMTaskService.queryMarketingTask(campaignTaskDto, dataGrid);
		} catch (MarketingException e) {
			logger.error("error occurs!",e);
		}
		return pageMap;
	}
	
	@RequestMapping(value = "/queryUnstartedCampaignTask")
	@ResponseBody
	public Map<String, Object> queryUnstartedCampaignTask(DataGridModel dataGrid, CampaignTaskDto campaignTaskDto){
		Map<String, Object> pageMap = new HashMap<String, Object>();
		try {
			pageMap = campaignBPMTaskService.queryUnStartedCampaignTask(campaignTaskDto, dataGrid);
		} catch (MarketingException e) {
			logger.error("error occurs!",e);
		}
		return pageMap;
	}
	
	/***************************************审批任务***********************************************/
	
	/**
	 * <p>查询审批任务</p>
	 * @param dataGrid
	 * @param obCustomerDto
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryAuditTask")
	@ResponseBody
	public Map<String, Object> queryAuditTask(DataGridModel dataGrid, ApprovingTaskDto approvingTaskDto){
		Map<String, Object> pageMap = new HashMap<String, Object>();
		try {
			pageMap = changeRequestService.queryChangeReqeust(approvingTaskDto, dataGrid);
		} catch (MarketingException e) {
			logger.error("error occurs!", e);
		}
		return pageMap;
	}
}
