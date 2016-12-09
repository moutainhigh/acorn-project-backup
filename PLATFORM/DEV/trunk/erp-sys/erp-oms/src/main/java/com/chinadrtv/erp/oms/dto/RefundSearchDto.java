package com.chinadrtv.erp.oms.dto;

/**
 * 顾客半签收订单查询参数
 * User: 徐志凯
 * Date: 13-3-27
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class RefundSearchDto implements java.io.Serializable {
    public RefundSearchDto()
    {
        page=1;
    }
    private Integer page;
    private Integer rows;
    private String orderId;
    private String contactId;
    private String mailId;
    private String phone;
    private String entityId;

    public RefundSearchDto(Integer page, Integer rows, String orderId, String contactId, String mailId, String phone, String entityId) {
        this.page = page;
        this.rows = rows;
        this.orderId = orderId;
        this.contactId = contactId;
        this.mailId = mailId;
        this.phone = phone;
        this.entityId = entityId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
