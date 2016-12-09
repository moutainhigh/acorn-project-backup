/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.service.LeadService;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;

/**
 * 2013-5-13 下午5:45:51
 * @version 1.0.0
 * @author yangfei
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:applicationContext*.xml")
@TransactionConfiguration//(defaultRollback=false)
@Transactional
public class LeadInteractionServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private LeadInterActionService leadInterActionService;
	
	@Autowired
	private LeadService leadService;

	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetLatestPhoneInterationByInstId() throws MarketingException {
		String instId = "370801";
		LeadInteraction leadInteraction = leadInterActionService.getLatestPhoneInterationByInstId(instId);
		assertTrue(leadInteraction != null);
	}
	
	@Test
	public void testGetLatestPhoneInterationByLeadId() {
		long leadId = 445;
		LeadInteraction leadInteraction = leadInterActionService.getLatestPhoneInterationByLeadId(leadId);
		assertTrue(leadInteraction != null);
	}
	
	@Test
	public void testUpdateLeadInteraction() {
		long leadId = 445;
		boolean result = leadInterActionService.updateLeadInteraction(leadId, "4");
		assertTrue(result);
		result = leadInterActionService.updateLeadInteraction(leadId, "3");
		assertFalse(result);
		result = leadInterActionService.updateLeadInteraction(leadId, "5");
		assertTrue(result);
	}
	
	@Test
	public void testGetLead2Updated() {
		long leadId = 445;
		Lead lead = leadInterActionService.getLead2Updated(leadId);
		assertTrue(lead != null);
		leadService.updateLead(lead);
	}
	
	@Test
	public void testUpdateLeadInteractionBaseInfo() {
		long leadId = 445;
		LeadInteraction leadInteraction = leadInterActionService.getLatestPhoneInterationByLeadId(leadId);
		leadInteraction.setComments("dfasfjas;");
		leadInterActionService.saveOrUpdate(leadInteraction);
	}
	
	@Test
	public void testUpdatePotential2Normal() {
		String contactId = "969484205";
		String potentialContactId = "969484204";
		
		int number = leadInterActionService.updatePotential2Normal(contactId, potentialContactId);
		assertTrue(number > 0);
	}
	
	/*    select *
    from acoapp_marketing.lead_interaction li
    left join acoapp_marketing.lead_interaction_order lio
      on li.id = lio.lead_interaction_id
   where li.time_length >= 20
     and li.time_length < 180
     and to_char(li.create_date,'yyyy-mm-dd hh24:mi:ss') <= '2013-07-30'
     and to_char(li.create_date,'yyyy-mm-dd hh24:mi:ss') >= '2013-07-15'
     and li.ani='15036233666'
     and li.dnis='24100000'
     and li.create_user='12650'
     and li.contact_id='929028298'
     and lio.order_id is null
     and li.is_call_back=0*/
	@Test 
	public void testQueryAllocatableLeadInteractionCount() {
		LeadInteractionSearchDto leadInteractionSearchDto = new LeadInteractionSearchDto();
		leadInteractionSearchDto.setLowCallDuration(20);
		leadInteractionSearchDto.setHighCallDuration(180);
		leadInteractionSearchDto.setIncomingLowDate("2013-07-16");
		leadInteractionSearchDto.setIncomingHighDate("2013-07-30");
		leadInteractionSearchDto.setUserId("12650");
		leadInteractionSearchDto.setContactId("929028298");
		List<Integer> allocateCounts = new ArrayList<Integer>();
		allocateCounts.add(1);
		allocateCounts.add(0);
		leadInteractionSearchDto.setAllocatedNumbers(allocateCounts);
/*		leadInteractionSearchDto.setAllocatedRecordEmilationNeeded(true);
		leadInteractionSearchDto.setOrderRecordEmilationNeeded(false);*/
		int number = leadInterActionService.queryAllocatableLeadInteractionCount(leadInteractionSearchDto);
		assertTrue(number > 0);
	}
	
	@Test 
	public void testQueryAllocatableLeadInteraction() {
		LeadInteractionSearchDto leadInteractionSearchDto = new LeadInteractionSearchDto();
		leadInteractionSearchDto.setLowCallDuration(20);
		leadInteractionSearchDto.setHighCallDuration(180);
		leadInteractionSearchDto.setIncomingLowDate("2013-07-16");
		leadInteractionSearchDto.setIncomingHighDate("2013-07-30");
		leadInteractionSearchDto.setUserId("12650");
//		leadInteractionSearchDto.setContactId("929028298");
/*		leadInteractionSearchDto.setAllocatedRecordEmilationNeeded(true);
		leadInteractionSearchDto.setOrderRecordEmilationNeeded(false);*/
		List<Integer> allocateCounts = new ArrayList<Integer>();
		allocateCounts.add(1);
		allocateCounts.add(0);
		leadInteractionSearchDto.setAllocatedNumbers(allocateCounts);
		List<Object> objs = leadInterActionService.queryAllocatableLeadInteraction(leadInteractionSearchDto);
		assertTrue(objs != null && objs.size() > 0);
	}
	
	@Test
	public void testQueryObsoleteInteraction() {
		LeadInteractionSearchDto leadInteractionSearchDto = new LeadInteractionSearchDto();
		leadInteractionSearchDto.setLowCallDuration(20);
		leadInteractionSearchDto.setHighCallDuration(180);
		leadInteractionSearchDto.setIncomingLowDate("2013-07-16");
		leadInteractionSearchDto.setIncomingHighDate("2013-07-30");
		leadInteractionSearchDto.setUserId("12650");
//		leadInteractionSearchDto.setContactId("929028298");
/*		leadInteractionSearchDto.setAllocatedRecordEmilationNeeded(true);
		leadInteractionSearchDto.setOrderRecordEmilationNeeded(false);*/
		List<Integer> allocateCounts = new ArrayList<Integer>();
		allocateCounts.add(1);
		allocateCounts.add(0);
		leadInteractionSearchDto.setAllocatedNumbers(allocateCounts);
		
		List<Long> selectedInteractions = new ArrayList<Long>();
//		selectedInteractions.add(2053l);
		selectedInteractions.add(2057l);
		leadInteractionSearchDto.setSelectedInteractions(selectedInteractions);
		List<Object> objs = leadInterActionService.queryObsoleteInteraction(leadInteractionSearchDto);
		assertTrue(objs != null && objs.size() > 0);
	}
	
	@Test
	public void testQueryTotalIntradayOutcallTime() {
		String agentId = "12650";
		long totalTime = leadInterActionService.queryTotalIntradayOutcallTime(agentId);
		assertTrue(totalTime > 0);
	}
}
