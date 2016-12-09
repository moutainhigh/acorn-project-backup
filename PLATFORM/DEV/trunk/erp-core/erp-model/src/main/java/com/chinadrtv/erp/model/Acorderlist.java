package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "ACORDERLIST", schema = "IAGENT")
public class Acorderlist implements Serializable {

    private String orderid;

    private String contactid;

    private String crusr;

    private Date crdt;

    private String mdusr;

    private Date mddt;

    private String content;

    private String dsc;

    private String type;

    private String status;

    private String id;

    private String store;

    private String deliver;

    private String nomdreason;

    @Column(name = "ORDERID", length = 20)
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    @Column(name = "CONTACTID", length = 20)
    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    @Column(name = "CRUSR", length = 10)
    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    @Column(name = "CRDT")
    @Temporal(TemporalType.TIMESTAMP)
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

    @Column(name = "MDDT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getMddt() {
        return mddt;
    }

    public void setMddt(Date mddt) {
        this.mddt = mddt;
    }

    @Column(name = "CONTENT", length = 2000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "DSC", length = 2000)
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    @Column(name = "TYPE", length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "STATUS", length = 2)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "STORE", length = 2000)
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Column(name = "DELIVER", length = 2000)
    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    @Column(name = "NOMDREASON", length = 10)
    public String getNomdreason() {
        return nomdreason;
    }

    public void setNomdreason(String nomdreason) {
        this.nomdreason = nomdreason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Acorderlist that = (Acorderlist) o;

        if (contactid != null ? !contactid.equals(that.contactid) : that.contactid != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (crdt != null ? !crdt.equals(that.crdt) : that.crdt != null) return false;
        if (crusr != null ? !crusr.equals(that.crusr) : that.crusr != null) return false;
        if (deliver != null ? !deliver.equals(that.deliver) : that.deliver != null) return false;
        if (dsc != null ? !dsc.equals(that.dsc) : that.dsc != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (mddt != null ? !mddt.equals(that.mddt) : that.mddt != null) return false;
        if (mdusr != null ? !mdusr.equals(that.mdusr) : that.mdusr != null) return false;
        if (nomdreason != null ? !nomdreason.equals(that.nomdreason) : that.nomdreason != null) return false;
        if (orderid != null ? !orderid.equals(that.orderid) : that.orderid != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (store != null ? !store.equals(that.store) : that.store != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderid != null ? orderid.hashCode() : 0;
        result = 31 * result + (contactid != null ? contactid.hashCode() : 0);
        result = 31 * result + (crusr != null ? crusr.hashCode() : 0);
        result = 31 * result + (crdt != null ? crdt.hashCode() : 0);
        result = 31 * result + (mdusr != null ? mdusr.hashCode() : 0);
        result = 31 * result + (mddt != null ? mddt.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (dsc != null ? dsc.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (store != null ? store.hashCode() : 0);
        result = 31 * result + (deliver != null ? deliver.hashCode() : 0);
        result = 31 * result + (nomdreason != null ? nomdreason.hashCode() : 0);
        return result;
    }
}
