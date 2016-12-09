/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;
import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.marketing.core.service.LeadInterActionService;
import com.chinadrtv.erp.marketing.core.service.LeadInteractionOrderService;
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
public class LeadInteractionOrderServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private LeadInteractionOrderService leadInteractionOrderService;
	
	@Test
	public void testQueryCampaignByOrderId() throws MarketingException {
		String orderId = "22749617";
		List<Map<String, Object>> leadCampaignIds = leadInteractionOrderService.queryLeadCampaignByOrderId(orderId);
		String campaignId = null;
		Long leadId = null;
		for(Map<String, Object> mo : leadCampaignIds) {
			String campaignIdResult = String.valueOf(((BigDecimal)mo.get("CAMID")).longValue());
			Long leadIdResult = ((BigDecimal)mo.get("LEADID")).longValue();
			if(StringUtils.isNotBlank(campaignIdResult)) {
				campaignId = campaignIdResult;
				leadId = leadIdResult;
				break;
			}
		}
		assertTrue(campaignId != null);
		assertTrue(leadId > 0l);
	}
}
