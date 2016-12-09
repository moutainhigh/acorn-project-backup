package com.chinadrtv.erp.oms.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.test.SpringTest;

public class ShipmentSettlementServiceTest extends SpringTest{
	
	private final String TEST_ID = "0";

	@Autowired
	private ShipmentSettlementService shipmentSettlementService;
	
	@Test
	public void testInit() {
		Assert.assertNotNull(shipmentSettlementService);
	}
	
	@Test
	public void testGetSettlementCount() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetSettlementsMapOfStringObjectIntInt() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetSettlementsLongArray() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetById() {
//		Assert.fail("Not yet implemented");
	}

	@Test
	public void testGetShipmentHeader() {
		ShipmentHeader shipmentHeader = new ShipmentHeader();
		shipmentHeader.setId(Long.parseLong(TEST_ID));
		shipmentHeader.setAccountType(TEST_ID);
		shipmentSettlementService.getShipmentHeader(shipmentHeader);
	}

	@Test
	public void testCreateShipmentSettlement() {
//		Assert.fail("Not yet implemented");
	}

}
