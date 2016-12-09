package com.chinadrtv.service.oms.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PretradeDto implements java.io.Serializable{
    public PretradeDto()
    {
        details=new ArrayList<PretradeDetailDto>();
    }
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Date getOutCrdt() {
        return outCrdt;
    }

    public void setOutCrdt(Date outCrdt) {
        this.outCrdt = outCrdt;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    private String tradeId;
    private String tradeType;
    private Date outCrdt;
    private BigDecimal payment;

    public List<PretradeDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<PretradeDetailDto> details) {
        this.details = details;
    }

    private List<PretradeDetailDto> details;
}
