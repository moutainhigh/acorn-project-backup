package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: gaoeejian
 * Date: 13-2-5
 * Time: 上午11:41
 * SCM收货单
 */
@Entity
@Table(name="SCM_UPLOAD_RECEIPT_HEADER_HIS")
public class ScmUploadReceiptHeaderHis implements java.io.Serializable {

    private Long ruid;
    private String receiptId;    //receiptid
    private String originNo;
    private String receiptType;
    private String assistant;
    private String sourceId;
    private String contract;
    private String warehouse;
    private String issueOrganization;
    private String receiptOrganization;
    private Date receiptDate;
    private String memo;
    private Long tasksNo;
    private Long upstatus ;                 //上传状态1上传中2上传完成
    private Date update;
    private String upuser;
    private Boolean dnstatus;
    private Date dndate;
    private String dnuser;
    private String  dsc;
    private String otherType;
    private String paymentTerm;
    private String carrier;
    private Double postFee;
    private Boolean isRegional;
    private Boolean isIAgent;
    private Long maileType;
    private Long emsClearStatus;
    private Double valueShipped;
    private Double carriage;
    private Long internalId;
    private String batchId;
    private Date batchDate;

    private List<ScmUploadReceiptDetailHis> details = new ArrayList<ScmUploadReceiptDetailHis>();

    @Id
    @Column(name = "RUID", updatable=false)
    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    @Column(name = "ORIGIN_NO", updatable=false)
    public String getOriginNo() {
        return originNo;
    }

    public void setOriginNo(String originNo) {
        this.originNo = originNo;
    }



    @Column(name = "RECEIPT_ID", updatable=false, unique = true)
    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    @Column(name = "receipt_type", updatable=false)
    public String getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(String receiptType) {
        this.receiptType = receiptType;
    }

    @Column(name = "warehouse", updatable=false)
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "source_id", updatable=false)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }


    @Column(name = "assistant", updatable=false)
    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    @Column(name = "contract", updatable=false)
    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    @Column(name = "issue_organization", updatable=false)
    public String getIssueOrganization() {
        return issueOrganization;
    }

    public void setIssueOrganization(String issueOrganization) {
        this.issueOrganization = issueOrganization;
    }

    @Column(name = "receipt_organization", updatable=false)
    public String getReceiptOrganization() {
        return receiptOrganization;
    }

    public void setReceiptOrganization(String receiptOrganization) {
        this.receiptOrganization = receiptOrganization;
    }

    @Column(name = "memo", updatable=false)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "tasksno", updatable=false)
    public Long getTasksNo() {
        return tasksNo;
    }

    public void setTasksNo(Long tasksNo) {
        this.tasksNo = tasksNo;
    }

    @Column(name = "upstatus", updatable=false)
    public Long getUpstatus() {
        return upstatus;
    }

    public void setUpstatus(Long upstatus) {
        this.upstatus = upstatus;
    }

    @Column(name = "updat", updatable=false)
    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    @Column(name = "upuser", updatable=false)
    public String getUpuser() {
        return upuser;
    }

    public void setUpuser(String upuser) {
        this.upuser = upuser;
    }

    @Column(name = "dnstatus", updatable=false)
    public Boolean getDnstatus() {
        return dnstatus;
    }

    public void setDnstatus(Boolean dnstatus) {
        this.dnstatus = dnstatus;
    }

    @Column(name = "dndat", updatable=false)
    public Date getDndate() {
        return dndate;
    }

    public void setDndate(Date dndate) {
        this.dndate = dndate;
    }

    @Column(name = "dnuser", updatable=false)
    public String getDnuser() {
        return dnuser;
    }

    public void setDnuser(String dnuser) {
        this.dnuser = dnuser;
    }

    @Column(name = "dsc", updatable=false)
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    @Column(name = "other_type", updatable=false)
    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    @Column(name = "carrier", updatable=false)
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Column(name = "payment_term", updatable=false)
    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    @Column(name = "isiagent", updatable=false)
    public Boolean getIsIAgent() {
        return isIAgent;
    }

    public void setIsIAgent(Boolean value) {
        isIAgent = value;
    }

    @Column(name = "mailetype", updatable=false)
    public Long getMaileType() {
        return maileType;
    }

    public void setMaileType(Long maileType) {
        this.maileType = maileType;
    }

    @Column(name = "emsclearstatus", updatable=false)
    public Long getEmsClearStatus() {
        return emsClearStatus;
    }

    public void setEmsClearStatus(Long emsClearStatus) {
        this.emsClearStatus = emsClearStatus;
    }

    @Column(name = "value_shipped", updatable=false)
    public Double getValueShipped() {
        return valueShipped;
    }

    public void setValueShipped(Double valueShipped) {
        this.valueShipped = valueShipped;
    }

    @Column(name = "carriage", updatable=false)
    public Double getCarriage() {
        return carriage;
    }

    public void setCarriage(Double carriage) {
        this.carriage = carriage;
    }

    @Column(name = "postfee", updatable=false)
    public Double getPostFee() {
        return postFee;
    }

    public void setPostFee(Double postFee) {
        this.postFee = postFee;
    }

    @Column(name = "receipt_date", updatable=false)
    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Column(name = "isregional", updatable=false)
    public Boolean getIsRegional() {
        return isRegional;
    }

    public void setIsRegional(Boolean regional) {
        isRegional = regional;
    }

    @Column(name = "INTERNAL_ID", updatable=false)
    public Long getInternalId() {
        return internalId;
    }

    public void setInternalId(Long internalId) {
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

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "receipt")
    public List<ScmUploadReceiptDetailHis> getDetails() {
        return details;
    }

    public void setDetails(List<ScmUploadReceiptDetailHis> details) {
        this.details = details;
    }



}

