/*
 * @(#)BpmProcessServiceImpl.java 1.0 2013-2-22下午2:55:31
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.bpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.bpm.service.BpmProcessService;

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
 * @since 2013-2-22 下午2:55:31 
 * 
 */
@Service("bpmProcessService")
public class BpmProcessServiceImpl implements BpmProcessService{

	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private HistoryService historyService;
	
	/* (非 Javadoc)
	* <p>Title: startProcessInstance</p>
	* <p>Description: </p>
	* @param processKey
	* @return
	* @see com.chinadrtv.erp.bpm.service.BpmProcessService#startProcessInstance(java.lang.String)
	*/ 
	public String startProcessInstance(String processKey) {
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey);
		return instance.getProcessInstanceId();
	}
	
	public String startProcessInstanceById(String processDefinitionId) {
		ProcessInstance instance = runtimeService.startProcessInstanceById(processDefinitionId);
		return instance.getProcessInstanceId();
	}

	/**
	* <p>Title: startProcessInstance</p>
	* <p>Description: </p>
	* @param processKey
	* @param startUser
	* @return
	* @see com.chinadrtv.erp.bpm.service.BpmProcessService#startProcessInstance(java.lang.String, java.lang.String)
	*/ 
	public String startProcessInstance(String processKey,String businessKey, String startUser) {
		//指定发起人
		identityService.setAuthenticatedUserId(startUser);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey,businessKey);
		return instance.getProcessInstanceId();
	}
	
	/**
	 * 
	* @Description: TODO
	* @param processKey
	* @param businessKey
	* @param startUser
	* @param var
	* @return
	* @return String
	* @throws
	 */
	public String startProcessInstance(String processKey,String businessKey, String startUser,Map<String,Object> var) {
		//指定发起人
		identityService.setAuthenticatedUserId(startUser);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processKey,businessKey,var);
		return instance.getProcessInstanceId();
	}
	

	/**
	* <p>Title: completeByBusinesskey</p>
	* <p>Description: </p>
	* @param businesskey
	* @return
	* @see com.chinadrtv.erp.bpm.service.BpmProcessService#completeByBusinesskey(java.lang.String)
	*/ 
	public void completeByProcessInsId(String processDefId,String processInsId,String user) {
		
		//获取已领取任务
		List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey(processDefId).processInstanceId(processInsId).taskAssignee(user).active().list();
		
		for(Task t:unsignedTasks){
			//签收任务
			taskService.claim(t.getId(), user);

			Map<String,Object> v = new HashMap<String, Object>();
			v.put("user", user);
			//完成任务
			taskService.complete(t.getId(), v);
		}
	}
	
	public void completeByTaskID(String processDefId,String processInsId,String taskID, String user) {
		
		//获取已领取任务
		List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey(processDefId).taskId(taskID)
				.processInstanceId(processInsId).taskAssignee(user).active().list();
		
		for(Task t:unsignedTasks){
			//签收任务
			taskService.claim(t.getId(), user);

			Map<String,Object> v = new HashMap<String, Object>();
			v.put("user", user);
			//完成任务
			taskService.complete(t.getId(), v);

		}
	}
	
	public void completeTaskByInstID(String instID, Map<String,Object> v, String user) {
		List<Task> unsignedTasks = taskService.createTaskQuery().processInstanceId(instID).active().list();
		for(Task t:unsignedTasks){
			//签收任务
			taskService.claim(t.getId(), user);
			v.put("user", user);
			//完成任务
			taskService.complete(t.getId(), v);
		}
	}
	
	public void submitTaskForm(String instID, Map<String, String> formProperties) {
		List<Task> unsignedTasks = taskService.createTaskQuery().processInstanceId(instID).active().list();
		for(Task t:unsignedTasks){
			formService.submitTaskFormData(t.getId(), formProperties);
		}
	}
	
	/**
	 * 
	* @Description: TODO
	* @param processDefId
	* @param user
	* @param firstResult
	* @param maxResults
	* @return
	* @return Map<String,String>
	* @throws
	 */
	public Map<String,String> queryTaskAssignee(String processDefId,String user,int firstResult,int maxResults) {
		
		Map<String,String> result = new HashMap<String, String>();
		List<Task> unsignedTasks =null;
		//获取已领取任务
		if(processDefId!=null){
			unsignedTasks = taskService.createTaskQuery().processDefinitionKey(processDefId).taskAssignee(user).active().listPage(firstResult, maxResults);
		}else{
			unsignedTasks = taskService.createTaskQuery().taskAssignee(user).active().listPage(firstResult, maxResults);
		}
		
		
		for(Task t:unsignedTasks){
			//根据流程实例Id，查询流程实例信息
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(t.getProcessInstanceId()).active()
								.singleResult();
			result.put(processInstance.getBusinessKey(),t.getName() );
		}
		
		return result;
	}

	
	/**
	 * 
	* @Description: TODO
	* @param processDefId
	* @param user
	* @return
	* @return Map<String,String>
	* @throws
	 */
	public Map<String,String> queryTaskAssignee(String processDefId,String user) {
		
		return queryTaskAssignee(processDefId, user, 0, 50);
	}
	
	/**
	 * 获取任务ID列表,
	 * queryTaskByAssigneeAndInstance
	 * @return 
	 *List<Integer>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<String> queryTaskByAssigneeAndInstance(String user, String instID) {
		List<String> ids = new ArrayList<String>();
		TaskQuery tq = taskService.createTaskQuery().active();
		if(StringUtils.isNotBlank(user)) {
			tq = tq.taskAssignee(user);
		}
		if(StringUtils.isNotBlank(instID)) {
			tq = tq.processInstanceId(instID);
		}
		List<Task> tasks = tq.list();
		if(tasks != null) {
			for(Task t : tasks) {
				ids.add(t.getId());
			}
		}
		return ids;
	}
	
	public List<String> queryHistTaskByInstID(String instID) {
		List<String> ids = new ArrayList<String>();
		List<HistoricTaskInstance> histTasks = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(instID)
				.list();
		for(HistoricTaskInstance hti : histTasks) {
			ids.add(hti.getId());
		}
		return ids;
	}
	
	public Map<String, String> getFromPropertiesByTaskID(String taskID) {
		Map<String, String> taskFormProperties = new HashMap<String, String>();
		List<HistoricDetail> historyVariables = historyService
				.createHistoricDetailQuery()
				.taskId(taskID)
				.formProperties().list();
		for(HistoricDetail hd : historyVariables) {
			HistoricFormProperty formProperty = (HistoricFormProperty) hd;
			taskFormProperties.put(formProperty.getPropertyId(), formProperty.getPropertyValue());
		}
		return taskFormProperties;
	}
	
	public void changeTaskOwner(String taskID, String newOwner) {
		taskService.setOwner(taskID, newOwner);
	}

	public void cancelInstance(String instID, String remark) {
		runtimeService.deleteProcessInstance(instID, remark);
	}
	
}
