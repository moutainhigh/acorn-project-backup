package com.chinadrtv.erp.pay.core.exception;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OnlineException extends RuntimeException {
    public OnlineException(String errorCode,String errorMessage)
    {
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private String errorCode;
    private String errorMessage;
}
