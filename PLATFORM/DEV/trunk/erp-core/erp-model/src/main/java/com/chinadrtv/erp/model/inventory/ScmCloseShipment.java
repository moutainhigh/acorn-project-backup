package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-19
 * Time: 下午4:45
 * To change this template use File | Settings | File Templates.
 * WMS出库取消
 */
@Entity
@Table(name = "SCM_CLOSE_SHIPMENT")
public class ScmCloseShipment implements java.io.Serializable {
    private Long ruId;
    private String shipmentId;
    private String revision;
    private String warehouse;
    private String orderType;
    private Integer itemsStatus;
    private String item;
    private double totalQty;
    private String cancel_user;
    private Date cancelDate;
    private Date dateTimeStamp;
    private Integer taskno;
    private Integer upsTatus;
    private Date updat;
    private String upuser;
    private Integer dnstatus;
    private Date dndat;
    private String dnuser;
    private String dsc;
    private String shipmentid;
    private Integer internalId;
    private String batchId;
    private Date batchDate;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCM_CLOSE_SHIPMENT_SEQ")
    @SequenceGenerator(name = "SCM_CLOSE_SHIPMENT_SEQ", sequenceName = "SCM_CLOSE_SHIPMENT_SEQ")
    @Column(name = "RUID")
    public Long getRuId() {
        return ruId;
    }

    public void setRuId(Long ruId) {
        this.ruId = ruId;
    }

    @Column(name = "SHIPMENT_ID")
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "REVISION")
    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Column(name = "WAREHOUSE")
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "ORDER_TYPE")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Column(name = "ITEM_STATUS")
    public Integer getItemsStatus() {
        return itemsStatus;
    }

    public void setItemsStatus(Integer itemsStatus) {
        this.itemsStatus = itemsStatus;
    }

    @Column(name = "ITEM")
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Column(name = "TOTAL_QTY")
    public double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(double totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "CANCEL_USER")
    public String getCancel_user() {
        return cancel_user;
    }

    public void setCancel_user(String cancel_user) {
        this.cancel_user = cancel_user;
    }

    @Column(name = "CANCEL_DATE")
    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    @Column(name = "DATE_TIME_STAMP")
    public Date getDateTimeStamp() {
        return dateTimeStamp;
    }

    public void setDateTimeStamp(Date dateTimeStamp) {
        this.dateTimeStamp = dateTimeStamp;
    }

    @Column(name = "TASKSNO")
    public Integer getTaskno() {
        return taskno;
    }

    public void setTaskno(Integer taskno) {
        this.taskno = taskno;
    }

    @Column(name = "UPSTATUS")
    public Integer getUpsTatus() {
        return upsTatus;
    }

    public void setUpsTatus(Integer upsTatus) {
        this.upsTatus = upsTatus;
    }

    @Column(name = "UPDAT")
    public Date getUpdat() {
        return updat;
    }

    public void setUpdat(Date updat) {
        this.updat = updat;
    }

    @Column(name = "UPUSER")
    public String getUpuser() {
        return upuser;
    }

    public void setUpuser(String upuser) {
        this.upuser = upuser;
    }

    @Column(name = "DNSTATUS")
    public Integer getDnstatus() {
        return dnstatus;
    }

    public void setDnstatus(Integer dnstatus) {
        this.dnstatus = dnstatus;
    }

    @Column(name = "DNDAT")
    public Date getDndat() {
        return dndat;
    }

    public void setDndat(Date dndat) {
        this.dndat = dndat;
    }

    @Column(name = "DNUSER")
    public String getDnuser() {
        return dnuser;
    }

    public void setDnuser(String dnuser) {
        this.dnuser = dnuser;
    }

    @Column(name = "DSC")
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    @Column(name = "SHIPMENTID")
    public String getShipmentid() {
        return shipmentid;
    }

    public void setShipmentid(String shipmentid) {
        this.shipmentid = shipmentid;
    }

    @Column(name = "INTERNAL_ID")
    public Integer getInternalId() {
        return internalId;
    }

    public void setInternalId(Integer internalId) {
        this.internalId = internalId;
    }

    @Column(name = "BATCH_ID")
    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Column(name = "BATCH_DATE")
    public Date getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }
}
