package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cuiming
 * @version 1.0
 * @since 2013-2-5 下午3:50:48
 * SCM出库单
 */
@Table(name = "SCM_UPLOAD_SHIPMENT_HEADER_HIS")
@Entity
public class ScmUploadShipmentHeaderHis implements java.io.Serializable {

	private Long ruid;
	private String shipmentId;
	private Long orderType;
	private String ncNo;
	private String sales;
	private String warehouse;
	private String issueOrganiztaion;
	private String receiptOrganization;
	private Date shippingDate;
	private String memo;
	private String otherType;
	private Long tasksNo;
	private Long upstattus;
	private Date updat;
	private String upuser;
	private Long dnstatus;
	private Date dndat;
	private String dnuser;
	private String dsc;
	private String paymentTerm;
	private String carrier;
	private String paymentMethod;
	private Long internalId;
	private Long postfee;
	private Long isiagent;
	private Long mailetype;
	private Long valueShippedLong;
	private Long carriage;
	private String shipToName;
	private String shipmenttd;
	private String batchId;
	private Date batchDate;

    private List<ScmUploadShipmentDetailHis> details = new ArrayList<ScmUploadShipmentDetailHis>();



    /**
	 * @return the ruid
	 */
	@Id
	@Column(name = "RUID")
	public Long getRuid() {
		return ruid;
	}

	/**
	 * @param ruid
	 *            the ruid to set
	 */
	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}

	/**
	 * @return the shipmentId
	 */
	@Column(name = "SHIPMENT_ID")
	public String getShipmentId() {
		return shipmentId;
	}

	/**
	 * @param shipmentId
	 *            the shipmentId to set
	 */
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	/**
	 * @return the orderType
	 */
	@Column(name = "ORDER_TYPE")
	public Long getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            the orderType to set
	 */
	public void setOrderType(Long orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return the ncNo
	 */
	@Column(name = "NC_NO")
	public String getNcNo() {
		return ncNo;
	}

	/**
	 * @param ncNo
	 *            the ncNo to set
	 */
	public void setNcNo(String ncNo) {
		this.ncNo = ncNo;
	}

	/**
	 * @return the sales
	 */
	@Column(name = "SALES")
	public String getSales() {
		return sales;
	}

	/**
	 * @param sales
	 *            the sales to set
	 */
	public void setSales(String sales) {
		this.sales = sales;
	}

	/**
	 * @return the warehouse
	 */
	@Column(name = "WAREHOUSE")
	public String getWarehouse() {
		return warehouse;
	}

	/**
	 * @param warehouse
	 *            the warehouse to set
	 */
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	/**
	 * @return the issueOrganiztaion
	 */
	@Column(name = "ISSUE_ORGANIZATION")
	public String getIssueOrganiztaion() {
		return issueOrganiztaion;
	}

	/**
	 * @param issueOrganiztaion
	 *            the issueOrganiztaion to set
	 */
	public void setIssueOrganiztaion(String issueOrganiztaion) {
		this.issueOrganiztaion = issueOrganiztaion;
	}

	/**
	 * @return the receiptOrganization
	 */
	@Column(name = "RECEIPT_ORGANIZATION")
	public String getReceiptOrganization() {
		return receiptOrganization;
	}

	/**
	 * @param receiptOrganization
	 *            the receiptOrganization to set
	 */
	public void setReceiptOrganization(String receiptOrganization) {
		this.receiptOrganization = receiptOrganization;
	}

	/**
	 * @return the shippingDate
	 */
	@Column(name = "SHIPPING_DATE")
	public Date getShippingDate() {
		return shippingDate;
	}

	/**
	 * @param shippingDate
	 *            the shippingDate to set
	 */
	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	/**
	 * @return the memo
	 */
	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the otherType
	 */
	@Column(name = "OTHER_TYPE")
	public String getOtherType() {
		return otherType;
	}

	/**
	 * @param otherType
	 *            the otherType to set
	 */
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	/**
	 * @return the tasksNo
	 */
	@Column(name = "TASKSNO")
	public Long getTasksNo() {
		return tasksNo;
	}

	/**
	 * @param tasksNo
	 *            the tasksNo to set
	 */
	public void setTasksNo(Long tasksNo) {
		this.tasksNo = tasksNo;
	}

	/**
	 * @return the upstattus
	 */
	@Column(name = "UPSTATUS")
	public Long getUpstattus() {
		return upstattus;
	}

	/**
	 * @param upstattus
	 *            the upstattus to set
	 */
	public void setUpstattus(Long upstattus) {
		this.upstattus = upstattus;
	}

	/**
	 * @return the updat
	 */
	@Column(name = "UPDAT")
	public Date getUpdat() {
		return updat;
	}

	/**
	 * @param updat
	 *            the updat to set
	 */
	public void setUpdat(Date updat) {
		this.updat = updat;
	}

	/**
	 * @return the upuser
	 */
	@Column(name = "UPUSER")
	public String getUpuser() {
		return upuser;
	}

	/**
	 * @param upuser
	 *            the upuser to set
	 */
	public void setUpuser(String upuser) {
		this.upuser = upuser;
	}

	/**
	 * @return the dnstatus
	 */
	@Column(name = "DNSTATUS")
	public Long getDnstatus() {
		return dnstatus;
	}

	/**
	 * @param dnstatus
	 *            the dnstatus to set
	 */
	public void setDnstatus(Long dnstatus) {
		this.dnstatus = dnstatus;
	}

	/**
	 * @return the dndat
	 */
	@Column(name = "DNDAT")
	public Date getDndat() {
		return dndat;
	}

	/**
	 * @param dndat
	 *            the dndat to set
	 */
	public void setDndat(Date dndat) {
		this.dndat = dndat;
	}

	/**
	 * @return the dnuser
	 */
	@Column(name = "DNUSER")
	public String getDnuser() {
		return dnuser;
	}

	/**
	 * @param dnuser
	 *            the dnuser to set
	 */
	public void setDnuser(String dnuser) {
		this.dnuser = dnuser;
	}

	/**
	 * @return the dsc
	 */
	@Column(name = "DSC")
	public String getDsc() {
		return dsc;
	}

	/**
	 * @param dsc
	 *            the dsc to set
	 */
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	/**
	 * @return the paymentTerm
	 */
	@Column(name = "PAYMENT_TERM")
	public String getPaymentTerm() {
		return paymentTerm;
	}

	/**
	 * @param paymentTerm
	 *            the paymentTerm to set
	 */
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	/**
	 * @return the carrier
	 */
	@Column(name = "CARRIER")
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier
	 *            the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return the paymentMethod
	 */
	@Column(name = "PAYMENT_METHOD")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the internalId
	 */
	@Column(name = "INTERNAL_ID")
	public Long getInternalId() {
		return internalId;
	}

	/**
	 * @param internalId
	 *            the internalId to set
	 */
	public void setInternalId(Long internalId) {
		this.internalId = internalId;
	}

	/**
	 * @return the postfee
	 */
	@Column(name = "POSTFEE")
	public Long getPostfee() {
		return postfee;
	}

	/**
	 * @param postfee
	 *            the postfee to set
	 */
	public void setPostfee(Long postfee) {
		this.postfee = postfee;
	}

	/**
	 * @return the isiagent
	 */
	@Column(name = "ISIAGENT")
	public Long getIsiagent() {
		return isiagent;
	}

	/**
	 * @param isiagent
	 *            the isiagent to set
	 */
	public void setIsiagent(Long isiagent) {
		this.isiagent = isiagent;
	}

	/**
	 * @return the mailetype
	 */
	@Column(name = "MAILETYPE")
	public Long getMailetype() {
		return mailetype;
	}

	/**
	 * @param mailetype
	 *            the mailetype to set
	 */
	public void setMailetype(Long mailetype) {
		this.mailetype = mailetype;
	}

	/**
	 * @return the valueShippedLong
	 */
	@Column(name = "VALUE_SHIPPED")
	public Long getValueShippedLong() {
		return valueShippedLong;
	}

	/**
	 * @param valueShippedLong
	 *            the valueShippedLong to set
	 */
	public void setValueShippedLong(Long valueShippedLong) {
		this.valueShippedLong = valueShippedLong;
	}

	/**
	 * @return the carriage
	 */
	@Column(name = "CARRIAGE")
	public Long getCarriage() {
		return carriage;
	}

	/**
	 * @param carriage
	 *            the carriage to set
	 */
	public void setCarriage(Long carriage) {
		this.carriage = carriage;
	}

	/**
	 * @return the shipToName
	 */
	@Column(name = "SHIP_TO_NAME")
	public String getShipToName() {
		return shipToName;
	}

	/**
	 * @param shipToName
	 *            the shipToName to set
	 */
	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	/**
	 * @return the shipmenttd
	 */
	@Column(name = "SHIPMENTID")
	public String getShipmenttd() {
		return shipmenttd;
	}

	/**
	 * @param shipmenttd
	 *            the shipmenttd to set
	 */
	public void setShipmenttd(String shipmenttd) {
		this.shipmenttd = shipmenttd;
	}

	public ScmUploadShipmentHeaderHis() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the batchId
	 */
	@Column(name = "BATCH_ID")
	public String getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	/**
	 * @return the batchDate
	 */
	@Column(name = "BATCH_DATE")
	public Date getBatchDate() {
		return batchDate;
	}

	/**
	 * @param batchDate
	 *            the batchDate to set
	 */
	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "shipment")
    public List<ScmUploadShipmentDetailHis> getDetails() {
        return details;
    }

    public void setDetails(List<ScmUploadShipmentDetailHis> details) {
        this.details = details;
    }

}
