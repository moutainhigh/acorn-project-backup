package com.chinadrtv.erp.oms.dto;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-19
 * Time: 上午10:47
 * To change this template use File | Settings | File Templates.
 * 商品折扣订单明细
 */
public class OrderDetailsDto implements java.io.Serializable  {
    private String orderId;      //订单号
    private String uPrice;       //产品原价
    private String upNum;       //原价订购数
    private String sPrice;     //产品优惠价
    private String spNum;      //优惠价订购数
    private String prodid;    //产品id
    private String prodName;    //产品名称
    private String lPrice;      //商品最低价

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getuPrice() {
        return uPrice;
    }

    public void setuPrice(String uPrice) {
        this.uPrice = uPrice;
    }

    public String getUpNum() {
        return upNum;
    }

    public void setUpNum(String upNum) {
        this.upNum = upNum;
    }

    public String getsPrice() {
        return sPrice;
    }

    public void setsPrice(String sPrice) {
        this.sPrice = sPrice;
    }

    public String getSpNum() {
        return spNum;
    }

    public void setSpNum(String spNum) {
        this.spNum = spNum;
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getlPrice() {
        return lPrice;
    }

    public void setlPrice(String lPrice) {
        this.lPrice = lPrice;
    }
}
