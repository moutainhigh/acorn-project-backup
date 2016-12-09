package com.chinadrtv.erp.sales.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderCheck;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderCheckDao extends GenericDao<OrderCheck, Long> {
    List<OrderCheck> getOrderChecks(String orderId);
    void saveOrderChecks(List<OrderCheck> orderCheckList);
}
