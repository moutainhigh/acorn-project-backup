package com.chinadrtv.erp.model.trade;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-20
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 * 客服价格调整明细
 */
@Entity
@Table(name = "SHIPMENT_TOTALPRICE_ADJ_DETAIL", schema = "ACOAPP_OMS")
public class ShipmentTotalPriceAdjDetail implements java.io.Serializable {
    private Long id;
    private Long shipmentRefundId;
    private String shipmentId;
    private Long shipmentLineNum;
    private String itemId;
    private String itemDesc;
    private Long totalQty;
    private BigDecimal totalPrice;
    private Integer freeFlag;
    private String remark;
    private Long agentQty;
    private BigDecimal agentAmount;
    private Long warehouseQty;
    private Long accountQty;
    private BigDecimal accountAmount;
    private String unitName;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_ADJ_DETAIL_SEQ")
    @SequenceGenerator(name = "SHIPMENT_ADJ_DETAIL_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_ADJ_DETAIL_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "SHIPMENT_REFUND_ID")
    public Long getShipmentRefundId() {
        return shipmentRefundId;
    }

    public void setShipmentRefundId(Long shipmentRefundId) {
        this.shipmentRefundId = shipmentRefundId;
    }
    @Column(name = "SHIPMENT_ID")
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
    @Column(name = "SHIPMENT_LINE_NUM")
    public Long getShipmentLineNum() {
        return shipmentLineNum;
    }

    public void setShipmentLineNum(Long shipmentLineNum) {
        this.shipmentLineNum = shipmentLineNum;
    }
    @Column(name = "ITEM_ID")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    @Column(name = "ITEM_DESC")
    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
    @Column(name = "TOTAL_QTY")
    public Long getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Long totalQty) {
        this.totalQty = totalQty;
    }
    @Column(name = "TOTAL_PRICE")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    @Column(name = "FREE_FLAG")
    public Integer getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "AGENT_QTY")
    public Long getAgentQty() {
        return agentQty;
    }

    public void setAgentQty(Long agentQty) {
        this.agentQty = agentQty;
    }
    @Column(name = "AGENT_AMOUNT")
    public BigDecimal getAgentAmount() {
        return agentAmount;
    }

    public void setAgentAmount(BigDecimal agentAmount) {
        this.agentAmount = agentAmount;
    }
    @Column(name = "WAREHOUSE_QTY")
    public Long getWarehouseQty() {
        return warehouseQty;
    }

    public void setWarehouseQty(Long warehouseQty) {
        this.warehouseQty = warehouseQty;
    }
    @Column(name = "ACCOUNT_QTY")
    public Long getAccountQty() {
        return accountQty;
    }

    public void setAccountQty(Long accountQty) {
        this.accountQty = accountQty;
    }
    @Column(name = "ACCOUNT_AMOUNT")
    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }
    @Column(name = "UNIT_NAME")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
