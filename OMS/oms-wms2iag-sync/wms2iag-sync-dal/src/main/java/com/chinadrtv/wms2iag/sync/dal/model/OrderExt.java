package com.chinadrtv.wms2iag.sync.dal.model;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderExt implements java.io.Serializable{
    private String orderId;

    private String wmsStatus;

    private String wmsDesc;

    private Date upDate;

    private Integer warehouseIdExt;

    private String warehouseNameExt;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWmsStatus() {
        return wmsStatus;
    }

    public void setWmsStatus(String wmsStatus) {
        this.wmsStatus = wmsStatus;
    }

    public String getWmsDesc() {
        return wmsDesc;
    }

    public void setWmsDesc(String wmsDesc) {
        this.wmsDesc = wmsDesc;
    }

    public Date getUpDate() {
        return upDate;
    }

    public void setUpDate(Date upDate) {
        this.upDate = upDate;
    }

    public Integer getWarehouseIdExt() {
        return warehouseIdExt;
    }

    public void setWarehouseIdExt(Integer warehouseIdExt) {
        this.warehouseIdExt = warehouseIdExt;
    }

    public String getWarehouseNameExt() {
        return warehouseNameExt;
    }

    public void setWarehouseNameExt(String warehouseNameExt) {
        this.warehouseNameExt = warehouseNameExt;
    }
}
