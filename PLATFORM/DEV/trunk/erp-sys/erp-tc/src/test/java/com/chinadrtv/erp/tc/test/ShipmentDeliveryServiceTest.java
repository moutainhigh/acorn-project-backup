/*
 * @(#)ShipmentTest.java 1.0 2013-2-20上午9:27:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;


import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.tc.service.ShipmentHeaderService;


/**
 * @author taoyawei
 * @version 1.0
 * @since 2013-2-20 上午9:27:08 
 * 
 */
public class ShipmentDeliveryServiceTest extends TCTest{

	@Autowired
	private ShipmentHeaderService shipmentHeaderService;

	/**
	 * 更新运单的送货公司(未出库)
	 * @throws Exception
	 */
	//@Test
	public void ShipmentDeliveryTest() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", "22082847");// 测试有运单明细的数据
		params.put("shipmentId", "22082847V001");
		params.put("logisticsStatusId", "0");
		params.put("newwarehouseId", "1");
		params.put("newentityId", "1");
		params.put("mdusr", "U002");
		int result = shipmentHeaderService.updateShipmentEntity(params);
		Assert.assertEquals(0, result);
	}
	
	/**
	 * 更新运单的送货公司(已出库)
	 * @throws Exception
	 */
	// @Test
	public void ShipmentDeliveryYetTest() throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", "997700222");
		params.put("shipmentId", "997700222001");
		params.put("newmailId", "EC6666666TV");
		params.put("logisticsStatusId", "0");
		params.put("newentityId", "1");
		params.put("mdusr", "U002");
		int result = shipmentHeaderService.updateShipmentEntity(params);
		Assert.assertEquals(0, result);
	}

}
