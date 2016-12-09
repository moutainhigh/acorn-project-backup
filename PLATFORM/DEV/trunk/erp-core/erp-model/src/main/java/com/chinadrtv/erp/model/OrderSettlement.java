package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-8
 * Time: 下午5:54
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ORDER_SETTLEMENT", schema = "IAGENT")
public class OrderSettlement implements java.io.Serializable {
    private Long ruId;
    private String sourceId;
    private String tradeId;
    private String shipmentId;
    private String settlementFlag;
    private String settlementDesc;
    private Double revenue;
    private Double expenditure;
    private String note;
    private Date crdt;
    private String isrecognition;
    private String cruser;
    private OrderSettlementType settlementType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_settlement_seq")
    @SequenceGenerator(name = "order_settlement_seq", sequenceName = "IAGENT.order_settlement_seq")
    @Column(name = "RUID")
    public Long getRuId() {
        return ruId;
    }

    public void setRuId(Long ruId) {
        this.ruId = ruId;
    }

    @Column(name = "SOURCE_ID")
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "TRADE_ID")
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    @Column(name = "SHIPMENT_ID")
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "SETTLEMENT_FLAG")
    public String getSettlementFlag() {
        return settlementFlag;
    }

    public void setSettlementFlag(String settlementFlag) {
        this.settlementFlag = settlementFlag;
    }

    @Column(name = "SETTLEMENT_DESC")
    public String getSettlementDesc() {
        return settlementDesc;
    }

    public void setSettlementDesc(String settlementDesc) {
        this.settlementDesc = settlementDesc;
    }

    @Column(name = "REVENUE")
    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    @Column(name = "EXPENDITURE")
    public Double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }

    @Column(name = "NOTE")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "CRDT")
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Column(name = "ISRECOGNITION")
    public String getIsrecognition() {
        return isrecognition;
    }

    public void setIsrecognition(String isrecognition) {
        this.isrecognition = isrecognition;
    }

    @Column(name = "CRUSER")
    public String getCruser() {
        return cruser;
    }

    public void setCruser(String cruser) {
        this.cruser = cruser;
    }

    @ManyToOne
    @JoinColumn(name = "SETTLEMENT_ID")
    public OrderSettlementType getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(OrderSettlementType settlementType) {
        this.settlementType = settlementType;
    }
}
