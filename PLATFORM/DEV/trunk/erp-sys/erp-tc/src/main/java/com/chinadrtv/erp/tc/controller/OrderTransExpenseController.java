package com.chinadrtv.erp.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.tc.core.model.OrderTransExpenseReturnInfo;
import com.chinadrtv.erp.tc.core.service.OrderTransExpenseService;

/**
 * 订单运费计算
 * User: liyu
 * Date: 13-1-28
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/transexpense")
public class OrderTransExpenseController extends TCBaseController{

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderTransExpenseController.class);

    @Autowired
    private OrderTransExpenseService orderTransExpenseService;

    @RequestMapping(value = "/get",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public OrderTransExpenseReturnInfo getTransExpense(@RequestBody String conditionStr){
        logger.debug(conditionStr);
        return orderTransExpenseService.getOrderTransExpenseByAgent(conditionStr);
    }
}
