package com.chinadrtv.logistics.dal.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-5-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OmsBackorder implements Serializable {
    private String orderid;
    private Date crdt;
    private String status;
    private String reason;
    private String iscan;
    private String scmreason;
    private Date expired;
    private String prod;
    private String crusr;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIscan() {
        return iscan;
    }

    public void setIscan(String iscan) {
        this.iscan = iscan;
    }

    public String getScmreason() {
        return scmreason;
    }

    public void setScmreason(String scmreason) {
        this.scmreason = scmreason;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }
}
