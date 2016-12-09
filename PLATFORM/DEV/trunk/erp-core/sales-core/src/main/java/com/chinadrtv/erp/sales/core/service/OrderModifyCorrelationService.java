package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.model.OrderChange;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.sales.core.model.OrderModifyCorrelation;

import java.util.List;
import java.util.Map;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-3
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderModifyCorrelationService {
    /**
     * 根据传递进来的修改类型，来确定关联性修改
     * @param userBpmTaskTypeList
     * @return 返回关联新的修改（key-表示父流程 value-表示子流程）
     */
    List<OrderModifyCorrelation> getCorrelationOrder(List<UserBpmTaskType> userBpmTaskTypeList, List<List<UserBpmTaskType>> dynamicList);

    List<List<UserBpmTaskType>> getContentCorrelationFromOrder(OrderChange orderChange,List<UserBpmTaskType> userBpmTaskTypeList);
}
