package com.chinadrtv.order.choose.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.OrderAssignLog;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderAssignLogService extends GenericService<OrderAssignLog, Long> {
    void saveOrUpateOrderAssignLog(OrderAssignLog orderAssignLog);
}
