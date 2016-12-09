package com.chinadrtv.erp.sales.core.util;

import com.chinadrtv.erp.sales.core.model.CreditcardOnlineAuthorizationBase;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class CreditcardAuthorizationException extends RuntimeException {
    public CreditcardOnlineAuthorizationBase getCreditcardOnlineAuthorizationBase() {
        return creditcardOnlineAuthorizationBase;
    }

    public void setCreditcardOnlineAuthorizationBase(CreditcardOnlineAuthorizationBase creditcardOnlineAuthorizationBase) {
        this.creditcardOnlineAuthorizationBase = creditcardOnlineAuthorizationBase;
    }

    private CreditcardOnlineAuthorizationBase creditcardOnlineAuthorizationBase;

    public CreditcardAuthorizationException(CreditcardOnlineAuthorizationBase creditcardOnlineAuthorizationBase)
    {
         this.creditcardOnlineAuthorizationBase=creditcardOnlineAuthorizationBase;
    }
}
