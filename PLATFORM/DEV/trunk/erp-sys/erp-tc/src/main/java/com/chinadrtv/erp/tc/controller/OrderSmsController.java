package com.chinadrtv.erp.tc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.model.agent.OrderSms;
import com.chinadrtv.erp.tc.core.service.OrderSmsService;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-1-29
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/ordersms")
public class OrderSmsController extends TCBaseController{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderSmsController.class);

    @Autowired
    private  OrderSmsService orderSmsService;

    @RequestMapping(value = "/save",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public Map<String, Object>  getTransExpense(@RequestBody OrderSms orderSms){
        logger.debug(orderSms.toString());

        Map<String, Object> resultMap = new HashMap<String, Object>();

        try {

            orderSmsService.sendMsg(orderSms.getMessage(), orderSms.getTo(), orderSms.getCustomerid(), orderSms.getOrderid());

            resultMap.put("code", "000");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", "100");
            resultMap.put("desc", e.getMessage());

        }
        return resultMap;

        //return orderTransExpenseService.getOrderTransExpenseByAgent(conditionStr);
    }
}
