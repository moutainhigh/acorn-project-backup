package com.chinadrtv.order.choose.service;


import com.chinadrtv.order.choose.dal.model.OrderAssign;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderRetryAssignService {
    List<Integer> addRetryOrders(List<OrderAssign> orderAssignList);
    void removeRetryOrders(List<Integer> indexList);
}
