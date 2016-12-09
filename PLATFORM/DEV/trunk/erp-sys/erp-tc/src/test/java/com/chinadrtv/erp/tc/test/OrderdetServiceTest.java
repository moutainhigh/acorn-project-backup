/*
 * @(#)OrderdetService.java 1.0 2013-2-27下午1:45:38
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;

import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.service.OrderdetService;

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
 * @since 2013-2-27 下午1:45:38 
 * 
 */
public class OrderdetServiceTest extends TCTest {
	
	@Autowired
	private OrderdetService orderdetService;
	
	//@Test
	public void testGetOrderDetList(){
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		List<OrderDetail> orderdetList = orderdetService.getOrderDetList(orderhist);
		System.out.println(orderdetList.size());
		Assert.assertNotNull(orderdetList);
	}
	
	//@Test
	public void testUpdateOrderdet(){
		
	}
}
