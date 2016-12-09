package com.chinadrtv.erp.sales.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-7-25
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CreditReturnTestDto extends CreditTestDto implements Serializable {
    public String getPreBatchNum() {
        return preBatchNum;
    }

    public void setPreBatchNum(String preBatchNum) {
        this.preBatchNum = preBatchNum;
    }

    public String getPreTransTime() {
        return preTransTime;
    }

    public void setPreTransTime(String preTransTime) {
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

    private String preBatchNum;
    private String preTransTime;
    private Integer preTraceNum;
    private String preRefNum;
    private String preAuthID;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    private String orderNum;
}
