package com.chinadrtv.erp.core.service;

import com.chinadrtv.erp.model.agent.Order;

/**
 * 订单下传规则检查接口.
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderRuleCheckService {
    /**
     * 检查订单是否符合规则
     * @param order
     * @return true-表示符合规则
     */
    boolean checkOrder(Order order);
    String getRuleName();
    String getBPMType();
}
