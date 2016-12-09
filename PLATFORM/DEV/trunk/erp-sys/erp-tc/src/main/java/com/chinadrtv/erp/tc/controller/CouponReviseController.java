package com.chinadrtv.erp.tc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.tc.core.model.ConTicket;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.service.CouponReviseService;

/**
 * Author  :taoyawei
 * Message：校正赠券
 */
@Controller
@RequestMapping({"/coupon"})
public class CouponReviseController extends TCBaseController
{
    private static final Logger logger = LoggerFactory.getLogger(CouponReviseController.class);
    @Autowired
    private CouponReviseService couponReviseService;
    @RequestMapping(value={"/revise"}, method={org.springframework.web.bind.annotation.RequestMethod.POST}, headers={"Content-Type=application/json"})
    @ResponseBody
    public OrderReturnCode getCouponRevisebyProc(@RequestBody ConTicket conTicket)
    {
        logger.debug("start get ZengquanRevise");
        try {
            logger.debug("start get ZengquanReviseEnd");
            couponReviseService.getCouponReviseproc(conTicket);
            return new OrderReturnCode("000", "赠券校正成功");
        } catch (Exception e) {
            logger.debug("end get ZengquanReviseError");
            return new OrderReturnCode("100", "赠券校正错误:" + e.getMessage());
        }
    }
}