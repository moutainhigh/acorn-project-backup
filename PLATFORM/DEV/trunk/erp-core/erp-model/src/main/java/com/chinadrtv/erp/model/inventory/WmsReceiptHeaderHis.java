package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: gaodejian
 * Date: 13-2-5
 * Time: 上午11:41
 * To change this template use File | Settings | File Templates.
 * 收货通知单
 */
@Table(name="WMS_RECEIPT_HEADER_HIS")
@Entity
public class WmsReceiptHeaderHis implements java.io.Serializable {

    private Long ruid;
    private String receiptId;
    private String receiptType;
    private String warehouse;
    private String sourceId;
    private String sourceName;
    private String sourceAddress1;
    private String sourcePostalCode;
    private String sourceCity;
    private String sourceCountry;
    private String sourcePhoneNum;
    private String assistant;
    private Date scheduledDateTime;
    private String contract;
    private String issueOrganization;
    private String receiptOrganization;
    private String memo;
    private Long tasksNo;
    private Long upstatus ;                 //上传状态1上传中2上传完成
    private Date update;
    private String upuser;
    private Boolean dnstatus;
    private Date dndate;
    private String dnuser;
    private String  dsc;
    private Date inactiveDate;
    private String otherType;
    private String carrier;
    private String paymentMethod;
    private String containerId;
    private Boolean isIAgent;
    private Long maileType;
    private Long emsClearStatus;
    private Double valueShipped;
    private Double carriage;
    private Double postFee;
    private String batchId;
    private Date batchDate;
    private List<WmsReceiptDetailHis> details = new ArrayList<WmsReceiptDetailHis>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_WMS_RECEIPT_HEADER")
    @SequenceGenerator(name = "S_WMS_RECEIPT_HEADER", sequenceName = "S_WMS_RECEIPT_HEADER")
    @Column(name = "RUID")
    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    @Column(name = "RECEIPT_ID", unique = true, updatable=false)
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

    @Column(name = "source_name", updatable=false)
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Column(name = "source_address1", updatable=false)
    public String getSourceAddress1() {
        return sourceAddress1;
    }

    public void setSourceAddress1(String sourceAddress1) {
        this.sourceAddress1 = sourceAddress1;
    }

    @Column(name = "source_postal_code", updatable=false)
    public String getSourcePostalCode() {
        return sourcePostalCode;
    }

    public void setSourcePostalCode(String sourcePostalCode) {
        this.sourcePostalCode = sourcePostalCode;
    }

    @Column(name = "source_city", updatable=false)
    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    @Column(name = "source_country", updatable=false)
    public String getSourceCountry() {
        return sourceCountry;
    }

    public void setSourceCountry(String sourceCountry) {
        this.sourceCountry = sourceCountry;
    }

    @Column(name = "source_phone_num", updatable=false)
    public String getSourcePhoneNum() {
        return sourcePhoneNum;
    }

    public void setSourcePhoneNum(String sourcePhoneNum) {
        this.sourcePhoneNum = sourcePhoneNum;
    }

    @Column(name = "assistant", updatable=false)
    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    @Column(name = "scheduled_date_time", updatable=false)
    public Date getScheduledDateTime() {
        return scheduledDateTime;
    }

    public void setScheduledDateTime(Date scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
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

    @Column(name = "inactive_date", updatable=false)
    public Date getInactiveDate() {
        return inactiveDate;
    }

    public void setInactiveDate(Date inactiveDate) {
        this.inactiveDate = inactiveDate;
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

    @Column(name = "payment_method", updatable=false)
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Column(name = "container_id", updatable=false)
    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
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

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "receipt")
    public List<WmsReceiptDetailHis> getDetails() {
        return details;
    }

    public void setDetails(List<WmsReceiptDetailHis> details) {
        this.details = details;
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
}

