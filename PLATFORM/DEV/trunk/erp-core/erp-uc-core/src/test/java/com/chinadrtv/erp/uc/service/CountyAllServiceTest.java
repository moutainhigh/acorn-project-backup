/*
 * @(#)CountyAllServiceTest.java 1.0 2013-6-7上午10:40:46
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.agent.CountyAll;
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
 * @since 2013-6-7 上午10:40:46 
 * 
 */
public class CountyAllServiceTest extends AppTest {

	@Autowired
	private CountyAllService countyAllService;
	
	/**
	 * <p>API-UC-43.查询邮政编码</p>
	 */
	@Test
	public void queryPostcode(){
		Integer countyId = 2584;
		String postcode = countyAllService.queryPostcode(countyId);
		Assert.assertNotNull(postcode);
		Assert.assertEquals(postcode, "565100");
	}
	
	/**
	 * <p>API-UC-44.查询区简码对应第三级地址</p>
	 */
	@Test
	public void queryAreaList(){
		String countyCode = "BY";
		String countyName = "白云";
		String areaCode = "0851";
		
		List<CountyAll> countyList = countyAllService.queryAreaList(countyCode, countyName, areaCode);
		Assert.assertNotNull(countyList);
		Assert.assertTrue(countyList.size()>=0);
	}
	/**
	 * <p>API-UC-45.查询区地址对应省和市</p>
	 */
	@Test
	public void queryByCountyId(){
		Integer countyId = 3132;
		CountyAll countyAll = countyAllService.queryByCountyId(countyId);
		Assert.assertNotNull(countyAll);
		Assert.assertEquals(countyAll.getCountyid(), countyId);
		Assert.assertEquals(countyAll.getProvid(), "03");
		Assert.assertTrue(countyAll.getCityid() == 5);
	}
}
