/*
 * @(#)LeadTest.java 1.0 2013-5-10下午4:55:41
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.service.CampaignBPMTaskService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.marketing.Lead;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-10 下午4:55:41
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext*.xml")
@TransactionConfiguration(defaultRollback=false)
@Transactional
public class LeadServiceTest  extends
AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private LeadService leadService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private CampaignBPMTaskService campaignBPMTaskService;
	

	/**
	 * @throws ServiceException 
	 * 
	 * @Description: 新增销售线索查询
	 * @return void
	 * @throws
	 */
	@Test
	public void runLeadTest4Tranction() throws ServiceException {
		Lead lead = new Lead();
		lead.setCreateUser("cmtest");
		lead.setCampaignId(1001l);
		lead.setContactId("918181224");
		lead.setOutbound(true);
		String instID = transactionService.saveAndUpdate(lead);
		assertNotNull(instID);
	}
	
	@Test
	public void runLeadTest2() throws ServiceException {
		Lead lead = new Lead();
		String instID = null;
		lead.setId(349l);
		lead.setCreateUser("cmtest");
		lead.setCampaignId(1001l);
		lead.setContactId("918181224");
		lead.setOutbound(true);
		try {
			instID = leadService.saveLead(lead).getBpmInstanceId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		assertNotNull(instID);
	}
	@Test
	public void runLeadTest() {
		Lead lead = new Lead();
		lead.setCreateUser("cmtest");
		lead.setCampaignId(1001l);
		lead.setContactId("918181224");
		lead.setOutbound(true);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
			sdf.format(new Date(System.currentTimeMillis()));
			System.out.println("starting prof test.....");
			Date date = new Date(System.currentTimeMillis());
			long startTime = System.currentTimeMillis();
			System.out.println(sdf.format(date));
			String instID = leadService.saveLead(lead).getBpmInstanceId();
			long duration = System.currentTimeMillis() - startTime;
			System.out.println(duration);
			System.out.println(instID);
			Assert.assertNotNull(instID);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testActivityConcurrency() throws ServiceException {
		Lead lead = new Lead();
		lead.setCreateUser("cmtest");
		lead.setCampaignId(1001l);
		lead.setContactId("918181224");
		lead.setOutbound(true);
		String instID = leadService.saveLead(lead).getBpmInstanceId();
		campaignBPMTaskService.completeTaskAndUpdateStatus("test", instID, "", "10001");
		campaignBPMTaskService.completeTaskAndUpdateStatus("test", instID, "", "10001");
	}
	
	@Test
	public void testSaveLead2() {
		Lead lead = new Lead();
		lead.setId(215L);
		lead.setCreateUser("cmtest");
		lead.setCampaignId(4054l);
		lead.setContactId("918181225");
		lead.setOutbound(true);
		try {
			String instID = leadService.saveLead(lead).getBpmInstanceId();
			System.out.println(instID);
			Assert.assertNotNull(instID);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description: 更新销售线索
	 * @return void
	 * @throws
	 */
	@Test
	public void updateLeadTest() {
		try {
			leadService.updateLead(62L, 1l, "测试", "cmtest1");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateLead2() {
		Lead lead = new Lead();
		lead.setId(445L);
		lead.setStatus(4L);
		lead.setPriority(2L);
		leadService.updateLead(lead);
	}
//
//	/**
//	 * 
//	 * @Description: 销售线索重新分配
//	 * @return void
//	 * @throws
//	 */
//	@Test
//	public void reapportionLeadTest() {
//		try {
//			leadService.reapportionLead(62l, "12345678");
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	@Test
	public void testGetLastestAliveLead() {
		String agentId = "27427";
		String campaignId = "193";
		String contactId = "993590267";
		Lead lead = leadService.getLastestAliveLead(agentId, contactId,
				campaignId);
		assertTrue(lead != null);
	}
	
	@Test
	public void testGetLastestAliveLead2() {
		String agentId = "27427";
		String contactId = "993590267";
		Lead lead = leadService.getLastestAliveLead(agentId, contactId);
		assertTrue(lead != null);
	}
	
/*	@Test
	public void testUpdatePotential2Normal() {
		String contactId = "54321";
		String potentialContactId="22202";
		int number = leadService.updatePotential2Normal(contactId, potentialContactId);
		assertTrue(number != 0);
	}*/
	
	@Test
	public void testTransferLeadAndSubsidiaries() throws Exception {
		String instId = "373742";
		String newAgentId = "27427";
		Date endDate = new Date();
		String comment = "客户指定需要联系张小三";
		Long leadId = leadService.transferLeadAndSubsidiaries(instId, newAgentId, endDate, comment);
		assertTrue(leadId > 0);
	}

}
