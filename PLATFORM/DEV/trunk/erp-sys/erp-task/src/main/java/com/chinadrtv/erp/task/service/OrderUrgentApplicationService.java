package com.chinadrtv.erp.task.service;

import com.chinadrtv.erp.task.entity.OrderUrgentApplication;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderUrgentApplicationService {
    List<OrderUrgentApplication> getAllNotHandleUrgentOrders();
    List<OrderUrgentApplication> getHandledByOrderIds(List<String> orderIdList);
}
