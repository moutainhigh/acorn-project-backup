package com.chinadrtv.erp.tc.core.dto;

import com.chinadrtv.erp.model.agent.Order;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderDto extends Order {
    private String shipmentId;

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
}
