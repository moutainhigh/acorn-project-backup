package com.chinadrtv.erp.pay.core.model;

/**
 * Sales在线支付信息.
 * User: 徐志凯
 * Date: 13-8-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OnlinePayment extends Payment {
    /**
     * 具体的在线支付类型
     */
    private String paytype;

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }
}
