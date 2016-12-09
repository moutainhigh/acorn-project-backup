package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CreditTestDto implements Serializable {
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    private BigDecimal amount;
    private Integer stage;
    private String cardNo;

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }

    private String validateDate;
}
