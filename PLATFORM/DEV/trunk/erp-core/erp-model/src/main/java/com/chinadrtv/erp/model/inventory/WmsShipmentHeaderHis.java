package com.chinadrtv.erp.model.inventory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-26
 * Time: 上午9:37
 * To change this template use File | Settings | File Templates.
 */
@Table(name="WMS_SHIPMENT_HEADER_HIS")
@Entity
public class WmsShipmentHeaderHis implements java.io.Serializable {
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
    private Date orderDateTime;
    private Date scheduleShipDate;
    private String paymentTerm;
    private String memo;
    private String certificationCode;
    private String carrierType;
    private String carrier;
    private Integer orderTypper;
    private String warehouse;
    private String upuser ;
    private String batchId;
    private Date batchDate;
    private List<WmsShipmentDetailHis> details = new ArrayList<WmsShipmentDetailHis>();


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_WMS_SHIPMENT_HEADER")
    @SequenceGenerator(name = "S_WMS_SHIPMENT_HEADER", sequenceName = "S_WMS_SHIPMENT_HEADER")
    @Column(name = "RUID")
    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    @Column(name = "PAYMENT_METHOD", updatable = false)
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Column(name = "SHIPMENT_ID", updatable = false)
    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Column(name = "REVISON", updatable = false)
    public String getRevison() {
        return revison;
    }

    public void setRevison(String revison) {
        this.revison = revison;
    }

    @Column(name = "CUSTOMER", updatable = false)
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "SHIP_TO_NAME", updatable = false)
    public String getShipToName() {
        return shipToName;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    @Column(name = "SHIP_TO_ADDRESS1", updatable = false)
    public String getShipToAddress1() {
        return shipToAddress1;
    }

    public void setShipToAddress1(String shipToAddress1) {
        this.shipToAddress1 = shipToAddress1;
    }

    @Column(name = "FREIGHT_BILL_TO", updatable = false)
    public String getFreightBillTo() {
        return freightBillTo;
    }

    public void setFreightBillTo(String freightBillTo) {
        this.freightBillTo = freightBillTo;
    }

    @Column(name = "SHIP_TO_ADDRESS2", updatable = false)
    public String getShipToAddress2() {
        return shipToAddress2;
    }

    public void setShipToAddress2(String shipToAddress2) {
        this.shipToAddress2 = shipToAddress2;
    }

    @Column(name = "SHIP_TO_POSTAL_CODE", updatable = false)
    public String getShipToPostalCode() {
        return shipToPostalCode;
    }

    public void setShipToPostalCode(String shipToPostalCode) {
        this.shipToPostalCode = shipToPostalCode;
    }

    @Column(name = "SHIP_TO_CITY", updatable = false)
    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    @Column(name = "SHIP_TO_COUNTRY", updatable = false)
    public String getShipToCountry() {
        return shipToCountry;
    }

    public void setShipToCountry(String shipToCountry) {
        this.shipToCountry = shipToCountry;
    }

    @Column(name = "HOME_PHONE_NUM", updatable = false)
    public String getHomePhoneNum() {
        return homePhoneNum;
    }

    public void setHomePhoneNum(String homePhoneNum) {
        this.homePhoneNum = homePhoneNum;
    }

    @Column(name = "MOBILE_PHONE_NUM", updatable = false)
    public String getMobilePhoneNum() {
        return mobilePhoneNum;
    }

    public void setMobilePhoneNum(String mobilePhoneNum) {
        this.mobilePhoneNum = mobilePhoneNum;
    }

    @Column(name = "OFFICE_PHONE_NUM", updatable = false)
    public String getOfficePhoneNum() {
        return officePhoneNum;
    }

    public void setOfficePhoneNum(String officePhoneNum) {
        this.officePhoneNum = officePhoneNum;
    }

    @Column(name = "ISSUE_ORGANIZATION", updatable = false)
    public String getIssueOrganization() {
        return issueOrganization;
    }

    public void setIssueOrganization(String issueOrganization) {
        this.issueOrganization = issueOrganization;
    }

    @Column(name = "RECEIPT_ORGANIZATION", updatable = false)
    public String getReceiptOrganization() {
        return receiptOrganization;
    }

    public void setReceiptOrganization(String receiptOrganization) {
        this.receiptOrganization = receiptOrganization;
    }

    @Column(name = "TRANSPORTATION_MODE", updatable = false)
    public String getTransportationMode() {
        return transportationMode;
    }

    public void setTransportationMode(String transportationMode) {
        this.transportationMode = transportationMode;
    }

    @Column(name = "SHIPMENT_TYPE", updatable = false)
    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    @Column(name = "ORDER_DATETIME", updatable = false)
    public Date getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    @Column(name = "SCHEDULE_SHIP_DATE", updatable = false)
    public Date getScheduleShipDate() {
        return scheduleShipDate;
    }

    public void setScheduleShipDate(Date scheduleShipDate) {
        this.scheduleShipDate = scheduleShipDate;
    }

    @Column(name = "PAYMENT_TERM", updatable = false)
    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    @Column(name = "MEMO", updatable = false)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "CERTIFICATION_CODE", updatable = false)
    public String getCertificationCode() {
        return certificationCode;
    }

    public void setCertificationCode(String certificationCode) {
        this.certificationCode = certificationCode;
    }

    @Column(name = "CARRIER_TYPE", updatable = false)
    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    @Column(name = "CARRIER", updatable = false)
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Column(name = "ORDER_TYPER", updatable = false)
    public Integer getOrderTypper() {
        return orderTypper;
    }

    public void setOrderTypper(Integer orderTypper) {
        this.orderTypper = orderTypper;
    }

    @Column(name = "WAREHOUSE", updatable = false)
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
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
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "shipment")
    public List<WmsShipmentDetailHis> getDetails() {
        return details;
    }

    public void setDetails(List<WmsShipmentDetailHis> details) {
        this.details = details;
    }

    @Column(name = "UPUSER")
    public String getUpuser() {
        return upuser;
    }

    public void setUpuser(String upuser) {
        this.upuser = upuser;
    }
}

