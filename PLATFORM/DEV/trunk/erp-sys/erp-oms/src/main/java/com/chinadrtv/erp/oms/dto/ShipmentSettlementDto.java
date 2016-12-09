package com.chinadrtv.erp.oms.dto;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-9-24
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 * 核销付款单拍平
 */
public class ShipmentSettlementDto implements java.io.Serializable {
    private Long id;
    private String createDate;
    private String rfoutdat;
    private String shipmentId;
    private String mailId;
    private Double arAmount;
    private String isSettled;
    private String companyId;
    private String paymentDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRfoutdat() {
        return rfoutdat;
    }

    public void setRfoutdat(String rfoutdat) {
        this.rfoutdat = rfoutdat;
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

    public Double getArAmount() {
        return arAmount;
    }

    public void setArAmount(Double arAmount) {
        this.arAmount = arAmount;
    }

    public String getSettled() {
        return isSettled;
    }

    public void setSettled(String settled) {
        isSettled = settled;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
