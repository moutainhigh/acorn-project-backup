package com.chinadrtv.erp.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.constant.OrderCode;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.utils.OrderException;
import com.chinadrtv.erp.tc.service.OrderhistService;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-1-30
 */
@Controller
@RequestMapping("/orderdet/1")
public class OrderdetController extends TCBaseController{
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderdetController.class);

    @Autowired
    private OrderhistService orderhistService;

    @RequestMapping(value = "/save",method= RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseBody
    public OrderReturnCode saveOrderdet(OrderDetail orderdet)
    {
        logger.debug("start save orderdet");
        try
        {
            orderhistService.updateOrderhistOrderdet(orderdet);

            logger.debug("save orderdet succ");

            return new OrderReturnCode(OrderCode.SUCC,null);
        }
        catch (OrderException orderExp)
        {
            logger.debug("save orderdet error");
            return orderExp.getOrderReturnCode();
        }
        catch (Exception exp)
        {
            logger.debug("save orderdet unkown error");
            return new OrderReturnCode(OrderCode.SYSTEM_ERROR,exp.getMessage());
        }
    }
}
