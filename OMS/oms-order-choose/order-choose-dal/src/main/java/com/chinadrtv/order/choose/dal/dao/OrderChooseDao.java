package com.chinadrtv.order.choose.dal.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.order.choose.dal.model.OrderChooseQueryParm;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-4-23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderChooseDao extends GenericDao<Order, Long> {
    List<Order> findOrderAutoChoose(OrderChooseQueryParm queryParm);
    void updateOrderAssignFlag(List<Long> orderIdList, String oldAssignFlag, String newAssignFlag);
}
