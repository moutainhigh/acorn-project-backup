package com.chinadrtv.erp.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 12-12-17
 * Time: 下午3:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "mail_status_his", schema = "IAGENT")
public class MailStatusHis implements java.io.Serializable {
    /**
     * 序列号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQMAILHIS")
    @SequenceGenerator(name = "SEQMAILHIS", sequenceName = "IAGENT.SEQ_MAILSTAT_HIS_RECID")
    @Column(name = "RECORD_ID")
    private Long recordId;
    /**
     * 订单号
     */

    @Column(name = "SHIPMENT_ID",length = 100)
    private String shipmentId;

    /**
     * 邮件号
     */
    @NotNull
    @Column(name = "MAILID",length = 20)
    private String mailId;

    /**
     * EMS状态
     */
    @Column(name = "STATUS_CODE",length = 8)
    private String statusCode;

    /**
     * EMS原因
     */
    @Column(name = "REASON_CODE", length = 8)
    private String reasonCode;

    /**
     * 签收信息
     */
    @Column(name = "SIGN_RESULT",length = 100)
    private String signResult;

    /**
     * EMS更新时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EMS_DATE_TIME_STAMP")
    private Date updateDate;

    @Transient
    public Time getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Time updateTime) {
        this.updateTime = updateTime;
    }

    @Transient
    private Time updateTime;

    /**
     * 是否已经处理
     */
    @Column(name = "IS_PROCESSED", length = 1)
    //@Type(type="yes_no")
    private String isProcessed;

    /**
     * 数据记录时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TIME_STAMP")
    private Date recordDate;

    /**
     * 送货公司
     */
    @Column(name = "COMPANYID",length = 16)
    private String companyId;

    /**
     * 操作类型
     */
    @Column(name = "OPERATYPE",length = 10)
    private String operaType;

    /**
     * 操作时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPERADATE")
    private Date  operaDate;

    /**
     * 站点
     */
    @Column(name = "STATION",length = 100)
    private String station;

    /**
     *  操作人
     */
    @Column(name = "OPTOR",length = 50)
    private String operator;

    /**
     * 拒收原因
     */
    @Column(name = "REFUSEREASON",length = 200)
    private String refuseReason;

    /**
     * 问题原因
     */
    @Column(name = "PROBLEMREASON",length = 200)
    private String problemReason;

    /**
     * 备注
     */
    @Column(name = "REMARKS",length = 200)
    private String remarks;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getSignResult() {
        return signResult;
    }

    public void setSignResult(String signResult) {
        this.signResult = signResult;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProcessed() {
        return isProcessed;
    }

    public void setProcessed(String processed) {
        isProcessed = processed;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOperaType() {
        return operaType;
    }

    public void setOperaType(String operaType) {
        this.operaType = operaType;
    }

    public Date getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(Date operaDate) {
        this.operaDate = operaDate;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getProblemReason() {
        return problemReason;
    }

    public void setProblemReason(String problemReason) {
        this.problemReason = problemReason;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
