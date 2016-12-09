package com.chinadrtv.postprice.model;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ResultInfo implements java.io.Serializable {
    public ResultInfo()
    {
        succ=true;
    }
    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    private boolean succ;
    private String errorMsg;
}
