package com.chinadrtv.erp.sales.core.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CreditcardOnlineAuthorizationResponse extends CreditcardOnlineAuthorizationBase {
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

    public Integer getTraceNum() {
        return traceNum;
    }

    public void setTraceNum(Integer traceNum) {
        this.traceNum = traceNum;
    }

    private Integer traceNum;


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
}
