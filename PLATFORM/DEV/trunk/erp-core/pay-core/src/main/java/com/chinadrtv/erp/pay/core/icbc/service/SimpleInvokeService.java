package com.chinadrtv.erp.pay.core.icbc.service;

import com.chinadrtv.erp.pay.core.icbc.model.InvokeListener;
import com.chinadrtv.erp.pay.core.icbc.model.Marshal;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-8
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface SimpleInvokeService {
    void addListener(InvokeListener listener);
    void init(String serverIp,int port,int timeOut);
    void callService(Marshal input, Marshal output);
    void close();
}
