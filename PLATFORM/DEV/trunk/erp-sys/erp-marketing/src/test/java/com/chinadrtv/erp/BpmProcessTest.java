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
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.chinadrtv.erp.bpm.service.BpmProcessService;
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
 * @since 2013-2-22 下午4:11:42
 *
 */
@ContextConfiguration("classpath*:applicationContext-test-*.xml")
public class BpmProcessTest  extends AbstractJUnit4SpringContextTests{

	@Autowired
	private BpmProcessService bpmProcessService;

	@Autowired
	private IdentityService identityService;
	
	@Autowired
	private TaskService taskService;

		
		@Test
	    public void startInstanceByUser() {
			
			Map<String,Object> var = new HashMap<String, Object>();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			var.put("jieshu", sf.format(DateUtil.addDays2Date(new Date(), 1)));
			//指定发起人
			identityService.setAuthenticatedUserId("zhaizhanyi");
			//启动流程
			String instanceId = bpmProcessService.startProcessInstance("agent_task",System.currentTimeMillis()+"","zhaizhanyi",var);
			Assert.assertNotNull(instanceId);
			
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
