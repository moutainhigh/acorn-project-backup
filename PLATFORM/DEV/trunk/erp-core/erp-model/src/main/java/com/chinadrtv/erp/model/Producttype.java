package com.chinadrtv.erp.model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-16
 */
@Entity
@Table(name="PRODUCTTYPE", schema = "IAGENT")
public class Producttype implements java.io.Serializable{
    private String recid;

    @javax.persistence.Column(name = "RECID", length = 16)
    @Id
    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    private String prodrecid;

    @javax.persistence.Column(name = "PRODRECID", length = 30)
    @Basic
    public String getProdrecid() {
        return prodrecid;
    }

    public void setProdrecid(String prodrecid) {
        this.prodrecid = prodrecid;
    }

    private String typeid;

    @javax.persistence.Column(name = "TYPEID", length = 50)
    @Basic
    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    private String dsc;

    @javax.persistence.Column(name = "DSC", length = 100)
    @Basic
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    private String pstatus;

    @javax.persistence.Column(name = "PSTATUS", length = 10)
    @Basic
    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producttype that = (Producttype) o;

        if (dsc != null ? !dsc.equals(that.dsc) : that.dsc != null) return false;
        if (prodrecid != null ? !prodrecid.equals(that.prodrecid) : that.prodrecid != null) return false;
        if (pstatus != null ? !pstatus.equals(that.pstatus) : that.pstatus != null) return false;
        if (recid != null ? !recid.equals(that.recid) : that.recid != null) return false;
        if (typeid != null ? !typeid.equals(that.typeid) : that.typeid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recid != null ? recid.hashCode() : 0;
        result = 31 * result + (prodrecid != null ? prodrecid.hashCode() : 0);
        result = 31 * result + (typeid != null ? typeid.hashCode() : 0);
        result = 31 * result + (dsc != null ? dsc.hashCode() : 0);
        result = 31 * result + (pstatus != null ? pstatus.hashCode() : 0);
        return result;
    }
}
