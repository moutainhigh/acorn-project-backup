package com.chinadrtv.companymail.common.dal.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-22
 * Time: 下午12:38
 * To change this template use File | Settings | File Templates.
 */

public class ZMMRPTReceivableslist implements java.io.Serializable {
    private String proname; //省份
    private String toPerson;    //收件人
    private String address;     //地址
    private String orderNum;    //订单号
    private String orderDate;     //订购日期
    private String productName; //产品简称
    private String emialNum;    //邮件号
    private Double incomeMoney; //收入
    private Double weight;  //重量


    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getToPerson() {
        return toPerson;
    }

    public void setToPerson(String toPerson) {
        this.toPerson = toPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getEmialNum() {
        return emialNum;
    }

    public void setEmialNum(String emialNum) {
        this.emialNum = emialNum;
    }

    public Double getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(Double incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
