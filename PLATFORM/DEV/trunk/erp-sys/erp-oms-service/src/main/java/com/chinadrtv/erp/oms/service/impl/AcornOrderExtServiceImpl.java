package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.oms.service.AcornOrderExtService;
import com.chinadrtv.erp.tc.core.dao.OrderhistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-4-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class AcornOrderExtServiceImpl implements AcornOrderExtService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AcornOrderExtServiceImpl.class);
    public AcornOrderExtServiceImpl()
    {
       logger.debug("AcornOrderExtServiceImpl is created");
    }

    @Autowired
    private OrderhistDao orderhistDao;

    @Value("${com.chinadrtv.erp.oms.service.impl.AcornOrderExtServiceImpl.orderIdAssignCount}")
    private Integer orderIdAssignCount;

    @Override
    public String getOrderId() {
        return orderhistDao.getOrderId();
    }

    @Override
    public Integer getMaxCount() {
        return orderIdAssignCount;
    }
}
