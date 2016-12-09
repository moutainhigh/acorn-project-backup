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
public class ICBCReqAuthentication extends ICBCReqBaseInfo {
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    protected String cardNo;
    protected Date expiryDate;
    protected String credentialsTypeNum;
    protected String credentialsNo;
    protected String mobile;

    public ICBCReqAuthentication()
    {
        this.setReqType(ICBCReqType.AUTHENTICATION);
    }

    @Override
    public String marshal() {
        StringBuilder stringBuilder=new StringBuilder(super.marshal());
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
        if(StringUtils.isNotEmpty(mobile))
            stringBuilder.append(mobile);
        stringBuilder.append(delimiter);
       /* if(StringUtils.isNotEmpty(credentialsNo))
            stringBuilder.append(credentialsTranNo);
        stringBuilder.append(delimiter);
        stringBuilder.append(String.valueOf(amount.multiply(new BigDecimal("100")).intValue()));//金额，以分为单位
        stringBuilder.append(delimiter);*/
        return stringBuilder.toString();
    }
}
