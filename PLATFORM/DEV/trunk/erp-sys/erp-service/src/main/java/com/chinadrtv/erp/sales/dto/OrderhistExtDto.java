package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.agent.Order;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderhistExtDto extends Order {
    public OrderhistExtDto()
    {
        ordertypeName="";
    }
    public Set<OrderDetailDto> getOrderDetailDtos() {
        return orderDetailDtos;
    }

    public void setOrderDetailDtos(Set<OrderDetailDto> orderDetailDtos) {
        this.orderDetailDtos = orderDetailDtos;
    }

    private Set<OrderDetailDto> orderDetailDtos=new HashSet<OrderDetailDto>();

    public String getOrdertypeName() {
        return ordertypeName;
    }

    public void setOrdertypeName(String ordertypeName) {
        this.ordertypeName = ordertypeName;
    }

    private String ordertypeName;

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    private String entityName;

    private String warehouseName;
}
