/*
 * @(#)TaskServiceImpl.java 1.0 2013年8月28日上午10:53:14
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.CampaignReceiverService;
import com.chinadrtv.erp.sales.controller.TaskAssignController;
import com.chinadrtv.erp.sales.service.TaskAssignService;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;

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
 * @since 2013年8月28日 上午10:53:14 
 * 
 */
@Service
public class TaskAssignServiceImpl implements TaskAssignService {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TaskAssignController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private CampaignReceiverService campaignReceiverService;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;

	/** 
	 * <p>Title: queryAgent</p>
	 * <p>Description: </p>
	 * @param groupList
	 * @return Map list
	 * @throws Exception 
	 * @see com.chinadrtv.erp.sales.service.TaskService#queryAgent(java.util.List)
	 */ 
	@Override
	public List<Map<String, Object>> queryAgent(List<String> groupList) throws Exception {
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		
		for(String group : groupList){
			List<AgentUser> tempList = new ArrayList<AgentUser>();
			try { 
				tempList = userService.getUserList(group);
			} catch(Exception e) {
				logger.error("查询用户异常",e);
			}
			
			for(AgentUser user : tempList){
				Map<String, Object> rsMap = new HashMap<String, Object>();
				
				String agentId = user.getUserId();
				rsMap.put("userWorkGrp", group);
				rsMap.put("agentId", agentId);
				
				/*CampaignTaskDto taskDto = new CampaignTaskDto();
				taskDto.setTaskSourceType(CampaignTaskSourceType.PUSH.getIndex());
				taskDto.setOwner(agentId);
				taskDto.setStatuses(CampaignTaskStatus.toStringIndexes());
				
				//已分配数量
				int obtainQty = campaignBPMTaskService.queryCount(taskDto);
				rsMap.put("obtainQty", obtainQty);
				
				CampaignTaskDto taskDtoUndial = new CampaignTaskDto();
				taskDtoUndial.setTaskSourceType(CampaignTaskSourceType.PUSH.getIndex());
				taskDtoUndial.setOwner(agentId);
				taskDtoUndial.setStatus("0");
				
				int obtainUnDialQty = campaignBPMTaskService.queryCount(taskDtoUndial);
				rsMap.put("obtainUnDialQty", obtainUnDialQty);*/
				userList.add(rsMap);
			}
		}
		return userList;
	}

	/** 
	 * <p>Title: assignToAgent</p>
	 * <p>Description: </p>
	 * @param crDto
	 * @param param
	 * @param assignStrategy
	 * @throws Exception 
	 * @see com.chinadrtv.erp.sales.service.TaskAssignService#assignToAgent(com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto, java.util.List)
	 */ 
	@Override
	public synchronized void assignToAgent(CampaignReceiverDto crDto, List<Map<String, Object>> agentList, String assignStrategy) throws Exception {
		campaignReceiverService.assignToAgent(crDto, agentList, assignStrategy);
	}
	
}
