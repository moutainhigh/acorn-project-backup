package com.chinadrtv.erp.tc.core.constant.model.shipment;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ShipmentException extends RuntimeException {
    private ShipmentReturnCode shipmentReturnCode;

    public ShipmentException(ShipmentReturnCode shipmentReturnCode)
    {
        this.shipmentReturnCode=shipmentReturnCode;
    }

    public ShipmentReturnCode getShipmentReturnCode() {
        return shipmentReturnCode;
    }
}
