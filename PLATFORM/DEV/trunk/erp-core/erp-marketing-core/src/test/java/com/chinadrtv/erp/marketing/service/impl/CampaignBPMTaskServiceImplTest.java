/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.service.impl;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskDto;
import com.chinadrtv.erp.marketing.core.dto.CampaignTaskVO;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.model.marketing.LeadTask;
import com.chinadrtv.erp.model.marketing.task.TaskFormItem;
import com.chinadrtv.erp.util.DateUtil;

/**
 * 2013-5-7 下午5:45:51
 * 
 * @version 1.0.0
 * @author yangfei
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-test*.xml")
@TransactionConfiguration//(defaultRollback=false)
@Transactional
public class CampaignBPMTaskServiceImplTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private CampaignBPMTaskService bpmTaskService;
	
	@Test
	public void testCreateMarketingTask2() throws MarketingException, SQLException {
		CampaignTaskDto mTaskDto = new CampaignTaskDto();
		 mTaskDto.setUserID("9124");
		 long startTime = System.currentTimeMillis();
		 String instID = null;
//		 for(int i = 0; i < 1000; i++) {
			 instID = bpmTaskService.createMarketingTask("644956","1001","910791224",0,
					 "1231231","9124", DateUtil.string2Date("2014-03-29", "yyyy-MM-dd"),null,0,0,0,"费用调整过大");
//		 }
		 long endTime = System.currentTimeMillis();
		 System.out.println(endTime - startTime);
		 assertTrue(StringUtils.isNotBlank(instID));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testQueryMarketingTask() throws MarketingException {
		CampaignTaskDto mTaskDto = new CampaignTaskDto();
		mTaskDto.setUserID("27427");
/*		mTaskDto.setStartDate("2013-05-15 09:00:20");
		mTaskDto.setCustomerID("910391244");*/
//		mTaskDto.setEndDate("2013-05-15 09:59:59");
//		mTaskDto.setStatus(Constants.LEAD_TASK_ACTIVE);
		mTaskDto.setInstId("308823");
		DataGridModel dataModel = new DataGridModel();
		dataModel.setPage(1);
		dataModel.setRows(10);
		Map<String, Object> results = bpmTaskService.queryMarketingTask(
				mTaskDto, dataModel);
		List<CampaignTaskVO> contents = (List<CampaignTaskVO>) results
				.get("rows");
	}
	
	@Test
	public void testCompleteTask() {
		String processInsId = "10366743";
		long startTime = System.currentTimeMillis();

		bpmTaskService.completeTaskAndUpdateStatus("", processInsId, "", "cmtest");
		bpmTaskService.completeTaskAndUpdateStatus("", processInsId, "", "cmtest");
		long duration = System.currentTimeMillis() - startTime;
		System.out.println(duration);
		
	}
	
	@Test
	public void testQueryContactTaskHistory() {
		String contactId = "10021920";
		List<Map<String, Object>> result = bpmTaskService.queryContactTaskHistory(contactId);
		assertTrue(result != null);
	}
	
	@Test
	public void testQueryMarketingTaskByInstId() {
		CampaignTaskVO cvo = bpmTaskService.queryMarketingTask("10398604");
		assertTrue(cvo != null);
	}
	
	@Test
	public void testQueryMarketingTaskByInstId2() {
		LeadTask lt = bpmTaskService.queryInst(null);
		assertTrue(lt != null);
	}
	
	@Test
	public void testQueryMarketingTaskCount() {
		CampaignTaskDto mTaskDto = new CampaignTaskDto();
		mTaskDto.setUserID("9124");
		mTaskDto.setCustomerID("910391244");
		int number = bpmTaskService.queryCount(mTaskDto);
		assertTrue(number > 0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryUnstartedMarketingTask() throws MarketingException {
		CampaignTaskDto mTaskDto = new CampaignTaskDto();
		mTaskDto.setUserID("9124");
		mTaskDto.setCustomerID("910791224");
/*		mTaskDto.setStartDate("2013-05-15 09:00:20");
		*/
//		mTaskDto.setEndDate("2013-05-15 09:59:59");
//		mTaskDto.setStatus(Constants.LEAD_TASK_ACTIVE);
		DataGridModel dataModel = new DataGridModel();
		dataModel.setPage(1);
		dataModel.setRows(10);
		Map<String, Object> results = bpmTaskService.queryUnStartedCampaignTask(
				mTaskDto, dataModel);
		List<CampaignTaskVO> contents = (List<CampaignTaskVO>) results
				.get("rows");
	}
	
	@Test
	public void testQueryUnstartedMarketingTaskCount() {
		CampaignTaskDto mTaskDto = new CampaignTaskDto();
		mTaskDto.setUserID("9124");
		mTaskDto.setCustomerID("910791224");
		int number = bpmTaskService.queryUnStartedCampaignTaskCount(mTaskDto);
		assertTrue(number > 0);
	}
	
	@Test
	public void testChangeTaskOwnerByLead() {
		int number = bpmTaskService.changeTaskOwnerByLead(34, "583");
		assertTrue(number > 0);
	}
	
	@Test
	public void testGetTaskForm() {
		String camID = "4054";
		List<TaskFormItem> tfis = bpmTaskService.getTaskForm(camID);
		System.out.println(tfis);
	}

	@Test
	public void testSubmitTaskForm() {
		List<TaskFormItem> items = new ArrayList<TaskFormItem>();

		TaskFormItem tfi = new TaskFormItem();
		tfi.setChecked(true);
		tfi.setName("推荐产品1");
		tfi.setValue("7123");

		TaskFormItem tfi2 = new TaskFormItem();
		tfi2.setChecked(false);
		tfi2.setName("推荐产品2");
		tfi2.setValue("5434");

		TaskFormItem tfi3 = new TaskFormItem();
		tfi3.setChecked(true);
		tfi3.setName("推荐产品3");
		tfi3.setValue("0321");

		items.add(tfi);
		items.add(tfi2);
		items.add(tfi3);

		bpmTaskService.submitTaskForm("13453", "100", items);
	}

	@Test
	public void testCancelTask() {
		 String instID = "305901";
		 String taskID = null;
		 String userID = "9124";
		 boolean isSuc = bpmTaskService.cancelTask("不想联系了!", instID, taskID, userID);
		 assertTrue(isSuc);
	}
	
	@Test
	public void testIsTaskFinished() {
		String instID = "10366743";
		boolean isFinished = bpmTaskService.isTaskFinished(instID);
		assertTrue(isFinished);
		instID = "385857";
		isFinished = bpmTaskService.isTaskFinished(instID);
		assertTrue(!isFinished);
		instID = "47434";
		isFinished = bpmTaskService.isTaskFinished(instID);
		assertTrue(!isFinished);
		instID = "14327";
		isFinished = bpmTaskService.isTaskFinished(instID);
		assertTrue(isFinished);
		
		
	}
	
	@Test
	public void testIsTaskDone() {
		String instID = "10366743";
		boolean isDone = bpmTaskService.queryInstsIsDone(instID);
		assertTrue(!isDone);
		instID = "14327";
		isDone = bpmTaskService.queryInstsIsDone(instID);
		assertTrue(!isDone);
		instID = "458123";
		isDone = bpmTaskService.queryInstsIsDone(instID);
		assertTrue(isDone);
		
	}
	
	@Test
	public void testUpdatePotential2Normal() {
		String contactId = "54321";
		String potentialContactId="27934590";
		int number = bpmTaskService.updatePotential2Normal(contactId, potentialContactId);
		assertTrue(number != 0);
	}
	
	@Test
	public void testUpdateInstAppointDate() {
		String instId="388912";
		Date date = new Date();
//		String stringDate = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss").format(date);
		int number = bpmTaskService.updateInstAppointDate(instId, date);
		assertTrue(number != 0);
	}
}
