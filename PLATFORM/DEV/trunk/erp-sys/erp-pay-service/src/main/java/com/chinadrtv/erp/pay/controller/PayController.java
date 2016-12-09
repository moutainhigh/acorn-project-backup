package com.chinadrtv.erp.pay.controller;

import com.chinadrtv.erp.pay.core.constant.CreditcardCode;
import com.chinadrtv.erp.pay.core.model.OnlinePayment;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.service.OnlinePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping(value = "onlinePay")
public class PayController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PayController.class);
    @Autowired
    @Qualifier("onlinePayService")
    private OnlinePayService onlinePayService;

    public PayController()
    {
        logger.debug("PayController is created!");
    }

    @RequestMapping(value = "/card")
    @ResponseBody
    public PayResult cardPay(@RequestBody OnlinePayment onlinePayment)
    {
        logger.debug("---begin online pay---");
        if(onlinePayment==null)
        {
            logger.error("input parm is null");
            return null;
        }

        logger.debug("paytype:"+onlinePayment.getPaytype());
        logger.debug("card no:"+onlinePayment.getCardNo());
        logger.debug("credentials No:"+onlinePayment.getCredentialsNo());
        logger.debug("extra code:"+onlinePayment.getExtraCode());
        logger.debug("mobile:"+onlinePayment.getMobile());
        logger.debug("order id:"+onlinePayment.getOrderId());
        logger.debug("amount:"+onlinePayment.getAmount());
        logger.debug("credentials Type:"+onlinePayment.getCredentialsType());
        logger.debug("expiry Date:"+onlinePayment.getExpiryDate());
        logger.debug("stage Num:"+onlinePayment.getStageNum());
        try
        {
            PayResult payResult = onlinePayService.pay(onlinePayment);
            if(!payResult.isSucc())
            {
                logger.error(payResult.getErrorMsg());
            }
            return payResult;
        }catch (Exception exp)
        {
            logger.error(exp.getMessage(), exp);
            PayResult payResult=new PayResult();
            payResult.setErrorMsg(exp.getMessage());
            payResult.setErrorCode(CreditcardCode.SystemError);

            return payResult;
        }
    }
}
