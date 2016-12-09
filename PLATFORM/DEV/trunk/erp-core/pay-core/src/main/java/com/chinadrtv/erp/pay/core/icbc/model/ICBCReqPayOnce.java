package com.chinadrtv.erp.pay.core.icbc.model;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ICBCReqPayOnce extends ICBCReqBaseInfo {
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCredentialsTypeNum() {
        return credentialsTypeNum;
    }

    public void setCredentialsTypeNum(String credentialsTypeNum) {
        this.credentialsTypeNum = credentialsTypeNum;
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

    public String getCredentialsTranNo() {
        return credentialsTranNo;
    }

    public void setCredentialsTranNo(String credentialsTranNo) {
        this.credentialsTranNo = credentialsTranNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    protected String currency;
    protected String cardNo;
    protected Date expiryDate;
    protected String credentialsTypeNum;
    protected String credentialsNo;
    protected String extraCode;
    protected String credentialsTranNo;//身份校验交易序号
    protected BigDecimal amount;

    public ICBCReqPayOnce()
    {
        currency="001";
        this.setReqType(ICBCReqType.PAYONCE);
    }


    @Override
    public String marshal() {
        StringBuilder stringBuilder=new StringBuilder(super.marshal());
        stringBuilder.append(currency);
        stringBuilder.append(delimiter);
        stringBuilder.append(cardNo);
        stringBuilder.append(delimiter);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMM");
        stringBuilder.append(simpleDateFormat.format(expiryDate));
        //stringBuilder.append(String.valueOf(amount.multiply(new BigDecimal("100")).intValue()));//金额，以分为单位
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
        stringBuilder.append(extraCode);
        stringBuilder.append(delimiter);
        if(StringUtils.isNotEmpty(credentialsNo))
            stringBuilder.append(credentialsTranNo);
        stringBuilder.append(delimiter);
        stringBuilder.append(String.valueOf(amount.multiply(new BigDecimal("100")).intValue()));//金额，以分为单位
        stringBuilder.append(delimiter);
        return stringBuilder.toString();
    }
}
