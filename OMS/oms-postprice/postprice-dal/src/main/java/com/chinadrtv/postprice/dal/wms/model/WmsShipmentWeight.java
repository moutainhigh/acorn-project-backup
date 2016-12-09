package com.chinadrtv.postprice.dal.wms.model;

import java.math.BigDecimal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class WmsShipmentWeight {
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    private String orderId;
    private BigDecimal weight;
}
