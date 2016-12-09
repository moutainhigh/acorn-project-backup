package com.chinadrtv.erp.pay.core.icbc.model;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ICBCReqPay extends ICBCReqBaseInfo {
    protected String currency;
    protected String cardNo;
    protected BigDecimal amount;
    protected String credentialsTypeNum;
    protected String credentialsNo;
    protected Date expiryDate;
    protected String extraCode;
    protected Integer stageNum;


    public ICBCReqPay()
    {
        currency="001";
        this.setReqType(ICBCReqType.PAY);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCredentialsNo() {
        return credentialsNo;
    }

    public void setCredentialsNo(String credentialsNo) {
        this.credentialsNo = credentialsNo;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getExtraCode() {
        return extraCode;
    }

    public void setExtraCode(String extraCode) {
        this.extraCode = extraCode;
    }

    public int getStageNum() {
        return stageNum;
    }

    public void setStageNum(int stageNum) {
        this.stageNum = stageNum;
    }

    public String getCredentialsTypeNum() {
        return credentialsTypeNum;
    }

    public void setCredentialsTypeNum(String credentialsTypeNum) {
        this.credentialsTypeNum = credentialsTypeNum;
    }



    @Override
    public String marshal() {
        StringBuilder stringBuilder=new StringBuilder(super.marshal());
        stringBuilder.append(currency);
        stringBuilder.append(delimiter);
        stringBuilder.append(cardNo);
        stringBuilder.append(delimiter);
        stringBuilder.append(String.valueOf(amount.multiply(new BigDecimal("100")).intValue()));//金额，以分为单位
        stringBuilder.append(delimiter);
        if(StringUtils.isNotEmpty(credentialsTypeNum))
        {
            stringBuilder.append(credentialsTypeNum);
        }
        stringBuilder.append(delimiter);
        if(StringUtils.isNotEmpty(credentialsNo))
        {
            stringBuilder.append(credentialsNo);
        }
        stringBuilder.append(delimiter);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMM");
        stringBuilder.append(simpleDateFormat.format(expiryDate));
        stringBuilder.append(delimiter);
        stringBuilder.append(extraCode);
        stringBuilder.append(delimiter);
        stringBuilder.append(stageNum);
        stringBuilder.append(delimiter);
        return stringBuilder.toString();
    }
}
