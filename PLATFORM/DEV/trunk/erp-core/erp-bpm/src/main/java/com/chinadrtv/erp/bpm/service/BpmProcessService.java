/*
 * @(#)BpmProcessService.java 1.0 2013-2-22下午2:54:00
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.bpm.service;

import java.util.List;
import java.util.Map;

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
 * @author yangfei
 * @version 1.0
 * @since 2013-2-22 下午2:54:00 
 * 
 */
public interface BpmProcessService {
	
	String startProcessInstance(String processKey);
	
	String startProcessInstance(String processKey,String businessKey, String startUser);
	
	String startProcessInstance(String processKey,String businessKey, String startUser,Map<String,Object> var);
	
	String startProcessInstanceById(String processDefinitionId);
	
	void completeByProcessInsId(String processDefId,String processInsId,String user);
	
	void completeByTaskID(String processDefId,String processInsId,String taskID, String user);
	
	void submitTaskForm(String taskID, Map<String, String> formProperties);
	
	Map<String, String> getFromPropertiesByTaskID(String taskID);
	
	Map<String,String> queryTaskAssignee(String processDefId,String user,int firstResult,int maxResults);
	
	Map<String,String> queryTaskAssignee(String processDefId,String user);
	
	List<String> queryHistTaskByInstID(String instID);
	
	List<String> queryTaskByAssigneeAndInstance(String user, String instID);
	
	/**
	 * 修改bpm任务所有者
	 * changeTaskOwner
	 * @param taskID
	 * @param newOwner 
	 *void
	 * @exception 
	 * @since  1.0.0
	 */
	void changeTaskOwner(String taskID, String newOwner);
	
	void cancelInstance(String instID, String remark);
	
	void completeTaskByInstID(String InstID, Map<String,Object> v, String user);

}
