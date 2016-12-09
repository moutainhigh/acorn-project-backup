/*
 * @(#)OldContactServiceTest.java 1.0 2013-5-31上午10:11:15
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.uc.test.AppTest;
import com.chinadrtv.erp.user.model.AgentUser;

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
 * @since 2013-5-31 上午10:11:15 
 * 
 */
public class OldContactexServiceTest extends AppTest{
	
	@Autowired
	private OldContactexService oldContactexService;
	
	@Test
	public void queryBindAgentUser(){
		String contactId = "910791264";
		AgentUser user = oldContactexService.queryBindAgentUser(contactId);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getUserId());
	}
}
