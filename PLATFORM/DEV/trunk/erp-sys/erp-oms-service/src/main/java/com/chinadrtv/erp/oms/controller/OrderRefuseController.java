package com.chinadrtv.erp.oms.controller;

import com.chinadrtv.erp.oms.model.OrderRefuseInfo;
import com.chinadrtv.erp.oms.service.OrderRefuseCheckService;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping("/order/refuse")
public class OrderRefuseController extends TCBaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderRefuseController.class);

    @Autowired
    private OrderRefuseCheckService orderRefuseCheckService;

    @RequestMapping(value = "/check",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public OrderReturnCode checkOrderRefuse(@RequestBody OrderRefuseInfo orderRefuseInfo)
    {
        if(orderRefuseInfo!=null)
        {
            logger.info("order id:"+orderRefuseInfo.getOrderId()+" - maild id:"+orderRefuseInfo.getMailId());
        }

        return orderRefuseCheckService.checkOrderRefuse(orderRefuseInfo);
    }
}
