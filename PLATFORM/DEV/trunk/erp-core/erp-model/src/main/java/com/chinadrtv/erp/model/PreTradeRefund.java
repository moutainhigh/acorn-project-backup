package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-17
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 * 淘宝退款取消
 */
@Entity
@Table(name = "PRE_TRADE_REFUND", schema = "ACOAPP_OMS")
public class PreTradeRefund implements java.io.Serializable {
    private Long id;
    private String refundId;
    private String opsTradeId;
    private String orderStatus;
    private String refundStatus;
    private Double orderTotalFee;
    private Double refundFee;
    private Integer sourceId;
    private String customerId;
    private Date refundCreated;
    private Date refundModified;
    private Date created;
    private Date modified;
    private String refundReason;
    private String refundDesc;
    private Date createDate;
    private Date modifyDate;



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRE_TRADE_REFUND_SEQ")
    @SequenceGenerator(name = "PRE_TRADE_REFUND_SEQ", sequenceName = "ACOAPP_OMS.PRE_TRADE_REFUND_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "REFUND_ID")
    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    @Column(name = "OPS_TRADE_ID")
    public String getOpsTradeId() {
        return opsTradeId;
    }

    public void setOpsTradeId(String opsTradeId) {
        this.opsTradeId = opsTradeId;
    }

    @Column(name = "ORDER_STATUS")
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "REFUND_STATUS")
    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    @Column(name = "ORDER_TOTAL_FEE")
    public Double getOrderTotalFee() {
        return orderTotalFee;
    }

    public void setOrderTotalFee(Double orderTotalFee) {
        this.orderTotalFee = orderTotalFee;
    }

    @Column(name = "REFUND_FEE")
    public Double getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Double refundFee) {
        this.refundFee = refundFee;
    }

    @Column(name = "SOURCE_ID")
    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "CUSTOMER_ID")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Column(name = "REFUND_CREATED")
    public Date getRefundCreated() {
        return refundCreated;
    }

    public void setRefundCreated(Date refundCreated) {
        this.refundCreated = refundCreated;
    }

    @Column(name = "REFUND_MODIFIED")
    public Date getRefundModified() {
        return refundModified;
    }

    public void setRefundModified(Date refundModified) {
        this.refundModified = refundModified;
    }

    @Column(name = "CREATED")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "MODIFIED")
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Column(name = "REFUND_REASON")
    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    @Column(name = "REFUND_DESC")
    public String getRefundDesc() {
        return refundDesc;
    }

    public void setRefundDesc(String refundDesc) {
        this.refundDesc = refundDesc;
    }

    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "MODIFY_DATE")
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
