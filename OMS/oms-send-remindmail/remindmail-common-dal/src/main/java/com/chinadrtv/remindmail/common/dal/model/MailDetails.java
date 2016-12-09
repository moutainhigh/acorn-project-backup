package com.chinadrtv.remindmail.common.dal.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-27
 * Time: 上午10:31
 * To change this template use File | Settings | File Templates.
 * 催送货邮件内容
 */
public class MailDetails implements java.io.Serializable{
    private String orderId;
    private String userId;
    private String userName;
    private String orderStatus;
    private String rfStatus;
    private String orderClass;
    private String appDate;
    private String applicationReason;
    private String appPsn;
    private String companyName;
    private String outHouse;
    private String mailId;
    private String name;
    private String sendDt;
    private String orderType;
    private String address;
    private String status;
    private String chkReason;
    private String chkPsn;
    private String chkDate;
    private String finishUser;
    private String finishDate;
    private String entityId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRfStatus() {
        return rfStatus;
    }

    public void setRfStatus(String rfStatus) {
        this.rfStatus = rfStatus;
    }

    public String getOrderClass() {
        return orderClass;
    }

    public void setOrderClass(String orderClass) {
        this.orderClass = orderClass;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public String getApplicationReason() {
        return applicationReason;
    }

    public void setApplicationReason(String applicationReason) {
        this.applicationReason = applicationReason;
    }

    public String getAppPsn() {
        return appPsn;
    }

    public void setAppPsn(String appPsn) {
        this.appPsn = appPsn;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOutHouse() {
        return outHouse;
    }

    public void setOutHouse(String outHouse) {
        this.outHouse = outHouse;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSendDt() {
        return sendDt;
    }

    public void setSendDt(String sendDt) {
        this.sendDt = sendDt;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChkReason() {
        return chkReason;
    }

    public void setChkReason(String chkReason) {
        this.chkReason = chkReason;
    }

    public String getChkPsn() {
        return chkPsn;
    }

    public void setChkPsn(String chkPsn) {
        this.chkPsn = chkPsn;
    }

    public String getChkDate() {
        return chkDate;
    }

    public void setChkDate(String chkDate) {
        this.chkDate = chkDate;
    }

    public String getFinishUser() {
        return finishUser;
    }

    public void setFinishUser(String finishUser) {
        this.finishUser = finishUser;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
