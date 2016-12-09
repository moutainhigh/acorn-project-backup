/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.erp.marketing.core.dao.CampaignReceiverDao;
import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;
import com.chinadrtv.erp.model.marketing.CampaignReceiver;

/**
 * 2013-9-9 上午9:19:56
 * @version 1.0.0
 * @author yangfei
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext*.xml")
@TransactionConfiguration//(defaultRollback=false)
@Transactional
public class CampaignReceiverDaoImplTest {
	
	@Autowired
	private CampaignReceiverDao campaignReceiverDao;
	
	@Test
	public void testGetAssignList() {
		CampaignReceiverDto cDto = new CampaignReceiverDto();
		cDto.setStatus("0");
		cDto.setBatchCode("2013081913444900001602");
		cDto.setCampaignId(275L);
		cDto.setJobid("VIP");
		int start = 0;
		int rows = 1;
		List<CampaignReceiver> cams = campaignReceiverDao.getAssignList(cDto, start, rows);
		assertTrue(cams != null && cams.size() > 1);
	}
}
