package com.chinadrtv.wms2iag.sync.iagent.service;

import com.chinadrtv.wms2iag.sync.dal.model.OrderExt;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderSyncService {
    void syncOrders(List<OrderExt> orderExtList);
}
