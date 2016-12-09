package com.chinadrtv.erp.tc.core.dto;

import com.chinadrtv.erp.model.AddressExt;

import java.util.Date;
import java.util.List;

/**
 * 订单查询参数
 * User: 徐志凯
 * Date: 13-5-13
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderQueryDto implements java.io.Serializable {
    private String orderId;
    private String parentOrderId;
    //private String shipmentId;//mailId
    private String mailId;
    private String contactId;
    private String contactName;
    private String crUsr;
    private AddressExt addressExt;
    private Date beginCrdt;
    private Date endCrdt;
    private Date beginOutdt;
    private Date endOutdt;
    private String status;
    private String payType;
    private String prodId;
    private List<String> contactIdList;
    private String trackUsr;

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    private String confirm;

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }

    private Integer checkResult;

    public List<Integer> getCheckResultList() {
        return checkResultList;
    }

    public void setCheckResultList(List<Integer> checkResultList) {
        this.checkResultList = checkResultList;
    }

    private List<Integer> checkResultList;

    public Integer getSortCrDate() {
        return sortCrDate;
    }

    public void setSortCrDate(Integer sortCrDate) {
        this.sortCrDate = sortCrDate;
    }

    public Boolean isCheckCrDate() {
        return isCheckCrDate;
    }

    public void setCheckCrDate(Boolean checkCrDate) {
        isCheckCrDate = checkCrDate;
    }

    /**
     * 是否检查创建日期限制
     */
    private Boolean isCheckCrDate;

    /**
     * 创建日期排序
     * 1正序 -1倒序
     */
    private Integer sortCrDate;

    public OrderQueryDto()
    {
        isCheckCrDate=null;
        sortCrDate=null;
    }

    public List<String> getGetContactIdList() {
        return getContactIdList;
    }

    public void setGetContactIdList(List<String> getContactIdList) {
        this.getContactIdList = getContactIdList;
    }

    private List<String> getContactIdList;

    public List<String> getPaytypeList() {
        return paytypeList;
    }

    public void setPaytypeList(List<String> paytypeList) {
        this.paytypeList = paytypeList;
    }

    private List<String> paytypeList;

    public String getGetContactName() {
        return getContactName;
    }

    public void setGetContactName(String getContactName) {
        this.getContactName = getContactName;
    }

    public String getGetContactPhone() {
        return getContactPhone;
    }

    public void setGetContactPhone(String getContactPhone) {
        this.getContactPhone = getContactPhone;
    }

    //TODO:
    private String getContactName;
    private String getContactPhone;

    private int startPos;
    private int pageSize;

    public List<String> getContactIdList() {
        return contactIdList;
    }

    public void setContactIdList(List<String> contactIdList) {
        this.contactIdList = contactIdList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    /*public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }*/


    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCrUsr() {
        return crUsr;
    }

    public void setCrUsr(String crUsr) {
        this.crUsr = crUsr;
    }

    public AddressExt getAddressExt() {
        return addressExt;
    }

    public void setAddressExt(AddressExt addressExt) {
        this.addressExt = addressExt;
    }

    public Date getBeginCrdt() {
        return beginCrdt;
    }

    public void setBeginCrdt(Date beginCrdt) {
        this.beginCrdt = beginCrdt;
    }

    public Date getEndCrdt() {
        return endCrdt;
    }

    public void setEndCrdt(Date endCrdt) {
        this.endCrdt = endCrdt;
    }

    public Date getBeginOutdt() {
        return beginOutdt;
    }

    public void setBeginOutdt(Date beginOutdt) {
        this.beginOutdt = beginOutdt;
    }

    public Date getEndOutdt() {
        return endOutdt;
    }

    public void setEndOutdt(Date endOutdt) {
        this.endOutdt = endOutdt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTrackUsr() {
        return trackUsr;
    }

    public void setTrackUsr(String trackUsr) {
        this.trackUsr = trackUsr;
    }

}
