package com.chinadrtv.erp.sales.controller;

import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.Payment;
import com.chinadrtv.erp.pay.core.service.OnlinePayAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/ICBCCreditService")
public class ICBCController {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ICBCController.class);

    @Qualifier("ICBCCreditcardServiceImpl")
    @Autowired
    private OnlinePayAdapterService onlinePayAdapterService;

    /*@InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(CredentialsType.class,new CredentialsTypeEnumUtils());
    }*/

    @RequestMapping(value = "/onlinepay",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public PayResult onlinePay(@RequestBody Payment payment)
    {
        logger.info("begin online pay");
        /*PayResult payResult=new PayResult();
        payResult.setErrorCode("");
        payResult.setErrorMsg("");
        payResult.setAuthID("111");
        payResult.setBatchNum("222");
        payResult.setTransTime(new Date());
        payResult.setHpAmount(new BigDecimal("22.3"));
        payResult.setHpFee(new BigDecimal("1.1"));
        payResult.setOrderNum("345");
        payResult.setRefNum("666");
        payResult.setTotalAmount(new BigDecimal("111.1"));
        payResult.setTraceNum(345);
        return payResult;*/
        try{
            PayResult payResult = onlinePayAdapterService.pay(payment);
            if(!payResult.isSucc())
            {
                logger.error("icbc online pay error:"+payResult.getErrorMsg());
            }
            return payResult;
        }catch (Exception exp)
        {
            logger.error("icbc online pay system error:",exp);
            PayResult payResult1=new PayResult();
            payResult1.setErrorCode("01");
            payResult1.setErrorMsg(exp.getMessage());
            return payResult1;
        }
    }

    @ExceptionHandler
    @ResponseBody
    public PayResult handleException(Exception ex) {
        PayResult payResult=new PayResult();
        payResult.setErrorCode("01");
        payResult.setErrorMsg(ex.getMessage());
        return payResult;
    }
}
