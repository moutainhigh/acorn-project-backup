package com.chinadrtv.erp.tc.core.dto;

import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.marketing.UserBpm;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-6
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderAuditExtDto {
    private Order order;
    private UserBpm userBpm;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public UserBpm getUserBpm() {
        return userBpm;
    }

    public void setUserBpm(UserBpm userBpm) {
        this.userBpm = userBpm;
    }
}
