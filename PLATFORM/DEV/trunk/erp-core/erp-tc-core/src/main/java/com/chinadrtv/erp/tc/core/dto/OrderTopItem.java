package com.chinadrtv.erp.tc.core.dto;

import java.io.Serializable;

/**
 * 销售排名前20个商品
 * User: gdj
 * Date: 13-7-15
 * Time: 下午1:27
 * To change this template use File | Settings | File Templates.
 */
public class OrderTopItem implements Serializable {

    private String prodId;
    private String prodName;
    private String ncfree;
    private String ncfreeName;
    private Double soldQty;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getNcfree() {
        return ncfree;
    }

    public void setNcfree(String ncfree) {
        this.ncfree = ncfree;
    }

    public String getNcfreeName() {
        return ncfreeName;
    }

    public void setNcfreeName(String ncfreeName) {
        this.ncfreeName = ncfreeName;
    }

    public Double getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(Double soldQty) {
        this.soldQty = soldQty;
    }

}
