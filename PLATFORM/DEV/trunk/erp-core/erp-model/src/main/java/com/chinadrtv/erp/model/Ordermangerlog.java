package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "ORDERMANGERLOG", schema = "ACOAPP_MONIAGT")
@Entity
public class Ordermangerlog implements Serializable {
    private Long ruid;
    private Integer opttyp;
    private Date optdat;
    private String optpsn;
    private String orderid;
    private String mail;
    private String notes;
    private String ordermsg;
    private String remarks;
    private String optcom;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERMANGERLOG_SEQ")
    @SequenceGenerator(name = "ORDERMANGERLOG_SEQ", sequenceName = "ACOAPP_MONIAGT.SQ_ORDERMANGERLOG")
    @Column(name = "RUID")
    public Long getRuid() {
        return ruid;
    }

    public void setRuid(Long ruid) {
        this.ruid = ruid;
    }

    @Column(name = "OPTTYP")
    public Integer getOpttyp() {
        return opttyp;
    }

    public void setOpttyp(Integer opttyp) {
        this.opttyp = opttyp;
    }

    @Column(name = "OPTDAT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getOptdat() {
        return optdat;
    }

    public void setOptdat(Date optdat) {
        this.optdat = optdat;
    }

    @Column(name = "OPTPSN", length = 20)
    public String getOptpsn() {
        return optpsn;
    }

    public void setOptpsn(String optpsn) {
        this.optpsn = optpsn;
    }

    @Column(name = "ORDERID", length = 40)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "MAIL", length = 100)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column(name = "NOTES", length = 500)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "ORDERMSG", length = 200)
    public String getOrdermsg() {
        return ordermsg;
    }

    public void setOrdermsg(String ordermsg) {
        this.ordermsg = ordermsg;
    }

    @Column(name = "REMARKS", length = 200)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "OPTCOM",  length = 20)
    public String getOptcom() {
        return optcom;
    }

    public void setOptcom(String optcom) {
        this.optcom = optcom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ordermangerlog that = (Ordermangerlog) o;

        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (optcom != null ? !optcom.equals(that.optcom) : that.optcom != null) return false;
        if (optdat != null ? !optdat.equals(that.optdat) : that.optdat != null) return false;
        if (optpsn != null ? !optpsn.equals(that.optpsn) : that.optpsn != null) return false;
        if (opttyp != null ? !opttyp.equals(that.opttyp) : that.opttyp != null) return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (ordermsg != null ? !ordermsg.equals(that.ordermsg) : that.ordermsg != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (ruid != null ? !ruid.equals(that.ruid) : that.ruid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ruid != null ? ruid.hashCode() : 0;
        result = 31 * result + (opttyp != null ? opttyp.hashCode() : 0);
        result = 31 * result + (optdat != null ? optdat.hashCode() : 0);
        result = 31 * result + (optpsn != null ? optpsn.hashCode() : 0);
        result = 31 * result + (orderid != null ? orderid.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (ordermsg != null ? ordermsg.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (optcom != null ? optcom.hashCode() : 0);
        return result;
    }
}
