package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-26
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ORDEREXT", schema = "IAGENT")
public class OrderExt implements java.io.Serializable{
    @Id
    @Column(name = "ORDERID",length = 16)
    private String orderId;

    @Column(name = "WMSSTATUS",length = 1)
    private String wmsStatus;

    @Column(name = "WMSDESC",length = 200)
    private String wmsDesc;

    @Column(name = "UPDAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date upDate;

    @Column(name = "WAREHOUSEUID_EXT")
    private Integer warehouseIdExt;

    @Column(name = "WAREHOUSENAMEEXT",length = 50)
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
