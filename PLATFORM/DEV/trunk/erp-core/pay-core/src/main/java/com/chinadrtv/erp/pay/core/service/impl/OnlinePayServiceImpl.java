package com.chinadrtv.erp.pay.core.service.impl;

import com.chinadrtv.erp.pay.core.constant.CreditcardCode;
import com.chinadrtv.erp.pay.core.exception.OnlineException;
import com.chinadrtv.erp.pay.core.model.OnlinePayment;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.PayType;
import com.chinadrtv.erp.pay.core.model.Payment;
import com.chinadrtv.erp.pay.core.service.OnlinePayAdapterService;
import com.chinadrtv.erp.pay.core.service.OnlinePayService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Sales在线支付服务实现
 * User: 徐志凯
 * Date: 13-8-2
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
//@Service("OnlinePayServiceImpl")
public class OnlinePayServiceImpl implements OnlinePayService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OnlinePayServiceImpl.class);

    @Autowired
    private List<OnlinePayAdapterService> onlinePayAdapterServiceList;

    public List<PayType> getAllPayTypes() {
        if (onlinePayAdapterServiceList == null) {
            onlinePayAdapterServiceList = new ArrayList<OnlinePayAdapterService>();
        }

        List<PayType> payTypeList = new ArrayList<PayType>();
        for (OnlinePayAdapterService onlinePayAdapterService : onlinePayAdapterServiceList) {
            payTypeList.add(onlinePayAdapterService.getPayType());
        }

        return payTypeList;
    }

    public PayResult pay(OnlinePayment onlinePayment) {
        try {
            return getMatchPayService(onlinePayment).pay(onlinePayment);
        } catch (RuntimeException exp) {
            return parseResultFromException(exp);
        }
    }

    public PayResult cancelPay(OnlinePayment onlinePayment, PayResult prePayResult) {
        try {
            return getMatchPayService(onlinePayment).cancelPay(onlinePayment, prePayResult);
        } catch (RuntimeException exp) {
            return parseResultFromException(exp);
        }
    }

    private PayResult parseResultFromException(Exception exp) {
        logger.error("online pay error:", exp);
        PayResult payResult = new PayResult();
        if (exp instanceof OnlineException) {
            OnlineException onlineException = (OnlineException) exp;
            payResult.setErrorCode(onlineException.getErrorCode());
            payResult.setErrorMsg(onlineException.getErrorMessage());
        } else {
            payResult.setErrorCode(CreditcardCode.SystemError);
            payResult.setErrorMsg(exp.getMessage());
        }
        return payResult;
    }


    private OnlinePayAdapterService getMatchPayService(OnlinePayment onlinePayment) {
        if (onlinePayAdapterServiceList == null) {
            throw new OnlineException(CreditcardCode.NoService, "没有在线支付的服务");
        }
        if (onlinePayment == null) {
            throw new OnlineException(CreditcardCode.ParamError, "输入参数不正确");
        }
        if (StringUtils.isEmpty(onlinePayment.getPaytype())) {
            throw new OnlineException(CreditcardCode.ParamError, "未指定在线支付类型");
        }

        for (OnlinePayAdapterService onlinePayAdapterService : onlinePayAdapterServiceList) {
            PayType payType = onlinePayAdapterService.getPayType();
            if (onlinePayment.getPaytype().equalsIgnoreCase(payType.getPayType()) || onlinePayment.getPaytype().equalsIgnoreCase(payType.getPayTypeName())) {
                return onlinePayAdapterService;
            }
        }

        throw new OnlineException(CreditcardCode.ParamError, "没有对应的支付类型");
    }
}
