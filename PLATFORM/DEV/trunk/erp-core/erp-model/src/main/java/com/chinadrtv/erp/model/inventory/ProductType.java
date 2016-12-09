package com.chinadrtv.erp.model.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-3-6
 * Time: 下午6:44
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "PRODUCTTYPE", schema = "IAGENT")
@Entity
public class ProductType implements Serializable {
    private String recid;
    private String dsc;
    private String prodrecid;

    @Id
    @Column(name = "RECID")
    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }


    @Column(name = "DSC")
    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    @Column(name = "PRODRECID")
    public String getProdrecid() {
        return prodrecid;
    }

    public void setProdrecid(String prodrecid) {
        this.prodrecid = prodrecid;
    }

}
