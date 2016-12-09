package com.chinadrtv.erp.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.tc.core.model.ConTicket;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.service.CouponService;

/**
 * Author :taoyawei 
 * Message ：新增赠券
 */
@Controller
@RequestMapping({ "/coupon" })
public class CouponController extends TCBaseController {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CouponController.class);

	@Autowired
	private CouponService couponService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Content-Type=application/json")
	@ResponseBody
	public OrderReturnCode getCouponRevisebyProc(@RequestBody ConTicket conTicket) {
		try {
			couponService.getCouponproc(conTicket);
			return new OrderReturnCode("000", "赠券新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增订单赠券失败", e);
			return new OrderReturnCode("120", "赠券新增失败" + e.getMessage());
		}
	}
}