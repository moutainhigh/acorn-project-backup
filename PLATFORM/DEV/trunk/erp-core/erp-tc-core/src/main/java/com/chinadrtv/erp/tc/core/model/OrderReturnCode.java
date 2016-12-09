package com.chinadrtv.erp.tc.core.model;

/**
 * 订单服务返回信息
 * User: 徐志凯
 * Date: 13-1-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

public class OrderReturnCode extends ReturnInfo {

    public OrderReturnCode() {

    }

    public OrderReturnCode(String code, String desc) {
        this.setCode(code);
        this.setDesc(desc);
    }
}
