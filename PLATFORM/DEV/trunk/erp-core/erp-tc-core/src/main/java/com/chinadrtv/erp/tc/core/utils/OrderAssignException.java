package com.chinadrtv.erp.tc.core.utils;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderAssignException extends RuntimeException {
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    private String errorCode;
    private String errorDesc;

    public OrderAssignException(String errorCode, String errorDesc)
    {
        this.errorCode=errorCode;
        this.errorDesc=errorDesc;
    }
}

