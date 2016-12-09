package com.chinadrtv.erp.sales.core.service.impl;

import com.chinadrtv.erp.core.service.OrderRuleCheckService;
import com.chinadrtv.erp.marketing.core.common.UserBpmTaskType;
import com.chinadrtv.erp.model.agent.Order;
import org.springframework.stereotype.Service;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("GetContactCheckServiceImpl")
public class GetContactCheckServiceImpl implements OrderRuleCheckService {
    @Override
    public boolean checkOrder(Order order) {
        if(!order.getContactid().equals(order.getGetcontactid()))
        {
            if(!"11".equals(order.getPaytype()))
                return false;
        }
        return true;
    }

    @Override
    public String getRuleName() {
        return UserBpmTaskType.ORDER_RECEIVER_CHANGE.name();
    }

    @Override
    public String getBPMType() {
        return String.valueOf(UserBpmTaskType.ORDER_RECEIVER_CHANGE.getIndex());
    }
}
