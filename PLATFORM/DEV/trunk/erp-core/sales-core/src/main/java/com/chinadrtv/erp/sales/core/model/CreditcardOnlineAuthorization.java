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
public class CreditcardOnlineAuthorization extends CreditcardOnlineAuthorizationBase {
    private String orderId;
    private String cardNo;
    private Date expiryDate;
    private BigDecimal amount;
    private Integer stageNum;

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
}
