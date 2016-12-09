package com.chinadrtv.erp.sales.core.model;

import org.apache.commons.lang.StringUtils;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CreditcardOnlineAuthorizationBase {
    //protected String status;
    protected String errorCode;
    protected String errorMsg;

    public CreditcardOnlineAuthorizationBase()
    {

    }

    public CreditcardOnlineAuthorizationBase(String errorCode,String errorMsg)
    {
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
    }

    public CreditcardOnlineAuthorizationBase(String errorCode,String errorMsg, String status)
    {
        this.errorCode=errorCode;
        this.errorMsg=errorMsg;
        //this.status=status;
    }
    /*public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSucc()
    {
        if(StringUtils.isEmpty(this.errorCode))
            return true;
        return false;
    }
}
