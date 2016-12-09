package com.chinadrtv.erp.model.trade;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 半拒收登录表详情
 * User: 王健
 * Date: 2013-03-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "SHIPMENT_REFUND_DETAIL", schema = "ACOAPP_OMS")
public class ShipmentRefundDetail implements java.io.Serializable {

    private static final long serialVersionUID = 5649112396729222300L;
    private Long id;
    //private Long shipmentRefundId;
    private String shipmentId;
    private Long shipmentLineNum;
    private String itemId;
    private String itemDesc;
    private Long totalQty;
    private BigDecimal unitPrice;
    private Long freeFlag;
    private String remark;
    private Long agentQty;
    private BigDecimal agentAmount;
    private Long warehouseQty;
    private Long accountQty;
    private BigDecimal accountAmount;
    private String unitName;

    private ShipmentRefund shipmentRefund;

    // Constructors

    /** default constructor */
    public ShipmentRefundDetail() {
    }

    /** full constructor */
    public ShipmentRefundDetail(ShipmentRefund shipmentRefund, String shipmentId,
                                Long shipmentLineNum, String itemId, String itemDesc,
                                Long totalQty, BigDecimal unitPrice, Long freeFlag,
                                String remark, Long agentQty, BigDecimal agentAmount,
                                Long warehouseQty, Long accountQty, BigDecimal accountAmount,
                                String unitName) {
        this.shipmentRefund=shipmentRefund;
        this.shipmentId = shipmentId;
        this.shipmentLineNum = shipmentLineNum;
        this.itemId = itemId;
        this.itemDesc = itemDesc;
        this.totalQty = totalQty;
        this.unitPrice = unitPrice;
        this.freeFlag = freeFlag;
        this.remark = remark;
        this.agentQty = agentQty;
        this.agentAmount = agentAmount;
        this.warehouseQty = warehouseQty;
        this.accountQty = accountQty;
        this.accountAmount = accountAmount;
        this.unitName = unitName;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_REFUND_DETAIL_SEQ")
    @SequenceGenerator(name = "SHIPMENT_REFUND_DETAIL_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_REFUND_DETAIL_SEQ")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "SHIPMENT_REFUND_ID")
    @JsonIgnore
    public ShipmentRefund getShipmentRefund() {
        return shipmentRefund;
    }

    public void setShipmentRefund(ShipmentRefund shipmentRefund) {
        this.shipmentRefund = shipmentRefund;
    }

    @Column(name = "SHIPMENT_ID", length = 20)
    public String getShipmentId() {
        return this.shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "SHIPMENT_LINE_NUM")
    public Long getShipmentLineNum() {
        return this.shipmentLineNum;
    }

    public void setShipmentLineNum(Long shipmentLineNum) {
        this.shipmentLineNum = shipmentLineNum;
    }

    @Column(name = "ITEM_ID", length = 20)
    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "ITEM_DESC", length = 100)
    public String getItemDesc() {
        return this.itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    @Column(name = "TOTAL_QTY")
    public Long getTotalQty() {
        return this.totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }

    @Column(name = "UNIT_PRICE", precision = 15, scale = 4)
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "FREE_FLAG", precision = 1)
    public Long getFreeFlag() {
        return this.freeFlag;
    }

    public void setFreeFlag(Long freeFlag) {
        this.freeFlag = freeFlag;
    }

    @Column(name = "REMARK", length = 50)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "AGENT_QTY")
    public Long getAgentQty() {
        return this.agentQty;
    }

    public void setAgentQty(Long agentQty) {
        this.agentQty = agentQty;
    }

    @Column(name = "AGENT_AMOUNT", precision = 15, scale = 4)
    public BigDecimal getAgentAmount() {
        return this.agentAmount;
    }

    public void setAgentAmount(BigDecimal agentAmount) {
        this.agentAmount = agentAmount;
    }

    @Column(name = "WAREHOUSE_QTY")
    public Long getWarehouseQty() {
        return this.warehouseQty;
    }

    public void setWarehouseQty(Long warehouseQty) {
        this.warehouseQty = warehouseQty;
    }

    @Column(name = "ACCOUNT_QTY")
    public Long getAccountQty() {
        return this.accountQty;
    }

    public void setAccountQty(Long accountQty) {
        this.accountQty = accountQty;
    }

    @Column(name = "ACCOUNT_AMOUNT", precision = 15, scale = 4)
    public BigDecimal getAccountAmount() {
        return this.accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }

    @Column(name = "UNIT_NAME", length = 20)
    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountAmount == null) ? 0 : accountAmount.hashCode());
        result = prime * result + ((accountQty == null) ? 0 : accountQty.hashCode());
        result = prime * result + ((agentAmount == null) ? 0 : agentAmount.hashCode());
        result = prime * result + ((agentQty == null) ? 0 : agentQty.hashCode());
        result = prime * result + ((freeFlag == null) ? 0 : freeFlag.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((itemDesc == null) ? 0 : itemDesc.hashCode());
        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result + ((shipmentId == null) ? 0 : shipmentId.hashCode());
        result = prime * result + ((shipmentLineNum == null) ? 0 : shipmentLineNum.hashCode());
        result = prime * result + ((shipmentRefund == null) ? 0 : shipmentRefund.hashCode());
        result = prime * result + ((totalQty == null) ? 0 : totalQty.hashCode());
        result = prime * result + ((unitName == null) ? 0 : unitName.hashCode());
        result = prime * result + ((unitPrice == null) ? 0 : unitPrice.hashCode());
        result = prime * result + ((warehouseQty == null) ? 0 : warehouseQty.hashCode());
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
        ShipmentRefundDetail other = (ShipmentRefundDetail) obj;
        if (accountAmount == null) {
            if (other.accountAmount != null)
                return false;
        } else if (!accountAmount.equals(other.accountAmount))
            return false;
        if (accountQty == null) {
            if (other.accountQty != null)
                return false;
        } else if (!accountQty.equals(other.accountQty))
            return false;
        if (agentAmount == null) {
            if (other.agentAmount != null)
                return false;
        } else if (!agentAmount.equals(other.agentAmount))
            return false;
        if (agentQty == null) {
            if (other.agentQty != null)
                return false;
        } else if (!agentQty.equals(other.agentQty))
            return false;
        if (freeFlag == null) {
            if (other.freeFlag != null)
                return false;
        } else if (!freeFlag.equals(other.freeFlag))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (itemDesc == null) {
            if (other.itemDesc != null)
                return false;
        } else if (!itemDesc.equals(other.itemDesc))
            return false;
        if (itemId == null) {
            if (other.itemId != null)
                return false;
        } else if (!itemId.equals(other.itemId))
            return false;
        if (remark == null) {
            if (other.remark != null)
                return false;
        } else if (!remark.equals(other.remark))
            return false;
        if (shipmentId == null) {
            if (other.shipmentId != null)
                return false;
        } else if (!shipmentId.equals(other.shipmentId))
            return false;
        if (shipmentLineNum == null) {
            if (other.shipmentLineNum != null)
                return false;
        } else if (!shipmentLineNum.equals(other.shipmentLineNum))
            return false;
        if (shipmentRefund == null) {
            if (other.shipmentRefund != null)
                return false;
        } else if (!shipmentRefund.equals(other.shipmentRefund))
            return false;
        if (totalQty == null) {
            if (other.totalQty != null)
                return false;
        } else if (!totalQty.equals(other.totalQty))
            return false;
        if (unitName == null) {
            if (other.unitName != null)
                return false;
        } else if (!unitName.equals(other.unitName))
            return false;
        if (unitPrice == null) {
            if (other.unitPrice != null)
                return false;
        } else if (!unitPrice.equals(other.unitPrice))
            return false;
        if (warehouseQty == null) {
            if (other.warehouseQty != null)
                return false;
        } else if (!warehouseQty.equals(other.warehouseQty))
            return false;
        return true;
    }
}