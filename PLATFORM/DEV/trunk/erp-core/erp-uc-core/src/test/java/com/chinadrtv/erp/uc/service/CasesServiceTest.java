package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.uc.test.AppTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * API-UC-32.	查询服务历史
 *  
 * @author haoleitao
 * @date 2013-5-13 下午3:55:04
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CasesServiceTest extends AppTest{

	@Autowired
	private CasesService casesService;
	
	@Test
	public void testInit(){
		Assert.assertNotNull(casesService);
	}
	
	/**
	 * API-UC-32.	查询服务历史
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	@Test
	public void getAllConpointcrByContactId(){
		String contactId= "931822208";
		int index = 0;
		int size = 10;
		List<Cases> list = casesService.getAllCasesByContactId(contactId, index, size);
		Assert.assertTrue(list != null);
	}
	
	/**
	 * API-UC-32.	查询服务历史的数量,加时间限制
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	@Test
	public void getAllConpointcrByContactIdCount(){
		String contactId= "931822208";
	    int count = casesService.getAllCasesByContactIdCount(contactId);
		Assert.assertTrue(count>0);
	}
}
