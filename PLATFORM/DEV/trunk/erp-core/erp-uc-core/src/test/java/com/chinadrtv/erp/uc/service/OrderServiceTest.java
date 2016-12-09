/*
 * @(#)OrderServiceTest.java 1.0 2013-5-7下午2:07:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.uc.dto.OrderDetailDto;
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
 * @since 2013-5-7 下午2:07:28 
 * 
 */
public class OrderServiceTest extends AppTest {
	
	@Autowired
	private OrderService orderService;
	
	@Test
	public void initTest(){
		Assert.assertNotNull(orderService);
	}

	@Test
	public void haveExWarehouseOrder(){
		String contactId = "910298214";
		boolean existOrder = orderService.haveExWarehouseOrder(contactId);
		Assert.assertEquals(false, existOrder);
	}
	
	@Test
	public void haveExWarehouseOrderByAddress(){
		String addressId = "28096114";
		boolean existOrder = orderService.haveExWarehouseOrderByAddress(addressId);
		Assert.assertEquals(true, existOrder);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void queryOrderListByContactId(){
		String contactId = "910298214";
		DataGridModel dam = new DataGridModel();
		dam.setPage(1);
		dam.setRows(1);
		
		Map<String, Object> pageMap = orderService.queryOrderListByContactId(dam, contactId);
		Integer totalCount = (Integer) pageMap.get("total");
		List<Order> pageList = (List<Order>) pageMap.get("rows");
		
		Assert.assertTrue(totalCount>=0);
		Assert.assertTrue(null!=pageList && pageList.size()>=0);
	}
	
	@Test
	public void queryOrderDetail(){
		String orderId = "928706272";
		List<OrderDetailDto> odDtoList = orderService.queryOrderDetail(orderId);
		Assert.assertTrue(null!=odDtoList && odDtoList.size()>0);
	}
	
	/**
	 * <p>API-UC-38.查询客户历史成单的座席是否当前座席</p>
	 */
	@Test
	public void queryOrderCreaterByContact(){
		String contactId = "943492257";
		List<String> rsList = orderService.queryOrderCreaterByContact(contactId);
		Assert.assertNotNull(rsList);
		Assert.assertTrue(rsList.contains("sa"));
	}
	
	@Test
	public void isOrderCreatorByContact(){
		String contactId = "943492257";
		String userId = "sa";
		boolean isCreator = orderService.isOrderCreator(userId, contactId);
		Assert.assertTrue(isCreator);
	}
}
