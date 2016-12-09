package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.model.OrderAssignRule;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-24
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderAssignRuleService {
    List<OrderAssignRule> findMatchRuleFromAmount(BigDecimal amount);
    List<OrderAssignRule> findMatchRuleFromProds(List<String> prods);
    List<OrderAssignRule> findMatchRuleFromCondition(List<Long> orderChannelIdList,List<Long> areaGroupIdList);
}
