package com.chinadrtv.erp.ic.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存在途明细
 * User: gdj
 * Date: 13-8-5
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class NcIntransitItem implements Serializable {

    private String warehouse;
    private String warehouseName;
    private String nccode;
    private String ncfree;
    private String ncfreeName;
    private Double transitQty;
    private Date arriveDate;

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getNccode() {
        return nccode;
    }

    public void setNccode(String nccode) {
        this.nccode = nccode;
    }

    public String getNcfree() {
        return ncfree;
    }

    public void setNcfree(String ncfree) {
        this.ncfree = ncfree;
    }

    public String getNcfreeName() {
        return ncfreeName;
    }

    public void setNcfreeName(String ncfreeName) {
        this.ncfreeName = ncfreeName;
    }

    public Double getTransitQty() {
        return transitQty;
    }

    public void setTransitQty(Double transitQty) {
        this.transitQty = transitQty;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }
}
