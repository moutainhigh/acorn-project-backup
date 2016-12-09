package com.chinadrtv.erp.tc.core.model;

import com.chinadrtv.erp.model.PreTrade;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PreTradeRest extends PreTrade implements java.io.Serializable {

    private String contactsource;//B2C客户来源，目前没处理
    private String crusr;
    private String userid;
    private String userPhone;
    private String userMobile;
    private String name;

    /**
     * 银行编号
     */
    //private String bankCode;
    /**
     * 授权码
     */
    //private String authCode;
    /**
     * 身份证编号
     */
    //private String idCardNumber;
    /**
     * 信用卡编号
     */
    //private String creditCardNumber;
    /**
     * 信用卡有效期
     */
    //private String creditCardExpire;
    /**
     * 信用卡期数
     */
    //private Integer creditCardCycles;

    //private String mailType;*/

    public String getContactsource() {
        return contactsource;
    }

    public void setContactsource(String contactsource) {
        this.contactsource = contactsource;
    }

    public String getCrusr() {
        return crusr;
    }

    public void setCrusr(String crusr) {
        this.crusr = crusr;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardExpire() {
        return creditCardExpire;
    }

    public void setCreditCardExpire(String creditCardExpire) {
        this.creditCardExpire = creditCardExpire;
    }

    public Integer getCreditCardCycles() {
        return creditCardCycles;
    }

    public void setCreditCardCycles(Integer creditCardCycles) {
        this.creditCardCycles = creditCardCycles;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    } */
}
