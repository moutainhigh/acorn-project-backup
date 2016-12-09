package com.chinadrtv.erp.pay.core.icbc.model;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-7
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ICBCResPay extends ICBCResBaseInfo {
    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public BigDecimal getMerchantFree() {
        return merchantFree;
    }

    public void setMerchantFree(BigDecimal merchantFree) {
        this.merchantFree = merchantFree;
    }

    public BigDecimal getContactFree() {
        return contactFree;
    }

    public void setContactFree(BigDecimal contactFree) {
        this.contactFree = contactFree;
    }

    public String getContactFreeType() {
        return contactFreeType;
    }

    public void setContactFreeType(String contactFreeType) {
        this.contactFreeType = contactFreeType;
    }

    public BigDecimal getCurrentAmout() {
        return currentAmout;
    }

    public void setCurrentAmout(BigDecimal currentAmout) {
        this.currentAmout = currentAmout;
    }

    public BigDecimal getHpAmount() {
        return hpAmount;
    }

    public void setHpAmount(BigDecimal hpAmount) {
        this.hpAmount = hpAmount;
    }

    public BigDecimal getCurrentFree() {
        return currentFree;
    }

    public void setCurrentFree(BigDecimal currentFree) {
        this.currentFree = currentFree;
    }

    public BigDecimal getHpFree() {
        return hpFree;
    }

    public void setHpFree(BigDecimal hpFree) {
        this.hpFree = hpFree;
    }

    protected String refNum; //分期付款交易检索参考号
    protected String orderNum;//银行端交易序号
    protected Date transTime;//交易日期 年月日
    protected BigDecimal merchantFree;//分期付款商户方手续费
    protected BigDecimal contactFree;//分期付款持卡人手续费
    protected String contactFreeType;//0-首期收取 1-分期收取
    protected BigDecimal currentAmout;//当期金额
    protected BigDecimal hpAmount; //每期金额
    protected BigDecimal currentFree;//持卡人首期手续费
    protected BigDecimal hpFree;//持卡人每期手续费

    @Override
    public String unmarshal(String data) {
        data=super.unmarshal(data);
        String[] datas=data.split("\\"+delimiter,-1);
        if(datas.length>=10)
        {
            refNum=datas[0].trim();
            orderNum=datas[1].trim();
            try{
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
                transTime=simpleDateFormat.parse(datas[2].trim());
            }catch (Exception exp)
            {

            }
            BigDecimal yuanDecimal=new BigDecimal("100");
            if(StringUtils.isNotEmpty(datas[3].trim()))
                merchantFree=new BigDecimal(datas[3].trim()).divide(yuanDecimal, RoundingMode.CEILING);
            if(StringUtils.isNotEmpty(datas[4].trim()))
                contactFree=new BigDecimal(datas[4].trim()).divide(yuanDecimal,RoundingMode.CEILING);
            contactFreeType=datas[5].trim();
            if(StringUtils.isNotEmpty(datas[6].trim()))
                currentAmout=new BigDecimal(datas[6].trim()).divide(yuanDecimal,RoundingMode.CEILING);
            if(StringUtils.isNotEmpty(datas[7].trim()))
                hpAmount=new BigDecimal(datas[7].trim()).divide(yuanDecimal,RoundingMode.CEILING);
            if(StringUtils.isNotEmpty(datas[8].trim()))
                currentFree=new BigDecimal(datas[8].trim()).divide(yuanDecimal,RoundingMode.CEILING);
            if(StringUtils.isNotEmpty(datas[9].trim()))
                hpFree=new BigDecimal(datas[9].trim()).divide(yuanDecimal,RoundingMode.CEILING);
        }
        return "";
    }

}
