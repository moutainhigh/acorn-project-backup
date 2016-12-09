package com.chinadrtv.erp.oms.model;

import java.io.Serializable;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-7-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class NetOrderResult implements Serializable {
    private boolean result;
    private String message_code;
    private String message;

    public NetOrderResult()
    {
        result=false;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage_code() {
        return message_code;
    }

    public void setMessage_code(String message_code) {
        this.message_code = message_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
