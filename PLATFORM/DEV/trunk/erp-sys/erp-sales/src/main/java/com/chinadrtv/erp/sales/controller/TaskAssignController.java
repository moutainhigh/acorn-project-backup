/*
 * @(#)TaskAssignController.java 1.0 2013年8月28日下午3:54:04
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;
import com.chinadrtv.erp.sales.service.TaskAssignService;
import com.chinadrtv.erp.uc.dto.AgentQueryDto4Callback;
import com.chinadrtv.erp.uc.dto.CallbackAssignDto;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description: SAMBA 数据推送 </b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年8月28日 下午3:54:04 
 * 
 */
@Controller
@RequestMapping(value = "/task")
public class TaskAssignController extends BaseController {
	
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TaskAssignController.class);
	
	@Autowired
	private TaskAssignService taskAssignService;

	@RequestMapping(value = "queryAgent")
	@ResponseBody
	public List<Map<String, Object>> queryAgent(AgentQueryDto4Callback dto) throws Exception{
		List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();
		try {
			rsList = taskAssignService.queryAgent(dto.getGroups());
		} catch (Exception e) {
			logger.error("加载坐席组信息失败", e);
		}
		return rsList;
	}
	
	/**
	 * 从坐席组分配类型到坐席
	 * @param liDto
	 * @param agentUsers
	 * @param averageAssign
	 * @param assignStrategy
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "/assignToAgent")
	public Map<String, Object> assignToAgent(CampaignReceiverDto crDto, String agentUsersStr, String assignStrategy){
		
		List<Map<String, Object>> param = new ArrayList<Map<String, Object>>();
		if(null != agentUsersStr && !"".equals(agentUsersStr)){
			JSONObject jsonObj = JSONObject.fromObject(agentUsersStr);
			CallbackAssignDto dto = (CallbackAssignDto) JSONObject.toBean(jsonObj, CallbackAssignDto.class);
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("assignCount", dto.getAssignCount());
			paramMap.put("userGroup", dto.getUserGroup());
			paramMap.put("userId", dto.getUserId());
			param.add(paramMap);
			
			List<String> groupList = new ArrayList<String>();
			groupList.add(dto.getUserGroup());
			
			crDto.setAgentGroupList(groupList);
		}
		
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		try {
			taskAssignService.assignToAgent(crDto, param, assignStrategy);
			success = true;
		} catch (Exception e) {
			message = e.getMessage();
			logger.error("分配到坐席失败", e);
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		return rsMap;
	}
}
