package com.chinadrtv.erp.sales.dto;

import com.chinadrtv.erp.model.OrderChange;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderChangeDto extends OrderChange {
    public String getOrdertypeName() {
        return ordertypeName;
    }

    public void setOrdertypeName(String ordertypeName) {
        this.ordertypeName = ordertypeName;
    }

    private String ordertypeName;
}
