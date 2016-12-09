package com.chinadrtv.erp.sales.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * 联系人历史订单信息
 * User: 徐志凯
 * Date: 13-6-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ContactOrderFrontDto extends MyOrderFrontDto {
    private Set<ContactOrderDetailFrontDto> contactOrderDetailFrontDtos=new HashSet<ContactOrderDetailFrontDto>();


    public Set<ContactOrderDetailFrontDto> getContactOrderDetailFrontDtos() {
        return contactOrderDetailFrontDtos;
    }

    public void setContactOrderDetailFrontDtos(Set<ContactOrderDetailFrontDto> contactOrderDetailFrontDtos) {
        this.contactOrderDetailFrontDtos = contactOrderDetailFrontDtos;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    private String grpName;

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getLogisticsStatusId() {
        return logisticsStatusId;
    }

    public void setLogisticsStatusId(String logisticsStatusId) {
        this.logisticsStatusId = logisticsStatusId;
    }

    private String shipmentId;

    private String logisticsStatusId;
}
