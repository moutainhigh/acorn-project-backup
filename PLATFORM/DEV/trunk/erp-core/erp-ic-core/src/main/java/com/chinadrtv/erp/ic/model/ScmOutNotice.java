package com.chinadrtv.erp.ic.model;

import java.util.Date;

/**
 * User: liukuan
 * Date: 13-2-17
 * Time: 上午10:03
 * To change this template use File | Settings | File Templates.
 * 获取Scm出库通知数据
 */
public class ScmOutNotice {
    private Long ruid;
    private String warehouse;
    private String status;
    private Integer orderTyper;
    private String shipmentId;
    private Date orderDateTime;
    private String item;
    private Double totalQty;



    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrderTyper() {
        return orderTyper;
    }

    public void setOrderTyper(Integer orderTyper) {
        this.orderTyper = orderTyper;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Double totalQty) {
        this.totalQty = totalQty;
    }
}
