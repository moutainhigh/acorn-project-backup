package com.chinadrtv.erp.sales.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.OrderChange;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-13
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderChangeService extends GenericService<OrderChange, Long> {
    OrderChange getOrderChange(Long orderChangeId);
    OrderChange mergeBatchOrderChanges(List<Long> orderChangeIdList);
    OrderChange saveOrderChange(OrderChange orderChange);
}
