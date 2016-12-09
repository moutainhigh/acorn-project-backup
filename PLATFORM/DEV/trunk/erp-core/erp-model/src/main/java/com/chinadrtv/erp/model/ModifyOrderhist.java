package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单修改请求 (TC).
 * User: 徐志凯
 * Date: 13-2-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Table(name = "MODIFY_ORDERHIST", schema = "IAGENT")
@Entity
public class ModifyOrderhist implements Serializable {
    private String modorderid;

    private String contactid;

    private String name;

    private String orderid;

    private String status;

    private String crusr;

    private String crname;

    private String crgrpid;

    private Date crdt;

    private String crreason;

    private String crnote;

    private String causr;

    private String caname;

    private String cagrpid;

    private Date cadt;

    private String finusr;

    private String finname;

    private String fingrpid;

    private Date findt;

    private String type;

    private String ordercrusr;

    private Integer flagcontact;

    private Integer flagorder;

    private String opusr;


    @Id
    @Column(name = "MODORDERID", length = 20)
    public String getModorderid() {
        return modorderid;
    }

    public void setModorderid(String modorderid) {
        this.modorderid = modorderid;
    }

    @Column(name = "CONTACTID", length = 20)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "NAME", length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ORDERID", length = 20)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "CRUSR", length = 10)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    @Column(name = "CRNAME", length = 20) 
    public String getCrname() {
        return crname;
    }

    public void setCrname(String crname) {
        this.crname = crname;
    }

    @Column(name = "CRGRPID", length = 20)
    public String getCrgrpid() {
        return crgrpid;
    }

    public void setCrgrpid(String crgrpid) {
        this.crgrpid = crgrpid;
    }

    @Column(name = "CRDT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    @Column(name = "CRREASON", length = 2000)
    public String getCrreason() {
        return crreason;
    }

    public void setCrreason(String crreason) {
        this.crreason = crreason;
    }

    @Column(name = "CRNOTE", length = 2000)
    public String getCrnote() {
        return crnote;
    }

    public void setCrnote(String crnote) {
        this.crnote = crnote;
    }

    @Column(name = "CAUSR", length = 10)
    public String getCausr() {
        return causr;
    }

    public void setCausr(String causr) {
        this.causr = causr;
    }

    @Column(name = "CANAME", length = 20)
    public String getCaname() {
        return caname;
    }

    public void setCaname(String caname) {
        this.caname = caname;
    }

    @Column(name = "CAGRPID", length = 20)
    public String getCagrpid() {
        return cagrpid;
    }

    public void setCagrpid(String cagrpid) {
        this.cagrpid = cagrpid;
    }

    @Column(name = "CADT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCadt() {
        return cadt;
    }

    public void setCadt(Date cadt) {
        this.cadt = cadt;
    }

    @Column(name = "FINUSR", length = 10)
    public String getFinusr() {
        return finusr;
    }

    public void setFinusr(String finusr) {
        this.finusr = finusr;
    }

    @Column(name = "FINNAME", length = 20)
    public String getFinname() {
        return finname;
    }

    public void setFinname(String finname) {
        this.finname = finname;
    }

    @Column(name = "FINGRPID", length = 20)
    public String getFingrpid() {
        return fingrpid;
    }

    public void setFingrpid(String fingrpid) {
        this.fingrpid = fingrpid;
    }

    @Column(name = "FINDT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getFindt() {
        return findt;
    }

    public void setFindt(Date findt) {
        this.findt = findt;
    }

    @Column(name = "TYPE", length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "ORDERCRUSR", length = 10)
    public String getOrdercrusr() {
        return ordercrusr;
    }

    public void setOrdercrusr(String ordercrusr) {
        this.ordercrusr = ordercrusr;
    }

    @Column(name = "FLAGCONTACT")
    public Integer getFlagcontact() {
        return flagcontact;
    }

    public void setFlagcontact(Integer flagcontact) {
        this.flagcontact = flagcontact;
    }

    @Column(name = "FLAGORDER")
    public Integer getFlagorder() {
        return flagorder;
    }

    public void setFlagorder(Integer flagorder) {
        this.flagorder = flagorder;
    }

    @Column(name = "OPUSR", length = 10)
    public String getOpusr() {
        return opusr;
    }

    public void setOpusr(String opusr) {
        this.opusr = opusr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModifyOrderhist that = (ModifyOrderhist) o;

        if (cadt != null ? !cadt.equals(that.cadt) : that.cadt != null) return false;
        if (cagrpid != null ? !cagrpid.equals(that.cagrpid) : that.cagrpid != null) return false;
        if (caname != null ? !caname.equals(that.caname) : that.caname != null) return false;
        if (causr != null ? !causr.equals(that.causr) : that.causr != null) return false;
        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if (crdt != null ? !crdt.equals(that.crdt) : that.crdt != null) return false;
        if (crgrpid != null ? !crgrpid.equals(that.crgrpid) : that.crgrpid != null) return false;
        if (crname != null ? !crname.equals(that.crname) : that.crname != null) return false;
        if (crnote != null ? !crnote.equals(that.crnote) : that.crnote != null) return false;
        if (crreason != null ? !crreason.equals(that.crreason) : that.crreason != null) return false;
        if (crusr != null ? !crusr.equals(that.crusr) : that.crusr != null) return false;
        if (findt != null ? !findt.equals(that.findt) : that.findt != null) return false;
        if (fingrpid != null ? !fingrpid.equals(that.fingrpid) : that.fingrpid != null) return false;
        if (finname != null ? !finname.equals(that.finname) : that.finname != null) return false;
        if (finusr != null ? !finusr.equals(that.finusr) : that.finusr != null) return false;
        if (flagcontact != null ? !flagcontact.equals(that.flagcontact) : that.flagcontact != null) return false;
        if (flagorder != null ? !flagorder.equals(that.flagorder) : that.flagorder != null) return false;
        if (modorderid != null ? !modorderid.equals(that.modorderid) : that.modorderid != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (opusr != null ? !opusr.equals(that.opusr) : that.opusr != null) return false;
        if (ordercrusr != null ? !ordercrusr.equals(that.ordercrusr) : that.ordercrusr != null) return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = modorderid != null ? modorderid.hashCode() : 0;
        result = 31 * result + (contactid != null ? contactid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (orderid != null ? orderid.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (crusr != null ? crusr.hashCode() : 0);
        result = 31 * result + (crname != null ? crname.hashCode() : 0);
        result = 31 * result + (crgrpid != null ? crgrpid.hashCode() : 0);
        result = 31 * result + (crdt != null ? crdt.hashCode() : 0);
        result = 31 * result + (crreason != null ? crreason.hashCode() : 0);
        result = 31 * result + (crnote != null ? crnote.hashCode() : 0);
        result = 31 * result + (causr != null ? causr.hashCode() : 0);
        result = 31 * result + (caname != null ? caname.hashCode() : 0);
        result = 31 * result + (cagrpid != null ? cagrpid.hashCode() : 0);
        result = 31 * result + (cadt != null ? cadt.hashCode() : 0);
        result = 31 * result + (finusr != null ? finusr.hashCode() : 0);
        result = 31 * result + (finname != null ? finname.hashCode() : 0);
        result = 31 * result + (fingrpid != null ? fingrpid.hashCode() : 0);
        result = 31 * result + (findt != null ? findt.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (ordercrusr != null ? ordercrusr.hashCode() : 0);
        result = 31 * result + (flagcontact != null ? flagcontact.hashCode() : 0);
        result = 31 * result + (flagorder != null ? flagorder.hashCode() : 0);
        result = 31 * result + (opusr != null ? opusr.hashCode() : 0);
        return result;
    }
}
