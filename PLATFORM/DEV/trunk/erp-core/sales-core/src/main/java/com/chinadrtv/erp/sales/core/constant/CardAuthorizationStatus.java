package com.chinadrtv.erp.sales.core.constant;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public final class CardAuthorizationStatus {
    /**
     * 新状态
     */
    public final String Normal="1";
    /**
     * 调用分期交易前
     */
    public final String PreHirePurchase="2";
    /**
     * 调用分期交易后
     */
    public final String HirePurchase="3";
    /**
     * 分期交易取消前
     */
    public final String PreHirePurchaseCancel="4";
    /**
     * 分期交易取消后
     */
    public final String  HirePurchaseCancel="5";
}
