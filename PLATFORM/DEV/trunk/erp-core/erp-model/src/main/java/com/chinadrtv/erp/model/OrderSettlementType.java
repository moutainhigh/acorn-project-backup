package com.chinadrtv.erp.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-9
 * Time: 上午9:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ORDER_SETTLEMENT_TYPE", schema = "IAGENT")
public class OrderSettlementType implements java.io.Serializable {
    private Long settlementId;
    private String sourceId;
    private String settlementFlag;
    private String settlementDesc;
    private String mappingKey;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_settlement_type_seq")
    @SequenceGenerator(name = "order_settlement_type_seq", sequenceName = "IAGENT.order_settlement_type_seq")
    @Column(name = "SETTLEMENT_ID")
    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    @Column(name = "SOURCE_ID")
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
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

    @Column(name = "MAPPING_KEY")
    public String getMappingKey() {
        return mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }
}
