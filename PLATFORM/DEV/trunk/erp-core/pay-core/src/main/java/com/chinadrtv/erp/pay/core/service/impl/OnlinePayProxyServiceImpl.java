package com.chinadrtv.erp.pay.core.service.impl;

import com.chinadrtv.erp.pay.core.constant.CreditcardCode;
import com.chinadrtv.erp.pay.core.model.OnlinePayment;
import com.chinadrtv.erp.pay.core.model.PayResult;
import com.chinadrtv.erp.pay.core.model.PayType;
import com.chinadrtv.erp.pay.core.service.OnlinePayAdapterService;
import com.chinadrtv.erp.pay.core.service.OnlinePayService;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-19
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OnlinePayProxyServiceImpl implements OnlinePayService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OnlinePayProxyServiceImpl.class);

    @Value("${com.chinadrtv.erp.sales.core.proxy.url:}")
    private String payServiceUrl;

    @Autowired(required = false)
    @Qualifier("payRestTemplate")
    private RestTemplate template;

    @Autowired
    private List<OnlinePayAdapterService> onlinePayAdapterServiceList;

    @Override
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


    @Override
    public PayResult pay(OnlinePayment onlinePayment) {
        if(onlinePayment==null)
        {
            PayResult payResult=new PayResult();
            payResult.setErrorCode(CreditcardCode.ParamError);
            payResult.setErrorMsg("input parm is null");
            logger.error(payResult.getErrorMsg());
            return payResult;
        }
        if(template==null)
        {
            PayResult payResult=new PayResult();
            payResult.setErrorCode(CreditcardCode.SystemError);
            payResult.setErrorMsg("service is not config");

            logger.error(payResult.getErrorMsg());
            return payResult;
        }
        if(StringUtils.isBlank(payServiceUrl))
        {
            PayResult payResult=new PayResult();
            payResult.setErrorCode(CreditcardCode.SystemError);
            payResult.setErrorMsg("pay url is not config");

            logger.error(payResult.getErrorMsg());
            return payResult;
        }

        try{
            PayResult payResult=template.postForObject(payServiceUrl,onlinePayment,PayResult.class);
            if(payResult!=null&&!payResult.isSucc())
            {
                logger.error("pay result is error:" + payResult.getErrorMsg());
            }
            return payResult;
        }catch (Exception exp)
        {
            logger.error("json format error:"+exp.getMessage(), exp);
            PayResult payResult=new PayResult();
            payResult.setErrorCode(CreditcardCode.SystemError);
            payResult.setErrorMsg(exp.getMessage());
            return payResult;
        }
    }

    @Override
    public PayResult cancelPay(OnlinePayment onlinePayment, PayResult prePayResult) {
        logger.error("cancal pay is not implement");
        return null;
    }
}
