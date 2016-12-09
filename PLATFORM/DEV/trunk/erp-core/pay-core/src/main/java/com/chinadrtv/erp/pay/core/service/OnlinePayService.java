package com.chinadrtv.erp.pay.core.service;

import com.chinadrtv.erp.pay.core.model.OnlinePayment;
import com.chinadrtv.erp.pay.core.model.Payment;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.PayType;

import java.util.List;

/**
 * Sales调用的在线支付接口
 * User: 徐志凯
 * Date: 13-8-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OnlinePayService {
    /**
     * 目前支持的所有在线支付的类型信息
     * @return
     */
    List<PayType> getAllPayTypes();

    /**
     * 在线支付
     * @param onlinePayment
     * @return
     */
    PayResult pay(OnlinePayment onlinePayment);

    /**
     * 取消在线支付
     * @param onlinePayment  在线支付信息
     * @param prePayResult  对应在线支付的返回结果信息
     * @return
     */
    PayResult cancelPay(OnlinePayment onlinePayment, PayResult prePayResult);
}
