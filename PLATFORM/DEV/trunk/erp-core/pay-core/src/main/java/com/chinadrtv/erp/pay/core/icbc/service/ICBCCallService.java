package com.chinadrtv.erp.pay.core.icbc.service;

import com.chinadrtv.erp.pay.core.icbc.model.*;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ICBCCallService {
    void init(String serverIp, int port,int timeOut);
    ICBCResPay pay(ICBCReqPay icbcReqPay);
    ICBCResPayOnce payOnce(ICBCReqPayOnce icbcReqPayOnce);
    ICBCResBaseInfo authentication(ICBCReqAuthentication icbcReqAuthentication);
    void close();
    ICBCResBaseInfo signIn(ICBCReqBaseInfo reqBaseInfo);
    ICBCResBaseInfo signOut(ICBCReqBaseInfo reqBaseInfo);
}
