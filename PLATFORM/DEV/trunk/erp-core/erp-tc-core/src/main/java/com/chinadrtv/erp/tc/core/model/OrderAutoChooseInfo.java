package com.chinadrtv.erp.tc.core.model;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderAutoChooseInfo implements Serializable {
    private Long id;
    private String orderId;
    private Long entityId;
    private Long version;
    private Long warehouseId;
    private String mdusr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }


    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }

}
