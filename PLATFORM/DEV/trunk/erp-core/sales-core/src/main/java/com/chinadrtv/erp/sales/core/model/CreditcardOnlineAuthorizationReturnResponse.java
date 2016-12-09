package com.chinadrtv.erp.sales.core.model;

import java.util.Date;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CreditcardOnlineAuthorizationReturnResponse extends CreditcardOnlineAuthorizationBase {
    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    /**
     * 交易时间
     */
    private Date transTime;
    /**
     * 流水号
     */
    private String refNum;
}
