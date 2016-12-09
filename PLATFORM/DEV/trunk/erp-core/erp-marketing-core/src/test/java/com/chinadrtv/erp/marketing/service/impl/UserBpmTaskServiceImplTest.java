/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.marketing.UserBpmTask;

/**
 * 2013-5-13 下午5:45:51
 * @version 1.0.0
 * @author yangfei
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:applicationContext*.xml")
@TransactionConfiguration
@Transactional
public class UserBpmTaskServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private UserBpmTaskService userBpmTaskService;

	@Test
	public void testQueryApprovingTaskByBatchID() throws MarketingException {
		String batchID = "1";
		List<UserBpmTask> uTasks = userBpmTaskService.queryApprovingTaskByBatchID(batchID);
		Assert.assertTrue(uTasks!=null && uTasks.size() > 0);
	}
	
	
	@Test
	public void testQueryUnProcessedContactBaseChangeTask() {
		String contactId = "910698284";
		List<UserBpmTask> avos = userBpmTaskService.queryUnProcessedContactBaseChangeTask(contactId);
		assertTrue(avos != null);
	}
	
	@Test
	public void testQueryUnterminatedOrderChangeTask() {
		String orderId = "22750112";
		List<UserBpmTask> avos = userBpmTaskService.queryUnterminatedOrderChangeTask(orderId);
		assertTrue(avos != null);
	}
	
	@Test
	public void testCreateApprovingTask() {
		UserBpmTask userBpmTask = new UserBpmTask();
		userBpmTask.setBatchID("1");
		userBpmTask.setChangeObjID("12");
		String instID = userBpmTaskService.createApprovingTask(userBpmTask);
		Assert.assertTrue(instID!=null);
	}
	
	@Test
	public void testcancelTask() {
		String batchID = "1";
		UserBpmTask userBpmTask = new UserBpmTask();
		userBpmTask.setBatchID(batchID);
		userBpmTask.setChangeObjID("12");
		String instID = userBpmTaskService.createApprovingTask(userBpmTask);
		boolean result = userBpmTaskService.cancelTask(batchID,"客户强烈不愿意");
		Assert.assertTrue(result);
	}
	
	@Test
	public void testCalcChangeReqeustStatus() {
		String batchID = "1";
		 String result = userBpmTaskService.checkChangeRequestStatus(batchID);
		Assert.assertTrue(result != null);
	}
	
	@Test 
	public void testApproveChangeRequest() throws MarketingException {
		boolean isApproved = false;
		isApproved = userBpmTaskService.approveChangeRequest("a", "b", "12345", "改动金额太大");
		Assert.assertTrue(isApproved);
	}
	
	@Test
	public void testCreateAndApproveAndGetRemark() throws MarketingException {
		UserBpmTask userBpmTask = new UserBpmTask();
		String batchId = "1";
		userBpmTask.setRemark("申请金额太大");
		userBpmTask.setBatchID(batchId);
		userBpmTask.setChangeObjID("12");
		String instID = userBpmTaskService.createApprovingTask(userBpmTask);
		boolean isApproved = userBpmTaskService.approveChangeRequest("a", "b", instID, "改动合理");
		Assert.assertTrue(isApproved);
		List<UserBpmTask> uTasks = userBpmTaskService.queryApprovingTaskByBatchID(batchId);
		Assert.assertTrue(uTasks != null);
		boolean isRejected = userBpmTaskService.rejectChangeRequest("a", "b", instID, "改动金额太大", false);
		Assert.assertTrue(isRejected);
		uTasks = userBpmTaskService.queryApprovingTaskByBatchID(batchId);
		Assert.assertTrue(uTasks != null);
	}
	
	@Test
	public void testIfPendingStatusExists() {
		List<String> insts = new ArrayList<String>();
		insts.add("321");
		insts.add("320");
		insts.add("298301");
		insts.add("315601");
		List<Object> objs = userBpmTaskService.filterPendingInstances(insts);
		Assert.assertTrue(objs.size() > 1);
	}
	
	@Test
	public void testGetChangeObjId() throws MarketingException {
		String batchId = "110";
		List<Object> objs = userBpmTaskService.getChangeObjIdByBatchIdOrInstanceId(batchId, null);
		Assert.assertTrue(objs.size() != 0);
	}
	
	@Test
	public void testGetChangeObjId2() throws MarketingException {
		String instId = "347701";
		List<Object> objs = userBpmTaskService.getChangeObjIdByBatchIdOrInstanceId(null, instId);
		Assert.assertTrue(objs.size() != 0);
	}
	
	@Test
	public void testQueryManagerApprovedEmsChangeProcess() {
		String startDate = "2013-08-12";
		String endDate = "2013-08-22";
		boolean isEms = true;
		int start = 0;
		int end = 10;
		List<UserBpmTask> userBpmTasks = userBpmTaskService.queryManagerApprovedEmsChangeProcess(startDate, endDate, isEms, start, end);
		assertTrue(userBpmTasks != null);
	}
	
	
	
}
