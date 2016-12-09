package com.chinadrtv.logistics.dal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

public class OrderLogistics
        implements Serializable
{
    private String orderId;
    private Date opDate;
    private String opDsc;
    private String opUser;
    private String mailId;
    private String company;

    public String getOrderId()
    {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+08:00")
    public Date getOpDate() {
        return this.opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }

    public String getOpDsc() {
        return this.opDsc;
    }

    public void setOpDsc(String opDsc) {
        this.opDsc = opDsc;
    }

    public String getOpUser() {
        return this.opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getMailId() {
        return this.mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}