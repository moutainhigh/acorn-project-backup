/*
 * @(#)ObCustomerServiceTest.java 1.0 2013-5-10下午1:39:59
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.exception.BizException;
import com.chinadrtv.erp.uc.constants.CustomerConstant;
import com.chinadrtv.erp.uc.dto.ObCustomerDto;
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
 * @since 2013-5-10 下午1:39:59 
 * 
 */
public class ObCustomerServiceTest extends AppTest {

	@Autowired
	private ObCustomerService obCustomerService;
	
	/**
	 * <p>查询我的客户OutBound-老客户</p>
	 * @throws ParseException 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void queryObCustomerOld() throws ParseException{
		DataGridModel dataModel = new DataGridModel();
		dataModel.setPage(1);
		dataModel.setStartRow(1);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ObCustomerDto dto = new ObCustomerDto();
		dto.setCustomerType(CustomerConstant.OB_CUSTOMER_TYPE_OLD);
		//dto.setName("石");
		//dto.setBeginDate(sdf.parse("2013-04-01"));
		//dto.setEndDate(sdf.parse("2013-06-01"));
		//dto.setContactid("910790284");
		
		//SQL 拼接测试
		/*dto.setAgentId("111");
		dto.setAgentGrpId("111");
		dto.setPhoneNo("111");
		dto.setPhoneType(2);
		dto.setMemberlevel("aaa");
		dto.setMembertype("111");
		dto.setCustomersource("111");*/
		
		
		Map<String, Object>  pageMap= obCustomerService.queryObCustomer(dataModel, dto);
		Integer total = (Integer) pageMap.get("total");
		List<ObCustomerDto> pageList = (List<ObCustomerDto>) pageMap.get("rows");
		
		Assert.assertTrue(total>=0);
		Assert.assertTrue(null!=pageList && pageList.size()>=0);
	}
	
	/**
	 * <p>查询我的客户OutBound-自主取数客户</p>
	 * @throws ParseException 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void queryObCustomerSelf() throws ParseException{
		DataGridModel dataModel = new DataGridModel();
		dataModel.setPage(1);
		dataModel.setStartRow(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ObCustomerDto dto = new ObCustomerDto();
		dto.setCustomerType(CustomerConstant.OB_CUSTOMER_TYPE_SELF);
		//dto.setName("石");
		dto.setBeginDate(sdf.parse("2013-04-01"));
		dto.setEndDate(sdf.parse("2013-06-01"));
		//dto.setContactid("910790284");
		
		//SQL 拼接测试
		/*dto.setAgentId("111");
		dto.setAgentGrpId("111");
		dto.setPhoneNo("111");
		dto.setPhoneType(2);
		dto.setMemberlevel("aaa");
		dto.setMembertype("111");
		dto.setCustomersource("111");*/
		
		Map<String, Object>  pageMap= obCustomerService.queryObCustomer(dataModel, dto);
		Integer total = (Integer) pageMap.get("total");
		List<ObCustomerDto> pageList = (List<ObCustomerDto>) pageMap.get("rows");
		
		Assert.assertTrue(total>=0);
		Assert.assertTrue(null!=pageList && pageList.size()>=0);
	}
	
	/**
	 * <p>查询我的客户OutBound-成单客户</p>
	 * @throws ParseException 
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void queryObCustomerOrder() throws ParseException{
		DataGridModel dataModel = new DataGridModel();
		dataModel.setPage(1);
		dataModel.setStartRow(1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ObCustomerDto dto = new ObCustomerDto();
		dto.setCustomerType(CustomerConstant.OB_CUSTOMER_TYPE_ORDER);
		//dto.setName("石");
		dto.setBeginDate(sdf.parse("2013-04-01"));
		dto.setEndDate(sdf.parse("2013-06-01"));
		//dto.setContactid("910790284");
		
		//SQL 拼接测试
		/*dto.setAgentId("111");
		dto.setAgentGrpId("111");
		dto.setPhoneNo("111");
		dto.setPhoneType(2);
		dto.setMemberlevel("aaa");
		dto.setMembertype("111");
		dto.setCustomersource("111");*/
		
		Map<String, Object>  pageMap= obCustomerService.queryObCustomer(dataModel, dto);
		Integer total = (Integer) pageMap.get("total");
		List<ObCustomerDto> pageList = (List<ObCustomerDto>) pageMap.get("rows");
		
		Assert.assertTrue(total>=0);
		Assert.assertTrue(null!=pageList && pageList.size()>=0);
	}
	
	@Test
	public void dateDiff(){
		Calendar calendar = Calendar.getInstance();
		Date endDate = calendar.getTime();
		calendar.add(Calendar.DATE, -7);
		Date beginDate = calendar.getTime();
		
		long diff = endDate.getTime()-beginDate.getTime();
		
		if(diff/1000/3600/24>7){
			throw new BizException("起始时间跨度不能大于7天");
		}
	}
}
