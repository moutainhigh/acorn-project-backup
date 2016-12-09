package com.chinadrtv.erp.pay.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 在线支付信息
 * User: 徐志凯
 * Date: 13-8-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class Payment implements Serializable {
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 有效日期
     */
    private Date expiryDate;
    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 分期数
     */
    private Integer stageNum;

    /**
     * 证件类型
     */
    private CredentialsType credentialsType;

    /**
     * 证件号码
     */
    private String credentialsNo;

    /**
     * 索权码（工行存放cvv2）
     */
    private String extraCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String mobile;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStageNum() {
        return stageNum;
    }

    public void setStageNum(Integer stageNum) {
        this.stageNum = stageNum;
    }

    public CredentialsType getCredentialsType() {
        return credentialsType;
    }

    public void setCredentialsType(CredentialsType credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public String getExtraCode() {
        return extraCode;
    }

    public void setExtraCode(String extraCode) {
        this.extraCode = extraCode;
    }


}
