package com.chinadrtv.logistics.model;

import java.io.Serializable;

public class OrderInfo
        implements Serializable
{
    private String orderId;

    public String getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}