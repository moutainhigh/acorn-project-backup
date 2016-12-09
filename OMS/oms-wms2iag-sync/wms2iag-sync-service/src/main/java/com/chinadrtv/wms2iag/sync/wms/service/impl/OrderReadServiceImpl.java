package com.chinadrtv.wms2iag.sync.wms.service.impl;

import com.chinadrtv.wms2iag.sync.dal.model.OrderExt;
import com.chinadrtv.wms2iag.sync.dal.wms.dao.WmsOrderExtDao;
import com.chinadrtv.wms2iag.sync.wms.service.OrderReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderReadServiceImpl implements OrderReadService {

    @Autowired
    private WmsOrderExtDao wmsOrderExtDao;

    @Override
    public List<OrderExt> readOrders(Date startDate) {
        //TODO:按照时间排序（是否直接在数据库端已经排序了）
        return wmsOrderExtDao.findOrders(startDate);
    }
}
