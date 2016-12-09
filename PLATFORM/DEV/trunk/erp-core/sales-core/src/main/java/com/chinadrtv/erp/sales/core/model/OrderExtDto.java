package com.chinadrtv.erp.sales.core.model;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderhistTrackTask;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-13
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderExtDto {
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ShipmentHeader getShipmentHeader() {
        return shipmentHeader;
    }

    public void setShipmentHeader(ShipmentHeader shipmentHeader) {
        this.shipmentHeader = shipmentHeader;
    }

    public OrderhistTrackTask getOrderhistTrackTask() {
        return orderhistTrackTask;
    }

    public void setOrderhistTrackTask(OrderhistTrackTask orderhistTrackTask) {
        this.orderhistTrackTask = orderhistTrackTask;
    }

    private Order order;
    private ShipmentHeader shipmentHeader;
    private OrderhistTrackTask orderhistTrackTask;
}
