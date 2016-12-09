/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskDto;
import com.chinadrtv.erp.marketing.core.dto.ApprovingTaskVO;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.ChangeRequestService;
import com.chinadrtv.erp.model.marketing.UserBpm;

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
public class ChangeRequestServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private ChangeRequestService changeRequestService;

	
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryMarketingTask() throws MarketingException {
		ApprovingTaskDto aTaskDto = new ApprovingTaskDto();
//		aTaskDto.setOrderID("928409242");
//		aTaskDto.setContactID("777852");
		aTaskDto.setDepartment("wuxi");
		aTaskDto.setStartDate("2013-01-10 16:00:20");
		DataGridModel dataModel = new DataGridModel();
		dataModel.setPage(1);
		dataModel.setRows(10);
		Map<String, Object> results = changeRequestService.queryChangeReqeust(aTaskDto, dataModel);
//		assertTrue(results.size() == 2);
		List<ApprovingTaskVO> contents = (List<ApprovingTaskVO>)results.get("rows");
		assertTrue(contents != null && contents.size() > 0);
	}
	
	@Test
	public void testCreateApprovingTask() {
		UserBpm userBpm = new UserBpm();
		userBpm.setDepartment("wuxi");
		userBpm.setContactID("777852");
		userBpm.setBusiType("1");
		userBpm.setStatus("0");
		String id = changeRequestService.createChangeRequest(userBpm);
		Assert.assertNotNull(id);
	}

	@Test
	public void testCancelChangeRequestByBatchID() {
		String batchID = "1";
		boolean result = changeRequestService.cancelChangeRequestByBatchID(batchID,"取消修改申请,客户临时同意");
		Assert.assertTrue(result);
	}
	
	@Test
	public void testQueryUnCompletedOrderChangeRequest() {
		String orderId ="22702312";
		List<UserBpm> ubs = changeRequestService.queryUnCompletedOrderChangeRequest(orderId);
		assertTrue(ubs!=null);
	}
	
	@Test
	public void testgetChangeRequestStatusByBatchID() {
		String batchID = "1";
		String result = changeRequestService.getChangeRequestStatus(batchID);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void testIsRequestProcessed() {
		String batchID = "341";
		boolean result = changeRequestService.isRequestProcessed(batchID);
		assertTrue(result);
	}
}
