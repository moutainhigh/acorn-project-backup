package com.chinadrtv.erp.oms.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.test.SpringTest;

public class ShipmentHeaderServiceTest extends SpringTest{
	
	private final String TEST_ID = "0";

	@Autowired
	private ShipmentHeaderService shipmentHeaderServicea;
	
	@Test
	public void testInit() {
		Assert.assertNotNull(shipmentHeaderServicea);
	}
	
	@Test
	public void testGetShipmentById() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetShipmentCount() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetShipmentsStringStringIntInt() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetShipmentsStringString() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetLogisticsShipmentCount() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetLogisticsShipments() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testAlternateLongStringString() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testAlternateStringStringString() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetShipmentFromShipmentId() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetShipmentFromOrderId() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetReceiptPayment() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetReceiptPaymentCount() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("accounted", false);
		params.put("exclude", false);
		Long count = shipmentHeaderServicea.getReceiptPaymentCount(params);
		logger.info("统计数：" + count);
	}

	@Test
	public void testExport() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetShipmentByAssociatedId() {
		shipmentHeaderServicea.getShipmentByAssociatedId(TEST_ID);
	}

	@Test
	public void testAccAble() {
		shipmentHeaderServicea.accAble(TEST_ID);
	}

}
