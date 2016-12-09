/*
 * @(#)BpmProcessTest.java 1.0 2013-2-22下午4:11:42
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.chinadrtv.erp.bpm.service.BpmProcessService;
import com.chinadrtv.erp.util.DateUtil;

@ContextConfiguration("classpath*:bpm-task-service/applicationContext-*.xml")
public class BpmSalesProcessTest  extends AbstractJUnit4SpringContextTests{

	@Autowired
	private BpmProcessService bpmProcessService;

	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
//		@Test
//	    public void startInstance() {
//			String instanceId = bpmProcessService.startProcessInstance("simplebookorder");
//			Assert.assertNotNull(instanceId);
//	    }

		
		@Test
//		@Deployment(resources = { "sales.task.bpmn20.xml" })
	    public void startInstanceByUser() throws InterruptedException {
			Map<String,Object> var = new HashMap<String, Object>();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//			var.put("taskDuration", sf.format(DateUtil.addDays2Date(new Date(), 1)));
			var.put("taskDuration", "2013-07-18T11:36:20");
			//指定发起人
			identityService.setAuthenticatedUserId("yangfei");
			//启动流程
			String instanceId = bpmProcessService.startProcessInstance("salesTask",System.currentTimeMillis()+"","yangfei",var);
			
			runtimeService.setVariable(instanceId, "taskDuration", "2013-07-18T11:37:10");

			
			Assert.assertNotNull(instanceId);
//			Thread.sleep(20000);
			List<Task> unsignedTasks = taskService.createTaskQuery().active().taskAssignee("yangfei").list();
			for(Task t:unsignedTasks){
				taskService.claim(t.getId(), "yangfei");
				Map<String,Object> v = new HashMap<String, Object>();
				v.put("user", "yangfei");
				//完成任务
				taskService.complete(t.getId(), v);
			}
			unsignedTasks = taskService.createTaskQuery().active().taskAssignee("yangfei").list();
			System.out.println(unsignedTasks.size());
//			//获取已领取任务
//			List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey("agent_task").taskAssignee("zhaizhanyi").active().list();
//			System.out.println(unsignedTasks.size());
//			
//			for(Task t:unsignedTasks){
//				//签收任务
//				taskService.claim(t.getId(), "zhaizhanyi");
//
//				Map<String,Object> v = new HashMap<String, Object>();
//				v.put("user", "zhaizhanyi");
//				//完成任务
//				taskService.complete(t.getId(), v);
//
//			}
//			Assert.assertTrue(unsignedTasks.size()>0);

	    }
		
		

}
