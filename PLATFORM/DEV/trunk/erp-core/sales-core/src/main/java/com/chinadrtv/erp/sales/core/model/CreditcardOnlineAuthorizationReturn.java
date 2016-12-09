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
public class CreditcardOnlineAuthorizationReturn extends CreditcardOnlineAuthorizationBase {
   private String batchNum;
   private String cardNo;
   private Date expiryDate;
   private Integer traceNum;
   private BigDecimal amount;
   private String preBatchNum;
   private Date preTransTime;
   private Integer preTraceNum;
   private String preRefNum;
   private String preAuthID;
   private Integer stageNum;
   private String orderNum;

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
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

    public Integer getTraceNum() {
        return traceNum;
    }

    public void setTraceNum(Integer traceNum) {
        this.traceNum = traceNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPreBatchNum() {
        return preBatchNum;
    }

    public void setPreBatchNum(String preBatchNum) {
        this.preBatchNum = preBatchNum;
    }

    public Date getPreTransTime() {
        return preTransTime;
    }

    public void setPreTransTime(Date preTransTime) {
        this.preTransTime = preTransTime;
    }

    public Integer getPreTraceNum() {
        return preTraceNum;
    }

    public void setPreTraceNum(Integer preTraceNum) {
        this.preTraceNum = preTraceNum;
    }

    public String getPreRefNum() {
        return preRefNum;
    }

    public void setPreRefNum(String preRefNum) {
        this.preRefNum = preRefNum;
    }

    public String getPreAuthID() {
        return preAuthID;
    }

    public void setPreAuthID(String preAuthID) {
        this.preAuthID = preAuthID;
    }

    public Integer getStageNum() {
        return stageNum;
    }

    public void setStageNum(Integer stageNum) {
        this.stageNum = stageNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

}
