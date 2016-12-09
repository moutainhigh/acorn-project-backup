package com.chinadrtv.erp.model.trade;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 出货结算单
 * User: gaodejian
 * Date: 13-4-8
 * Time: 下午3:18
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "SHIPMENT_SETTLEMENT", schema = "ACOAPP_OMS")
public class ShipmentSettlement implements java.io.Serializable {

    private Long id;
    private String nccompanyId;
    private Long companyId;
    private Long type;
    private Double arAmount;
    private Double apAmount;
    private String isSettled;
    private String settledUser;
    private Date settledDate;
    private String LotNo;
    private ShipmentHeader shipmentHeader;
    private Long orderId;
    private String createUser;
    private Date createDate;
    private String updateUser;
    private Date updateDate;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_SETTLEMENT_SEQ")
    @SequenceGenerator(name = "SHIPMENT_SETTLEMENT_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_SETTLEMENT_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SHIPMENT_HEADER_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    public ShipmentHeader getShipmentHeader() {
        return shipmentHeader;
    }

    public void setShipmentHeader(ShipmentHeader shipmentHeader) {
        this.shipmentHeader = shipmentHeader;
    }

    @Column(name = "NC_COMPANY_ID")
    public String getNccompanyId() {
        return nccompanyId;
    }

    public void setNccompanyId(String nccompanyId) {
        this.nccompanyId = nccompanyId;
    }

    @Column(name = "COMPANY_ID")
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Column(name = "TYPE")
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Column(name = "AR_AMOUNT")
    public Double getArAmount() {
        return arAmount;
    }

    public void setArAmount(Double arAmount) {
        this.arAmount = arAmount;
    }

    @Column(name = "AP_AMOUNT")
    public Double getApAmount() {
        return apAmount;
    }

    public void setApAmount(Double apAmount) {
        this.apAmount = apAmount;
    }

    @Column(name = "LOT_NO")
    public String getLotNo() {
        return LotNo;
    }

    public void setLotNo(String lotNo) {
        LotNo = lotNo;
    }

    @Column(name = "IS_SETTLED")
    public String getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(String isSettled) {
        this.isSettled = isSettled;
    }

    @Column(name = "ORDER_ID")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Column(name = "CREATE_USER")
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_USER")
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Column(name = "SETTLED_USER")
    public String getSettledUser() {
        return settledUser;
    }

    public void setSettledUser(String settledUser) {
        this.settledUser = settledUser;
    }

    @Column(name = "SETTLED_DATE")
    public Date getSettledDate() {
        return settledDate;
    }

    public void setSettledDate(Date settledDate) {
        this.settledDate = settledDate;
    }
}

