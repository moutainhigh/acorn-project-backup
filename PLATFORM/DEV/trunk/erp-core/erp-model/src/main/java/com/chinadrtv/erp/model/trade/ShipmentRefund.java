package com.chinadrtv.erp.model.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 半拒收登录表头
 * User: 王健
 * Date: 2013-03-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "SHIPMENT_REFUND", schema = "ACOAPP_OMS")
public class ShipmentRefund implements java.io.Serializable {

    private static final long serialVersionUID = 489351914464920246L;
    private Long id;
    private String shipmentId;
    private Long orderRefHisId;
    private String orderId;
    private Long revision;
    private String mailId;
    private String entityId;
    private Date agentCreateDate;
    private String warehouseUser;
    private Date warehouseConfirmDate;
    private String accountUser;
    private Date accountConfirmDate;
    private Long status;
    private BigDecimal amount;
    private String remark;
    private String agentUser;
    private String rejectCode;
    private Long refundHeadId;

    private Set<ShipmentRefundDetail> shipmentRefundDetails = new HashSet<ShipmentRefundDetail>();

    // Constructors

    /** default constructor */
    public ShipmentRefund() {
    }

    /** full constructor */
    public ShipmentRefund(String shipmentId, Long orderRefHisId,
                          String orderId, Long revision, String mailId, String entityId,
                          Date agentCreateDate, String warehouseUser,
                          Date warehouseConfirmDate, String accountUser,
                          Date accountConfirmDate, Long status, BigDecimal amount,
                          String remark, String agentUser) {
        this.shipmentId = shipmentId;
        this.orderRefHisId = orderRefHisId;
        this.orderId = orderId;
        this.revision = revision;
        this.mailId = mailId;
        this.entityId = entityId;
        this.agentCreateDate = agentCreateDate;
        this.warehouseUser = warehouseUser;
        this.warehouseConfirmDate = warehouseConfirmDate;
        this.accountUser = accountUser;
        this.accountConfirmDate = accountConfirmDate;
        this.status = status;
        this.amount = amount;
        this.remark = remark;
        this.agentUser = agentUser;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_REFUND_SEQ")
    @SequenceGenerator(name = "SHIPMENT_REFUND_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_REFUND_SEQ")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SHIPMENT_ID", length = 20)
    public String getShipmentId() {
        return this.shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "ORDER_REF_HIS_ID")
    public Long getOrderRefHisId() {
        return this.orderRefHisId;
    }

    public void setOrderRefHisId(Long orderRefHisId) {
        this.orderRefHisId = orderRefHisId;
    }

    @Column(name = "ORDER_ID", length = 20)
    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Version
    @Column(name = "REVISION")
    public Long getRevision() {
        return this.revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    @Column(name = "MAIL_ID", length = 20)
    public String getMailId() {
        return this.mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    @Column(name = "ENTITY_ID", length = 20)
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AGENT_CREATE_DATE", length = 7)
    public Date getAgentCreateDate() {
        return this.agentCreateDate;
    }

    public void setAgentCreateDate(Date agentCreateDate) {
        this.agentCreateDate = agentCreateDate;
    }

    @Column(name = "WAREHOUSE_USER", length = 20)
    public String getWarehouseUser() {
        return this.warehouseUser;
    }

    public void setWarehouseUser(String warehouseUser) {
        this.warehouseUser = warehouseUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WAREHOUSE_CONFIRM_DATE", length = 7)
    public Date getWarehouseConfirmDate() {
        return this.warehouseConfirmDate;
    }

    public void setWarehouseConfirmDate(Date warehouseConfirmDate) {
        this.warehouseConfirmDate = warehouseConfirmDate;
    }

    @Column(name = "ACCOUNT_USER", length = 20)
    public String getAccountUser() {
        return this.accountUser;
    }

    public void setAccountUser(String accountUser) {
        this.accountUser = accountUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ACCOUNT_CONFIRM_DATE", length = 7)
    public Date getAccountConfirmDate() {
        return this.accountConfirmDate;
    }

    public void setAccountConfirmDate(Date accountConfirmDate) {
        this.accountConfirmDate = accountConfirmDate;
    }

    @Column(name = "STATUS")
    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "AMOUNT", precision = 15, scale = 4)
    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "REMARK", length = 200)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "AGENT_USER", length = 20)
    public String getAgentUser() {
        return this.agentUser;
    }

    public void setAgentUser(String agentUser) {
        this.agentUser = agentUser;
    }

    @Column(name = "REJECT_CODE", length = 20)
    public String getRejectCode() {
        return rejectCode;
    }

    public void setRejectCode(String rejectCode) {
        this.rejectCode = rejectCode;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY, mappedBy = "shipmentRefund")
    @JsonIgnore
    public Set<ShipmentRefundDetail> getShipmentRefundDetails() {
        return shipmentRefundDetails;
    }

    public void setShipmentRefundDetails(Set<ShipmentRefundDetail> shipmentRefundDetails) {
        this.shipmentRefundDetails = shipmentRefundDetails;
    }

    @Column(name = "REFUND_HEAD_ID")
	public Long getRefundHeadId() {
		return refundHeadId;
	}

	public void setRefundHeadId(Long refundHeadId) {
		this.refundHeadId = refundHeadId;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountConfirmDate == null) ? 0 : accountConfirmDate.hashCode());
        result = prime * result + ((accountUser == null) ? 0 : accountUser.hashCode());
        result = prime * result + ((agentCreateDate == null) ? 0 : agentCreateDate.hashCode());
        result = prime * result + ((agentUser == null) ? 0 : agentUser.hashCode());
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((entityId == null) ? 0 : entityId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((mailId == null) ? 0 : mailId.hashCode());
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
        result = prime * result + ((orderRefHisId == null) ? 0 : orderRefHisId.hashCode());
        result = prime * result + ((rejectCode == null) ? 0 : rejectCode.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result + ((revision ==null)? 0 : revision.hashCode());
        result = prime * result + ((shipmentId == null) ? 0 : shipmentId.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((warehouseConfirmDate == null) ? 0 : warehouseConfirmDate.hashCode());
        result = prime * result + ((warehouseUser == null) ? 0 : warehouseUser.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShipmentRefund other = (ShipmentRefund) obj;
        if (accountConfirmDate == null) {
            if (other.accountConfirmDate != null)
                return false;
        } else if (!accountConfirmDate.equals(other.accountConfirmDate))
            return false;
        if (accountUser == null) {
            if (other.accountUser != null)
                return false;
        } else if (!accountUser.equals(other.accountUser))
            return false;
        if (agentCreateDate == null) {
            if (other.agentCreateDate != null)
                return false;
        } else if (!agentCreateDate.equals(other.agentCreateDate))
            return false;
        if (agentUser == null) {
            if (other.agentUser != null)
                return false;
        } else if (!agentUser.equals(other.agentUser))
            return false;
        if (amount == null) {
            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
            return false;
        if (entityId == null) {
            if (other.entityId != null)
                return false;
        } else if (!entityId.equals(other.entityId))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (mailId == null) {
            if (other.mailId != null)
                return false;
        } else if (!mailId.equals(other.mailId))
            return false;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        if (orderRefHisId == null) {
            if (other.orderRefHisId != null)
                return false;
        } else if (!orderRefHisId.equals(other.orderRefHisId))
            return false;
        if (rejectCode == null) {
            if (other.rejectCode != null)
                return false;
        } else if (!rejectCode.equals(other.rejectCode))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (revision != other.revision)
            return false;
        if (shipmentId == null) {
            if (other.shipmentId != null)
                return false;
        } else if (!shipmentId.equals(other.shipmentId))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (warehouseConfirmDate == null) {
            if (other.warehouseConfirmDate != null)
                return false;
        } else if (!warehouseConfirmDate.equals(other.warehouseConfirmDate))
            return false;
        if (warehouseUser == null) {
            if (other.warehouseUser != null)
                return false;
        } else if (!warehouseUser.equals(other.warehouseUser))
            return false;
        return true;
    }

}