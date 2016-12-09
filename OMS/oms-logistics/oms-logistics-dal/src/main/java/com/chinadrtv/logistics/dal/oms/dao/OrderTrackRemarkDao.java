package com.chinadrtv.logistics.dal.oms.dao;

import com.chinadrtv.logistics.dal.model.OrderTrackRemark;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-12-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderTrackRemarkDao {
    List<OrderTrackRemark> findTrackRemarks(String orderId);
}
