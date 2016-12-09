package com.chinadrtv.erp.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-16
 */
@Table(name = "STOCK_WMS", schema = "IAGENT")
@Entity
public class StockWms implements Serializable {
    private String prodid;

    @javax.persistence.Column(name = "PRODID", length = 30)
    @Basic
    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    private String warehouseid;

    @Column(name = "WAREHOUSEID", length = 10)
    @Basic
    public String getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(String warehouseid) {
        this.warehouseid = warehouseid;
    }

    private String warehousename;

    @Column(name = "WAREHOUSENAME", length = 40)
    @Basic
    public String getWarehousename() {
        return warehousename;
    }

    public void setWarehousename(String warehousename) {
        this.warehousename = warehousename;
    }

    private Long quantity;

    @javax.persistence.Column(name = "QUANTITY")
    @Basic
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    private String producttypedsc;

    @javax.persistence.Column(name = "PRODUCTTYPEDSC", length = 200)
    @Basic
    public String getProducttypedsc() {
        return producttypedsc;
    }

    public void setProducttypedsc(String producttypedsc) {
        this.producttypedsc = producttypedsc;
    }

    private String stockid;

    @javax.persistence.Column(name = "STOCKID", length = 20)
    @Id
    public String getStockid() {
        return stockid;
    }

    public void setStockid(String stockid) {
        this.stockid = stockid;
    }

    private String companyid;

    @javax.persistence.Column(name = "COMPANYID", length = 12)
    @Basic
    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    private String companyname;

    @javax.persistence.Column(name = "COMPANYNAME", length = 100)
    @Basic
    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    private Long lockqty;

    @javax.persistence.Column(name = "LOCKQTY")
    @Basic
    public Long getLockqty() {
        return lockqty;
    }

    public void setLockqty(Long lockqty) {
        this.lockqty = lockqty;
    }

    private BigInteger typ;

    @javax.persistence.Column(name = "TYP")
    @Basic
    public BigInteger getTyp() {
        return typ;
    }

    public void setTyp(BigInteger typ) {
        this.typ = typ;
    }

    private Date updat;

    @javax.persistence.Column(name = "UPDAT")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    public Date getUpdat() {
        return updat;
    }

    public void setUpdat(Date updat) {
        this.updat = updat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockWms stockWms = (StockWms) o;

        if (lockqty != stockWms.lockqty) return false;
        if (quantity != stockWms.quantity) return false;
        if (companyid != null ? !companyid.equals(stockWms.companyid) : stockWms.companyid != null) return false;
        if (companyname != null ? !companyname.equals(stockWms.companyname) : stockWms.companyname != null)
            return false;
        if (prodid != null ? !prodid.equals(stockWms.prodid) : stockWms.prodid != null) return false;
        if (producttypedsc != null ? !producttypedsc.equals(stockWms.producttypedsc) : stockWms.producttypedsc != null)
            return false;
        if (stockid != null ? !stockid.equals(stockWms.stockid) : stockWms.stockid != null) return false;
        if (typ != null ? !typ.equals(stockWms.typ) : stockWms.typ != null) return false;
        if (updat != null ? !updat.equals(stockWms.updat) : stockWms.updat != null) return false;
        if (warehouseid != null ? !warehouseid.equals(stockWms.warehouseid) : stockWms.warehouseid != null)
            return false;
        if (warehousename != null ? !warehousename.equals(stockWms.warehousename) : stockWms.warehousename != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = prodid != null ? prodid.hashCode() : 0;
        result = 31 * result + (warehouseid != null ? warehouseid.hashCode() : 0);
        result = 31 * result + (warehousename != null ? warehousename.hashCode() : 0);
        result = 31 * result + (int) (quantity ^ (quantity >>> 32));
        result = 31 * result + (producttypedsc != null ? producttypedsc.hashCode() : 0);
        result = 31 * result + (stockid != null ? stockid.hashCode() : 0);
        result = 31 * result + (companyid != null ? companyid.hashCode() : 0);
        result = 31 * result + (companyname != null ? companyname.hashCode() : 0);
        result = 31 * result + (int) (lockqty ^ (lockqty >>> 32));
        result = 31 * result + (typ != null ? typ.hashCode() : 0);
        result = 31 * result + (updat != null ? updat.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.prodid+"-"+this.warehouseid+"-"+this.producttypedsc;
    }
}
