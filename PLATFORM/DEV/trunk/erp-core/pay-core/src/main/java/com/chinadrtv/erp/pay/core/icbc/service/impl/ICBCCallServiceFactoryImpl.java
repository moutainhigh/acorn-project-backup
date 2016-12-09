package com.chinadrtv.erp.pay.core.icbc.service.impl;

import com.chinadrtv.erp.pay.core.icbc.service.ICBCCallService;
import com.chinadrtv.erp.pay.core.icbc.service.ICBCCallServiceFactory;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-9
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
//@Service
public abstract class ICBCCallServiceFactoryImpl implements ICBCCallServiceFactory {
    public abstract ICBCCallService getICBCCallService();
}
