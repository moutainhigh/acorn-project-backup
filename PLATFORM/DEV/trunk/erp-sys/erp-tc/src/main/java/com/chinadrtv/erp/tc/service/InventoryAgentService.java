package com.chinadrtv.erp.tc.service;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-17
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface InventoryAgentService {
    void reserveInventory(Order order,ShipmentHeader shipmentHeader,String warehouseId,String usrId);
    void unreserveInventory(Order order, ShipmentHeader shipmentHeader, String usrId);
    boolean isProdIcControl(String flag);
}
