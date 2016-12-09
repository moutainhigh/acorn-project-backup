package com.chinadrtv.erp.sales.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.OrderChange;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-13
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderChangeDao extends GenericDao<OrderChange, Long> {
    OrderChange getOrderChangeFromProcInstId(String procInstId);
    List<OrderChange> getOrderChangeList();
}
