package com.chinadrtv.erp.shipment.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-1
 * Time: 下午12:18
 * To change this template use File | Settings | File Templates.
 * 拆分商品信息
 */
public class Products {
    private String orderid;
    private String orderdetid;
    private String plucode;
    private String pluname;
    private String totalqty;
    private String slprc;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderdetid() {
        return orderdetid;
    }

    public void setOrderdetid(String orderdetid) {
        this.orderdetid = orderdetid;
    }

    public String getPlucode() {
        return plucode;
    }

    public void setPlucode(String plucode) {
        this.plucode = plucode;
    }

    public String getPluname() {
        return pluname;
    }

    public void setPluname(String pluname) {
        this.pluname = pluname;
    }

    public String getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(String totalqty) {
        this.totalqty = totalqty;
    }

    public String getSlprc() {
        return slprc;
    }

    public void setSlprc(String slprc) {
        this.slprc = slprc;
    }
}
