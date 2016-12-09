package com.chinadrtv.erp.pay.core.service;

import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.PayType;
import com.chinadrtv.erp.pay.core.model.Payment;

/**
 * 在线支付系统的支付接口
 * 各支付系统需要实现此接口与sales对接
 * User: 徐志凯
 * Date: 13-8-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OnlinePayAdapterService {
    /**
     * 支付的类型信息
     * @return
     */
    PayType getPayType();

    /**
     * 在线支付
     * @param payment
     * @return
     */
    PayResult pay(Payment payment);

    /**
     * 取消在线支付
     * @param payment  在线支付信息
     * @param prePayResult  对应在线支付的返回结果信息
     * @return
     */
    PayResult cancelPay(Payment payment, PayResult prePayResult);
}
