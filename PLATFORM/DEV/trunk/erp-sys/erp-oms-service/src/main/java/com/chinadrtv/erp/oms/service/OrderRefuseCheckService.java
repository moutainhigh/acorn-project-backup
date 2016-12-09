package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.oms.model.OrderRefuseInfo;
import com.chinadrtv.erp.oms.model.OrderRefuseResult;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderRefuseCheckService {
    OrderReturnCode checkOrderRefuse(OrderRefuseInfo orderRefuseInfo);
}
