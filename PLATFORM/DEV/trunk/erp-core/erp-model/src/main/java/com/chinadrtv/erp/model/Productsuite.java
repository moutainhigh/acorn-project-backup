package com.chinadrtv.erp.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-16
 */
@Entity
@Table(name="PRODUCTSUITE", schema = "IAGENT")
public class Productsuite implements java.io.Serializable{
    private String id;

    @javax.persistence.Column(name = "ID", length = 20)
    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String prodsuiteid;

    @javax.persistence.Column(name = "PRODSUITEID", length = 30)
    @Basic
    public String getProdsuiteid() {
        return prodsuiteid;
    }

    public void setProdsuiteid(String prodsuiteid) {
        this.prodsuiteid = prodsuiteid;
    }

    private String prodid;

    @javax.persistence.Column(name = "PRODID", length = 30)
    @Basic
    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    private BigDecimal prodnum;

    @javax.persistence.Column(name = "PRODNUM", precision = 20, scale = 8)
    @Basic
    public BigDecimal getProdnum() {
        return prodnum;
    }

    public void setProdnum(BigDecimal prodnum) {
        this.prodnum = prodnum;
    }

    private String priflag;

    @javax.persistence.Column(name = "PRIFLAG", length = 5)
    @Basic
    public String getPriflag() {
        return priflag;
    }

    public void setPriflag(String priflag) {
        this.priflag = priflag;
    }

    private BigDecimal prodpercent;

    @javax.persistence.Column(name = "PRODPERCENT", precision = 20, scale = 8)
    @Basic
    public BigDecimal getProdpercent() {
        return prodpercent;
    }

    public void setProdpercent(BigDecimal prodpercent) {
        this.prodpercent = prodpercent;
    }

    private String lastdate;

    @javax.persistence.Column(name = "LASTDATE", length = 20)
    @Basic
    public String getLastdate() {
        return lastdate;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Productsuite that = (Productsuite) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastdate != null ? !lastdate.equals(that.lastdate) : that.lastdate != null) return false;
        if (priflag != null ? !priflag.equals(that.priflag) : that.priflag != null) return false;
        if (prodid != null ? !prodid.equals(that.prodid) : that.prodid != null) return false;
        if (prodnum != null ? !prodnum.equals(that.prodnum) : that.prodnum != null) return false;
        if (prodpercent != null ? !prodpercent.equals(that.prodpercent) : that.prodpercent != null) return false;
        if (prodsuiteid != null ? !prodsuiteid.equals(that.prodsuiteid) : that.prodsuiteid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (prodsuiteid != null ? prodsuiteid.hashCode() : 0);
        result = 31 * result + (prodid != null ? prodid.hashCode() : 0);
        result = 31 * result + (prodnum != null ? prodnum.hashCode() : 0);
        result = 31 * result + (priflag != null ? priflag.hashCode() : 0);
        result = 31 * result + (prodpercent != null ? prodpercent.hashCode() : 0);
        result = 31 * result + (lastdate != null ? lastdate.hashCode() : 0);
        return result;
    }
}
