/*
 * @(#)CampaignReceiverServiceTest.java 1.0 2013年12月18日上午9:58:43
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.service;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.JunitTestBase;
import com.chinadrtv.erp.marketing.core.service.CampaignReceiverService;
import com.chinadrtv.erp.model.marketing.CampaignReceiver;

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
 * @author andrew
 * @version 1.0
 * @since 2013年12月18日 上午9:58:43 
 * 
 */
public class CampaignReceiverServiceTest extends JunitTestBase {

	@Autowired
	private CampaignReceiverService campaignReceiverService;
	
	@Test
	public void testRecycleBatchData() throws Exception {
		String batchCode = "2013082813194200001741";
		campaignReceiverService.recycleBatchData(batchCode);
	}
	
	@Test
	public void testQueryByBpmInstId() {
		long bpmInstId = 492823;
		CampaignReceiver cr = campaignReceiverService.queryByBpmInstId(bpmInstId);
		Assert.assertNotNull(cr);
		Assert.assertNotNull(cr.getId());
	}
	
	@Test
	public void testQueryBatchProgress() {
		String batchCode = "2013082813194200001741";
		Map<String, Integer> rsMap = campaignReceiverService.queryBatchProgress(batchCode);
		
		Assert.assertNotNull(rsMap);
		Assert.assertNotNull(rsMap.get("totalQty"));
		Assert.assertNotNull(rsMap.get("unStartQty"));
	}
}
