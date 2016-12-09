package com.chinadrtv.erp.sales.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-31
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MyOrderFrontAuditDto extends MyOrderFrontDto {
    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getAuditstatus() {
        return auditstatus;
    }

    public void setAuditstatus(String auditstatus) {
        this.auditstatus = auditstatus;
    }

    private String batchid;
    private String auditstatus;

    public String getAudittype() {
        return audittype;
    }

    public void setAudittype(String audittype) {
        this.audittype = audittype;
    }

    private String audittype;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getAuditdt() {
        return auditdt;
    }

    public void setAuditdt(Date auditdt) {
        this.auditdt = auditdt;
    }

    private Date auditdt;

    public String getBpmCrtUsr() {
        return bpmCrtUsr;
    }

    public void setBpmCrtUsr(String bpmCrtUsr) {
        this.bpmCrtUsr = bpmCrtUsr;
    }

    private String bpmCrtUsr;


}
