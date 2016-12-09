package com.chinadrtv.companymail.common.dal.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-1-22
 * Time: 上午11:16
 * To change this template use File | Settings | File Templates.
 * R56数据模板
 */

public class ZMMRPTEMSMailList implements java.io.Serializable {
    private String companyId;   //公司代码
    private String orderDate;   //交寄日期
    private String email;   //邮件号
    private Double orderMoney;  //  订单金额
    private String baoliu;  //     保留
    private String baoliu1; //            保留1
    private String cityId;  //     城市简码
    private String name;    //             姓名
    private String address;     //             地址
    private String orderId; //                     订单号
    private String proName; //        产品简称
    private String telephone;   //            电话号码
    private String postcode;    //                    邮编

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    private Double weight;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getBaoliu() {
        return baoliu;
    }

    public void setBaoliu(String baoliu) {
        this.baoliu = baoliu;
    }

    public String getBaoliu1() {
        return baoliu1;
    }

    public void setBaoliu1(String baoliu1) {
        this.baoliu1 = baoliu1;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
