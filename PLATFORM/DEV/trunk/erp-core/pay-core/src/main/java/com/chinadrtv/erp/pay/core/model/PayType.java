package com.chinadrtv.erp.pay.core.model;

/**
 * 在线支付类型信息
 * User: 徐志凯
 * Date: 13-8-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class PayType {
    public PayType(String type,String name)
    {
        payType=type;
        payTypeName=name;
    }

    public PayType()
    {
    }
    /**
     * 在线支付类型 - 招行在线 支付等
     */
    private String payType;
    /**
     * 在线支付显示名称
     */
    private String payTypeName;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }
}
