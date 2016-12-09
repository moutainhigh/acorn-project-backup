package com.chinadrtv.erp.tc.core.model;

/**
 * 前置订单返回结果信息 (TC).
 * User: 徐志凯
 * Date: 13-3-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PretradeReturnCode extends OrderReturnCode {
    public PretradeReturnCode()
    {
        success=false;
        impState="013";
    }

    private boolean success;
    private String orderId;
    private String impState;

    public String getImpState() {
        return impState;
    }

    public void setImpState(String impState) {
        this.impState = impState;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
