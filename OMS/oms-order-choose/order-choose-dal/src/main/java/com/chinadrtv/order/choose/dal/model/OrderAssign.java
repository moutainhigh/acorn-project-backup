package com.chinadrtv.order.choose.dal.model;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderAssign implements java.io.Serializable {
    private Long orderId;
    private String assignFlag;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAssignFlag() {
        return assignFlag;
    }

    public void setAssignFlag(String assignFlag) {
        this.assignFlag = assignFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderAssign)) return false;

        OrderAssign that = (OrderAssign) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }
}
