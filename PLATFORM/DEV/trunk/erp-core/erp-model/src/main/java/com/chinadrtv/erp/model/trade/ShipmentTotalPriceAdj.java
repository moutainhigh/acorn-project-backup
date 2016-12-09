package com.chinadrtv.erp.model.trade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-20
 * Time: 上午11:33
 * To change this template use File | Settings | File Templates.
 * 客服价格调整表头
 */
@Entity
@Table(name = "SHIPMENT_TOTALPRICE_ADJ", schema = "ACOAPP_OMS")
public class ShipmentTotalPriceAdj implements java.io.Serializable{
    private Long id;
    private String shipmentId;
    private Long orderRefHisId;
    private String orderId;
    private Long revision;
    private String mailId;
    private String entityId;
    private Date agentCreateDate;
    private String accountUser;
    private Date accountConfirmDate;
    private Long status;
    private BigDecimal amount;
    private String remark;
    private String agentUser;
    private String rejectCode;
    private Long refundHeadId;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHIPMENT_ADJ_SEQ")
    @SequenceGenerator(name = "SHIPMENT_ADJ_SEQ", sequenceName = "ACOAPP_OMS.SHIPMENT_ADJ_SEQ")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SHIPMENT_ID")
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
    @Column(name = "ORDER_REF_HIS_ID")
    public Long getOrderRefHisId() {
        return orderRefHisId;
    }

    public void setOrderRefHisId(Long orderRefHisId) {
        this.orderRefHisId = orderRefHisId;
    }
    @Column(name = "ORDER_ID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @Column(name = "REVISION")
    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }
    @Column(name = "MAIL_ID")
    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }
    @Column(name = "ENTITY_ID")
    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    @Column(name = "AGENT_CREATE_DATE")
    public Date getAgentCreateDate() {
        return agentCreateDate;
    }

    public void setAgentCreateDate(Date agentCreateDate) {
        this.agentCreateDate = agentCreateDate;
    }
    @Column(name = "ACCOUNT_USER")
    public String getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(String accountUser) {
        this.accountUser = accountUser;
    }
    @Column(name = "ACCOUNT_CONFIRM_DATE")
    public Date getAccountConfirmDate() {
        return accountConfirmDate;
    }

    public void setAccountConfirmDate(Date accountConfirmDate) {
        this.accountConfirmDate = accountConfirmDate;
    }
    @Column(name = "STATUS")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
    @Column(name = "AMOUNT")
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Column(name = "AGENT_USER")
    public String getAgentUser() {
        return agentUser;
    }

    public void setAgentUser(String agentUser) {
        this.agentUser = agentUser;
    }
    @Column(name = "REJECT_CODE")
    public String getRejectCode() {
        return rejectCode;
    }

    public void setRejectCode(String rejectCode) {
        this.rejectCode = rejectCode;
    }
    @Column(name = "REFUND_HEAD_ID")
    public Long getRefundHeadId() {
        return refundHeadId;
    }

    public void setRefundHeadId(Long refundHeadId) {
        this.refundHeadId = refundHeadId;
    }
}
