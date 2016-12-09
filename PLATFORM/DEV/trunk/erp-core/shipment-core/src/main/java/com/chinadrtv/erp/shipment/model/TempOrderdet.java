package com.chinadrtv.erp.shipment.model;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-31
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
public class TempOrderdet implements java.io.Serializable {
    private String orderid;
    private String prodid;
    private String orderdetid;
    private String num;
    private String amt;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getOrderdetid() {
        return orderdetid;
    }

    public void setOrderdetid(String orderdetid) {
        this.orderdetid = orderdetid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
