package com.chinadrtv.erp.pay.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 在线支付返回结果
 * User: 徐志凯
 * Date: 13-8-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PayResult implements Serializable {
    /**
     * 交易时间
     */
    private Date transTime;
    /**
     * 授权码
     */
    private String authID;
    /**
     * 订单号
     */
    private String orderNum;
    /**
     * 分期手续费
     */
    private BigDecimal hpFee;
    /**
     * 分期金额
     */
    private BigDecimal hpAmount;
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    /**
     * 流水号
     */
    private String refNum;
    /**
     * trace号
     */
    private Integer traceNum;

    /**
     * 错误代码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 批次号
     */
    private String batchNum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String authID) {
        this.authID = authID;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getHpFee() {
        return hpFee;
    }

    public void setHpFee(BigDecimal hpFee) {
        this.hpFee = hpFee;
    }

    public BigDecimal getHpAmount() {
        return hpAmount;
    }

    public void setHpAmount(BigDecimal hpAmount) {
        this.hpAmount = hpAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public Integer getTraceNum() {
        return traceNum;
    }

    public void setTraceNum(Integer traceNum) {
        this.traceNum = traceNum;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @JsonIgnore
    public boolean isSucc()
    {
        if(StringUtils.isEmpty(this.errorCode))
            return true;
        return false;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

}
