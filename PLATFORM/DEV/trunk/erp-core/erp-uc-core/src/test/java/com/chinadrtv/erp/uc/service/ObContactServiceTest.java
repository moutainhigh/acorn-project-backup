/*
 * @(#)ObContactServiceTest.java 1.0 2013-6-3上午9:54:30
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.uc.test.AppTest;

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
 * @since 2013-6-3 上午9:54:30 
 * 
 */
public class ObContactServiceTest extends AppTest{

	@Autowired
	private ObContactService obContactService;
	
	/**
	 * <p>API-UC-37.查询客户取数的座席</p>
	 */
	@Test
	public void queryAgentUser(){
		String contactId = "12865558";
		String agentUser = obContactService.queryAgentUser(contactId);
		Assert.assertNotNull(agentUser);
		Assert.assertEquals(agentUser, "27427");
	}
}
