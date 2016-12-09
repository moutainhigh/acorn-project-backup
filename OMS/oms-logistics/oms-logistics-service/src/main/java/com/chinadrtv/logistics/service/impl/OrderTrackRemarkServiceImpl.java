package com.chinadrtv.logistics.service.impl;

import com.chinadrtv.logistics.dal.oms.dao.OrderTrackRemarkDao;
import com.chinadrtv.logistics.dal.model.OrderTrackRemark;
import com.chinadrtv.logistics.service.OrderTrackRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-12-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderTrackRemarkServiceImpl implements OrderTrackRemarkService {

    @Autowired
    private OrderTrackRemarkDao orderTrackRemarkDao;

    @Override
    public List<OrderTrackRemark> queryTrackRemarks(String orderId) {
        return orderTrackRemarkDao.findTrackRemarks(orderId);
    }
}
