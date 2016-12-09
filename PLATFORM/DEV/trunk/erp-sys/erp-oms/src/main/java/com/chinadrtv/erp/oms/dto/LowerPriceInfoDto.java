package com.chinadrtv.erp.oms.dto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-18
 * Time: 上午11:26
 * To change this template use File | Settings | File Templates.
 */
public class LowerPriceInfoDto implements java.io.Serializable {
    private String senddt;
    private String shipmentId;
    private String mailId;
    private String orderType;
    private String totalPrice;
    private String lowerPrice; //渠道最低价
    private String addrdesc;
    private String name;    //客户名称
    private String orderId;
    private String alertAmount; //折扣后订单金额
    private String mailPrice;   //订单运费

    public String getMailPrice() {
        return mailPrice;
    }

    public void setMailPrice(String mailPrice) {
        this.mailPrice = mailPrice;
    }

    public String getAlertAmount() {
        return alertAmount;
    }

    public void setAlertAmount(String alertAmount) {
        this.alertAmount = alertAmount;
    }

    public String getSenddt() {
        return senddt;
    }

    public void setSenddt(String senddt) {
        this.senddt = senddt;
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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(String lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public String getAddrdesc() {
        return addrdesc;
    }

    public void setAddrdesc(String addrdesc) {
        this.addrdesc = addrdesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
