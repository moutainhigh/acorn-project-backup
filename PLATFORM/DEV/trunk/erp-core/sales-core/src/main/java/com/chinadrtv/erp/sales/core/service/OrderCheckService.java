package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.core.service.OrderRuleCheckService;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.model.agent.Order;

import java.util.List;

/**
 * 订单规则检查接口.
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderCheckService {
    boolean checkOrder(Order order,UserBpmTaskType userBpmTaskType, String usrId);
    List<OrderRuleCheckService> attachOrderRules(Order order);
    String getCheckRuleName(String bpmType);
}
