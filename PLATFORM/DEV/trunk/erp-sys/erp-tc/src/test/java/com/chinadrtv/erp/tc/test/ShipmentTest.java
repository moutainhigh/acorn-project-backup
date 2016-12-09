/*
 * @(#)ShipmentTest.java 1.0 2013-2-20上午9:27:08
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
 * @since 2013-2-20 上午9:27:08 
 * 
 */
public class ShipmentTest extends OrderRESTClient<Object, OrderReturnCode> {

	private static final String CONTEXT = "http://localhost:8080/erp-tc/shipment/";
	
	@Test
	public void testInit(){
		Assert.assertEquals(true, true);
	}
	
	/**
	 * SR3.6_1.2取消运单 
	* @Description: 
	* @return void
	* @throws
	 */
	//@Test
	public void testCancelBillway(){
		Order orderhist = new Order();
		orderhist.setOrderid("101010");
		orderhist.setMdusr("CCC");
		orderhist.setRevision(10);
		OrderReturnCode orc = this.postData(CONTEXT + "cancelWaybill", orderhist);
		Assert.assertEquals(orc.getCode(), OrderCode.SUCC);
	}
	
	/**
	 * SR3.6_1.1产生运单
	* @Description: 
	* @return void
	* @throws
	 */
	//@Test
	public void testGenerateWaybill(){
		Order orderhist = new Order();
		orderhist.setOrderid("997700222");
		orderhist.setMdusr("ASD");
		orderhist.setRevision(10);
		OrderReturnCode orc = this.postData(CONTEXT + "generateWaybill", orderhist);
		Assert.assertEquals(orc.getCode(), OrderCode.SUCC);
	}
	
}
