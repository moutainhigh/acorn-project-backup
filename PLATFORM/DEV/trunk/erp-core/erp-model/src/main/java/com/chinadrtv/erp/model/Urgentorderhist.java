package com.chinadrtv.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "URGENTORDERHIST", schema = "IAGENT")
public class Urgentorderhist {
    private String id;

    private String contactid;

    private String orderid;

    private String crusr;

    private Date crdt;

    private String mdusr;

    private Date mddt;

    private String dsc;

    private String status;

    private String note;

    private Date procdt;

    private String procusr;

    @Id
    @Column(name = "ID", length = 20)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CONTACTID", length = 20)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }


    @Column(name = "ORDERID", length = 20)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "CRUSR", length = 10)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }


    @Column(name = "CRDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }


    @Column(name = "MDUSR", length = 10)
    public String getMdusr() {
        return mdusr;
    }

    public void setMdusr(String mdusr) {
        this.mdusr = mdusr;
    }


    @Column(name = "MDDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }


    @Column(name = "DSC", length = 2000)
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }


    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Column(name = "NOTE", length = 64)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Column(name = "PROCDT", length = 7)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getProcdt() {
        return procdt;
    }

    public void setProcdt(Date procdt) {
        this.procdt = procdt;
    }

    @Column(name = "PROCUSR", length = 10)
    public String getProcusr() {
        return procusr;
    }

    public void setProcusr(String procusr) {
        this.procusr = procusr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Urgentorderhist that = (Urgentorderhist) o;

        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if (crdt != null ? !crdt.equals(that.crdt) : that.crdt != null) return false;
        if (crusr != null ? !crusr.equals(that.crusr) : that.crusr != null) return false;
        if (dsc != null ? !dsc.equals(that.dsc) : that.dsc != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (mddt != null ? !mddt.equals(that.mddt) : that.mddt != null) return false;
        if (mdusr != null ? !mdusr.equals(that.mdusr) : that.mdusr != null) return false;
        if (note != null ? !note.equals(that.note) : that.note != null) return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (procdt != null ? !procdt.equals(that.procdt) : that.procdt != null) return false;
        if (procusr != null ? !procusr.equals(that.procusr) : that.procusr != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (contactid != null ? contactid.hashCode() : 0);
        result = 31 * result + (orderid != null ? orderid.hashCode() : 0);
        result = 31 * result + (crusr != null ? crusr.hashCode() : 0);
        result = 31 * result + (crdt != null ? crdt.hashCode() : 0);
        result = 31 * result + (mdusr != null ? mdusr.hashCode() : 0);
        result = 31 * result + (mddt != null ? mddt.hashCode() : 0);
        result = 31 * result + (dsc != null ? dsc.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (procdt != null ? procdt.hashCode() : 0);
        result = 31 * result + (procusr != null ? procusr.hashCode() : 0);
        return result;
    }
}
