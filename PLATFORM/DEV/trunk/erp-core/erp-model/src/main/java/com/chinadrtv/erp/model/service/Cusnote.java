package com.chinadrtv.erp.model.service;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CUSNOTE", schema = "IAGENT")
@IdClass(CusnoteId.class)
public class Cusnote implements Serializable {
    private String notecate;
    private String isreplay;
    private Date gendate;
    private String genseat;
    private String noteremark;
    private Date redate;
    private String reseat;
    private String renote;
    private String noteclass;
    private String orderid;
    private Integer featstr;
    private String caseid;
    private Integer valid;

    @Column(name = "NOTECATE")
    public String getNotecate() {
        return notecate;
    }

    public void setNotecate(String notecate) {
        this.notecate = notecate;
    }

    @Column(name = "ISREPLAY")
    public String getIsreplay() {
        return isreplay;
    }

    public void setIsreplay(String isreplay) {
        this.isreplay = isreplay;
    }

    @Column(name = "GENDATE")
    public Date getGendate() {
        return gendate;
    }

    public void setGendate(Date gendate) {
        this.gendate = gendate;
    }

    @Column(name = "GENSEAT")
    public String getGenseat() {
        return genseat;
    }

    public void setGenseat(String genseat) {
        this.genseat = genseat;
    }

    @Column(name = "NOTEREMARK")
    public String getNoteremark() {
        return noteremark;
    }

    public void setNoteremark(String noteremark) {
        this.noteremark = noteremark;
    }

    @Column(name = "REDATE")
    public Date getRedate() {
        return redate;
    }

    public void setRedate(Date redate) {
        this.redate = redate;
    }

    @Column(name = "RESEAT")
    public String getReseat() {
        return reseat;
    }

    public void setReseat(String reseat) {
        this.reseat = reseat;
    }

    @Column(name = "RENOTE")
    public String getRenote() {
        return renote;
    }

    public void setRenote(String renote) {
        this.renote = renote;
    }

    @Column(name = "NOTECLASS")
    public String getNoteclass() {
        return noteclass;
    }

    public void setNoteclass(String noteclass) {
        this.noteclass = noteclass;
    }

    @Id
    @Column(name = "ORDERID")
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Id
    @Column(name = "FEATSTR")
    public Integer getFeatstr() {
        return featstr;
    }

    public void setFeatstr(Integer featstr) {
        this.featstr = featstr;
    }

    @Column(name = "CASEID")
    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    @Column(name = "VALID")
    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }
}
