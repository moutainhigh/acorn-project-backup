package com.chinadrtv.acorn.bean;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CheckResult {
    public CheckResult(boolean succ,String msg)
    {
        this.isSucc=succ;
        this.errorMsg=msg;
    }

    public boolean isSucc() {
        return isSucc;
    }

    public void setSucc(boolean succ) {
        isSucc = succ;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private boolean isSucc;
    private String errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorCode;
}
