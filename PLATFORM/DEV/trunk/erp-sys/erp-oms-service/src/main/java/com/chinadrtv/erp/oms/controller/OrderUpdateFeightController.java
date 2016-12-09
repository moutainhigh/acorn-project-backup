package com.chinadrtv.erp.oms.controller;

import java.util.Map;

import com.chinadrtv.erp.sales.core.service.OrderhistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.tc.core.model.OrderReturnCode;

/**
 * Author :taoyawei Message:修改订单运费
 */
@Controller
@RequestMapping({ "/orderfreight" })
public class OrderUpdateFeightController extends TCBaseController {

	private static final Logger logger = LoggerFactory.getLogger(OrderUpdateFeightController.class);

	@Autowired
	private OrderhistService orderhistService;

	/**
	 * 修改订单运费
	 * 
	 * @param params
	 * @return OrderReturnCode
	 */
	@RequestMapping(value = { "/update" }, method = RequestMethod.POST, headers = { "Content-Type=application/json" })
	@ResponseBody
	public OrderReturnCode updateOrderFeight(@RequestBody Map<String, Object> params) {
		try {
			/*
			 * String orderid = map.get("orderid").toString(); String upoint =
			 * map.get("mdusr").toString(); BigDecimal freight = new
			 * BigDecimal(0); int upid = 0; freight = new
			 * BigDecimal(Double.parseDouble(map.get("mailprice").toString()));
			 * orderDelDetailService.insertTcHistory(orderid);// 生成快照 upid =
			 * orderUpFrightService.updateOrderhistFreight(orderid, freight,
			 * upoint);// 修改订单运费 upid =
			 * orderUpFrightService.updateOrderdetFreight(orderid, freight,
			 * upoint);// 修改订单明细 // 主销品运费
			 */

			orderhistService.updateOrderFreight(params);

			return new OrderReturnCode("000", "修改运费成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改运费失败", e);
			return new OrderReturnCode("100", "修改运费失败"+e.getMessage());
		}
	}

}