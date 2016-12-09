package com.chinadrtv.erp.oms.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderRefundDto implements java.io.Serializable {
    public OrderRefundDto()
    {
        System.out.println("here");
    }
    private String orderId;
    private String remark;
    private String userId;

    public List<OrderdetRefundDto> getOrderdetRefundDtoSet() {
        return orderdetRefundDtoSet;
    }

    public void setOrderdetRefundDtoSet(List<OrderdetRefundDto> orderdetRefundDtoSet) {
        this.orderdetRefundDtoSet = orderdetRefundDtoSet;
    }

    private List<OrderdetRefundDto> orderdetRefundDtoSet =new ArrayList<OrderdetRefundDto>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
