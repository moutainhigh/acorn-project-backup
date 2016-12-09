package com.chinadrtv.erp.model.trade;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
* WMS发货信息
* User: 王健
* Date: 2013-2-18
* 橡果国际-系统集成部
* Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
*/
@Entity
@Table(name = "WMS_SHIPMENT_HEADER", schema = "ACOAPP_DBTRANSF")
public class WmsShipmentHeader implements java.io.Serializable {

	private static final long serialVersionUID = 2162737434233880057L;
	private Long ruid;
	private String paymentMethod;
	private String shipmentId;
	private String revison;
	private String customer;
	private String shipToName;
	private String shipToAddress1;
	private String freightBillTo;
	private String shipToAddress2;
	private String shipToPostalCode;
	private String shipToCity;
	private String shipToCountry;
	private String homePhoneNum;
	private String mobilePhoneNum;
	private String officePhoneNum;
	private String issueOrganization;
	private String receiptOrganization;
	private String transportationMode;
	private String shipmentType;
	private Date orderDatetime;
	private Date scheduleShipDate;
	private String paymentTerm;
	private String memo;
	private String certificationCode;
	private String carrierType;
	private String carrier;
	private Integer orderTyper;
	private String invoiceType;
	private String invoiceTitle;
	private BigDecimal valueShipped;
	private String warehouse;
	private String sales;
	private BigDecimal carriage;
	private Integer tasksno;
	private Integer upstatus;
	private Date updat;
	private String upuser;
	private Boolean dnstatus;
	private Date dndat;
	private String dnuser;
	private String dsc;
	private String otherType;
	private Boolean isiagent;
	private Integer mailetype;
	private BigDecimal postfee;
	private Boolean allowRepeat;
	private String batchId;
	private Date batchDate;
    private String shipToProvinceName;
    private String shipToCityName;
    private String shipToCountyName;

	// Constructors

	/** default constructor */
	public WmsShipmentHeader() {
	}

	/** minimal constructor */
	public WmsShipmentHeader(Integer tasksno, Integer upstatus, Date updat,
			Boolean dnstatus, Boolean allowRepeat) {
		this.tasksno = tasksno;
		this.upstatus = upstatus;
		this.updat = updat;
		this.dnstatus = dnstatus;
		this.allowRepeat = allowRepeat;
	}

	/** full constructor */
	public WmsShipmentHeader(String paymentMethod, String shipmentId,
			String revison, String customer, String shipToName,
			String shipToAddress1, String freightBillTo, String shipToAddress2,
			String shipToPostalCode, String shipToCity, String shipToCountry,
			String homePhoneNum, String mobilePhoneNum, String officePhoneNum,
			String issueOrganization, String receiptOrganization,
			String transportationMode, String shipmentType, Date orderDatetime,
			Date scheduleShipDate, String paymentTerm, String memo,
			String certificationCode, String carrierType, String carrier,
			Integer orderTyper, String invoiceType, String invoiceTitle,
			BigDecimal valueShipped, String warehouse, String sales,
			BigDecimal carriage, Integer tasksno, Integer upstatus, Date updat,
			String upuser, Boolean dnstatus, Date dndat, String dnuser,
			String dsc, String otherType, Boolean isiagent,
			Integer mailetype, BigDecimal postfee, Boolean allowRepeat,
			String batchId, Date batchDate) {
		this.paymentMethod = paymentMethod;
		this.shipmentId = shipmentId;
		this.revison = revison;
		this.customer = customer;
		this.shipToName = shipToName;
		this.shipToAddress1 = shipToAddress1;
		this.freightBillTo = freightBillTo;
		this.shipToAddress2 = shipToAddress2;
		this.shipToPostalCode = shipToPostalCode;
		this.shipToCity = shipToCity;
		this.shipToCountry = shipToCountry;
		this.homePhoneNum = homePhoneNum;
		this.mobilePhoneNum = mobilePhoneNum;
		this.officePhoneNum = officePhoneNum;
		this.issueOrganization = issueOrganization;
		this.receiptOrganization = receiptOrganization;
		this.transportationMode = transportationMode;
		this.shipmentType = shipmentType;
		this.orderDatetime = orderDatetime;
		this.scheduleShipDate = scheduleShipDate;
		this.paymentTerm = paymentTerm;
		this.memo = memo;
		this.certificationCode = certificationCode;
		this.carrierType = carrierType;
		this.carrier = carrier;
		this.orderTyper = orderTyper;
		this.invoiceType = invoiceType;
		this.invoiceTitle = invoiceTitle;
		this.valueShipped = valueShipped;
		this.warehouse = warehouse;
		this.sales = sales;
		this.carriage = carriage;
		this.tasksno = tasksno;
		this.upstatus = upstatus;
		this.updat = updat;
		this.upuser = upuser;
		this.dnstatus = dnstatus;
		this.dndat = dndat;
		this.dnuser = dnuser;
		this.dsc = dsc;
		this.otherType = otherType;
		this.isiagent = isiagent;
		this.mailetype = mailetype;
		this.postfee = postfee;
		this.allowRepeat = allowRepeat;
		this.batchId = batchId;
		this.batchDate = batchDate;
	}

	@Id
	@Column(name = "RUID", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_wms_shipment_header")
    @SequenceGenerator(name = "s_wms_shipment_header", sequenceName = "s_wms_shipment_header")
	public Long getRuid() {
		return this.ruid;
	}

	public void setRuid(Long ruid) {
		this.ruid = ruid;
	}

	@Column(name = "PAYMENT_METHOD", length = 20)
	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "SHIPMENT_ID", length = 20)
	public String getShipmentId() {
		return this.shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	@Column(name = "REVISON", length = 10)
	public String getRevison() {
		return this.revison;
	}

	public void setRevison(String revison) {
		this.revison = revison;
	}

	@Column(name = "CUSTOMER", length = 40)
	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Column(name = "SHIP_TO_NAME", length = 50)
	public String getShipToName() {
		return this.shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	@Column(name = "SHIP_TO_ADDRESS1", length = 200)
	public String getShipToAddress1() {
		return this.shipToAddress1;
	}

	public void setShipToAddress1(String shipToAddress1) {
		this.shipToAddress1 = shipToAddress1;
	}

	@Column(name = "FREIGHT_BILL_TO", length = 25)
	public String getFreightBillTo() {
		return this.freightBillTo;
	}

	public void setFreightBillTo(String freightBillTo) {
		this.freightBillTo = freightBillTo;
	}

	@Column(name = "SHIP_TO_ADDRESS2", length = 200)
	public String getShipToAddress2() {
		return this.shipToAddress2;
	}

	public void setShipToAddress2(String shipToAddress2) {
		this.shipToAddress2 = shipToAddress2;
	}

	@Column(name = "SHIP_TO_POSTAL_CODE", length = 25)
	public String getShipToPostalCode() {
		return this.shipToPostalCode;
	}

	public void setShipToPostalCode(String shipToPostalCode) {
		this.shipToPostalCode = shipToPostalCode;
	}

	@Column(name = "SHIP_TO_CITY", length = 30)
	public String getShipToCity() {
		return this.shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	@Column(name = "SHIP_TO_COUNTRY", length = 25)
	public String getShipToCountry() {
		return this.shipToCountry;
	}

	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}

	@Column(name = "HOME_PHONE_NUM", length = 100)
	public String getHomePhoneNum() {
		return this.homePhoneNum;
	}

	public void setHomePhoneNum(String homePhoneNum) {
		this.homePhoneNum = homePhoneNum;
	}

	@Column(name = "MOBILE_PHONE_NUM", length = 100)
	public String getMobilePhoneNum() {
		return this.mobilePhoneNum;
	}

	public void setMobilePhoneNum(String mobilePhoneNum) {
		this.mobilePhoneNum = mobilePhoneNum;
	}

	@Column(name = "OFFICE_PHONE_NUM", length = 100)
	public String getOfficePhoneNum() {
		return this.officePhoneNum;
	}

	public void setOfficePhoneNum(String officePhoneNum) {
		this.officePhoneNum = officePhoneNum;
	}

	@Column(name = "ISSUE_ORGANIZATION", length = 20)
	public String getIssueOrganization() {
		return this.issueOrganization;
	}

	public void setIssueOrganization(String issueOrganization) {
		this.issueOrganization = issueOrganization;
	}

	@Column(name = "RECEIPT_ORGANIZATION", length = 20)
	public String getReceiptOrganization() {
		return this.receiptOrganization;
	}

	public void setReceiptOrganization(String receiptOrganization) {
		this.receiptOrganization = receiptOrganization;
	}

	@Column(name = "TRANSPORTATION_MODE", length = 25)
	public String getTransportationMode() {
		return this.transportationMode;
	}

	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}

	@Column(name = "SHIPMENT_TYPE", length = 25)
	public String getShipmentType() {
		return this.shipmentType;
	}

	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	@Column(name = "ORDER_DATETIME", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getOrderDatetime() {
		return this.orderDatetime;
	}

	public void setOrderDatetime(Date orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	@Column(name = "SCHEDULE_SHIP_DATE", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getScheduleShipDate() {
		return this.scheduleShipDate;
	}

	public void setScheduleShipDate(Date scheduleShipDate) {
		this.scheduleShipDate = scheduleShipDate;
	}

	@Column(name = "PAYMENT_TERM", length = 100)
	public String getPaymentTerm() {
		return this.paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	@Column(name = "MEMO", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "CERTIFICATION_CODE", length = 25)
	public String getCertificationCode() {
		return this.certificationCode;
	}

	public void setCertificationCode(String certificationCode) {
		this.certificationCode = certificationCode;
	}

	@Column(name = "CARRIER_TYPE", length = 25)
	public String getCarrierType() {
		return this.carrierType;
	}

	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}

	@Column(name = "CARRIER", length = 25)
	public String getCarrier() {
		return this.carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	@Column(name = "ORDER_TYPER", precision = 2, scale = 0)
	public Integer getOrderTyper() {
		return this.orderTyper;
	}

	public void setOrderTyper(Integer orderTyper) {
		this.orderTyper = orderTyper;
	}

	@Column(name = "INVOICE_TYPE", length = 25)
	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Column(name = "INVOICE_TITLE", length = 50)
	public String getInvoiceTitle() {
		return this.invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	@Column(name = "VALUE_SHIPPED", scale = 5)
	public BigDecimal getValueShipped() {
		return this.valueShipped;
	}

	public void setValueShipped(BigDecimal valueShipped) {
		this.valueShipped = valueShipped;
	}

	@Column(name = "WAREHOUSE", length = 20)
	public String getWarehouse() {
		return this.warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Column(name = "SALES", length = 20)
	public String getSales() {
		return this.sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	@Column(name = "CARRIAGE", precision = 8)
	public BigDecimal getCarriage() {
		return this.carriage;
	}

	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}

	@Column(name = "TASKSNO", nullable = false, precision = 22, scale = 0)
	public Integer getTasksno() {
		return this.tasksno;
	}

	public void setTasksno(Integer tasksno) {
		this.tasksno = tasksno;
	}

	@Column(name = "UPSTATUS", nullable = false, precision = 1, scale = 0)
	public Integer getUpstatus() {
		return this.upstatus;
	}

	public void setUpstatus(Integer upstatus) {
		this.upstatus = upstatus;
	}

	@Column(name = "UPDAT", nullable = false, length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getUpdat() {
		return this.updat;
	}

	public void setUpdat(Date updat) {
		this.updat = updat;
	}

	@Column(name = "UPUSER", length = 30)
	public String getUpuser() {
		return this.upuser;
	}

	public void setUpuser(String upuser) {
		this.upuser = upuser;
	}

	@Column(name = "DNSTATUS", nullable = false, precision = 1, scale = 0)
	public Boolean getDnstatus() {
		return this.dnstatus;
	}

	public void setDnstatus(Boolean dnstatus) {
		this.dnstatus = dnstatus;
	}

	@Column(name = "DNDAT", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getDndat() {
		return this.dndat;
	}

	public void setDndat(Date dndat) {
		this.dndat = dndat;
	}

	@Column(name = "DNUSER", length = 30)
	public String getDnuser() {
		return this.dnuser;
	}

	public void setDnuser(String dnuser) {
		this.dnuser = dnuser;
	}

	@Column(name = "DSC", length = 2000)
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	@Column(name = "OTHER_TYPE", length = 20)
	public String getOtherType() {
		return this.otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	@Column(name = "ISIAGENT", precision = 1, scale = 0)
	public Boolean getIsiagent() {
		return this.isiagent;
	}

	public void setIsiagent(Boolean isiagent) {
		this.isiagent = isiagent;
	}

	@Column(name = "MAILETYPE", precision = 22, scale = 0)
	public Integer getMailetype() {
		return this.mailetype;
	}

	public void setMailetype(Integer mailetype) {
		this.mailetype = mailetype;
	}

	@Column(name = "POSTFEE", precision = 10)
	public BigDecimal getPostfee() {
		return this.postfee;
	}

	public void setPostfee(BigDecimal postfee) {
		this.postfee = postfee;
	}

	@Column(name = "ALLOW_REPEAT", nullable = false, precision = 1, scale = 0)
	public Boolean getAllowRepeat() {
		return this.allowRepeat;
	}

	public void setAllowRepeat(Boolean allowRepeat) {
		this.allowRepeat = allowRepeat;
	}

	@Column(name = "BATCH_ID", length = 50)
	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Column(name = "BATCH_DATE", length = 7)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Temporal(TemporalType.TIMESTAMP)
	public Date getBatchDate() {
		return this.batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}

    @Column(name = "SHIP_TO_PROVINCENAME", length = 25)
    public String getShipToProvinceName() {
        return shipToProvinceName;
    }

    public void setShipToProvinceName(String shipToProvinceName) {
        this.shipToProvinceName = shipToProvinceName;
    }

    @Column(name = "SHIP_TO_CITYNAME", length = 25)
    public String getShipToCityName() {
        return shipToCityName;
    }

    public void setShipToCityName(String shipToCityName) {
        this.shipToCityName = shipToCityName;
    }

    @Column(name = "SHIP_TO_COUNTYNAME", length = 25)
    public String getShipToCountyName() {
        return shipToCountyName;
    }

    public void setShipToCountyName(String shipToCountyName) {
        this.shipToCountyName = shipToCountyName;
    }


}