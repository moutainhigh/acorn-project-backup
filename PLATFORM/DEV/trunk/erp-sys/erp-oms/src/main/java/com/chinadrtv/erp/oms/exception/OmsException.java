package com.chinadrtv.erp.oms.exception;

import com.chinadrtv.erp.core.exception.BizException;

/**
 * OMS系统异常.
 * User: 徐志凯
 * Date: 13-4-1
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OmsException extends BizException {
    private String code;
    public OmsException(String code)
    {
        super("");
        this.code=code;
    }

    public OmsException(String code,String message)
    {
        super(message);
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
