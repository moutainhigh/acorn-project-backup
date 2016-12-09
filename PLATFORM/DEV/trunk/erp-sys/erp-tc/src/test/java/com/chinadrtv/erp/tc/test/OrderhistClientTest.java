/*
 * @(#)OrderhistClientTest.java 1.0 2013-3-4上午9:45:09
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;

import junit.framework.Assert;

import org.junit.Test;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;

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
 * @since 2013-3-4 上午9:45:09 
 * 
 */
public class OrderhistClientTest extends OrderRESTClient<Order, OrderReturnCode> {

	private final static String context = "http://localhost:8080/erp-tc/order/1";
	
	@Test
	public void testInit(){
		
	}
	
	/**
	 * 逻辑删除订单 
	* @Description: 
	* @return void
	* @throws
	 */
	//@Test
	public void testDeleteLogic(){
		Order orderhist = new Order();
		orderhist.setOrderid("997700222");
		orderhist.setCallid("4321");
		orderhist.setMdusr("U003");
		OrderReturnCode orc = this.postData(context + "/deleteLogic", orderhist);
		
		Assert.assertEquals(OrderCode.SUCC, orc.getCode());
	}
}
