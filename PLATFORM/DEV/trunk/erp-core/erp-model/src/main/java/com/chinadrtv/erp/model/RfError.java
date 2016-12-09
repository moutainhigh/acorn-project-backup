package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 出库扫描发送短信错误日志 (TC).
 * User: 徐志凯
 * Date: 13-2-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "RF_ERROR", schema = "ACOAPP_MONIAGT")
@Entity
public class RfError implements Serializable {
    private Long logId;
    private Integer logRunid;
    private Integer logType;
    private String logAppend;
    private Date logDate;
    private String orderid;
    private String mailid;
    private String outpsn;
    private Integer errtype;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RF_ERROR_SEQ")
    @SequenceGenerator(name = "RF_ERROR_SEQ", sequenceName = "ACOAPP_MONIAGT.RF_ERROR_SE")
    @Column(name = "LOG_ID")
    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    @Column(name = "LOG_RUNID")
    public Integer getLogRunid() {
        return logRunid;
    }

    public void setLogRunid(Integer logRunid) {
        this.logRunid = logRunid;
    }

    @Column(name = "LOG_TYPE")
    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }


    @Column(name = "LOG_APPEND", length = 2000)
    public String getLogAppend() {
        return logAppend;
    }

    public void setLogAppend(String logAppend) {
        this.logAppend = logAppend;
    }


    @Column(name = "LOG_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    @Column(name = "ORDERID", length = 20)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "MAILID", length = 40)
    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    @Column(name = "OUTPSN", length = 100)
    public String getOutpsn() {
        return outpsn;
    }

    public void setOutpsn(String outpsn) {
        this.outpsn = outpsn;
    }


    @Column(name = "ERRTYPE", length = 22)
    public Integer getErrtype() {
        return errtype;
    }

    public void setErrtype(Integer errtype) {
        this.errtype = errtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RfError rfError = (RfError) o;

        if (errtype != null ? !errtype.equals(rfError.errtype) : rfError.errtype != null) return false;
        if (logAppend != null ? !logAppend.equals(rfError.logAppend) : rfError.logAppend != null) return false;
        if (logDate != null ? !logDate.equals(rfError.logDate) : rfError.logDate != null) return false;
        if (logId != null ? !logId.equals(rfError.logId) : rfError.logId != null) return false;
        if (logRunid != null ? !logRunid.equals(rfError.logRunid) : rfError.logRunid != null) return false;
        if (logType != null ? !logType.equals(rfError.logType) : rfError.logType != null) return false;
        if (mailid != null ? !mailid.equals(rfError.mailid) : rfError.mailid != null) return false;
        if (orderid != null ? !orderid.equals(rfError.orderid) : rfError.orderid != null) return false;
        if (outpsn != null ? !outpsn.equals(rfError.outpsn) : rfError.outpsn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = logId != null ? logId.hashCode() : 0;
        result = 31 * result + (logRunid != null ? logRunid.hashCode() : 0);
        result = 31 * result + (logType != null ? logType.hashCode() : 0);
        result = 31 * result + (logAppend != null ? logAppend.hashCode() : 0);
        result = 31 * result + (logDate != null ? logDate.hashCode() : 0);
        result = 31 * result + (orderid != null ? orderid.hashCode() : 0);
        result = 31 * result + (mailid != null ? mailid.hashCode() : 0);
        result = 31 * result + (outpsn != null ? outpsn.hashCode() : 0);
        result = 31 * result + (errtype != null ? errtype.hashCode() : 0);
        return result;
    }
}
