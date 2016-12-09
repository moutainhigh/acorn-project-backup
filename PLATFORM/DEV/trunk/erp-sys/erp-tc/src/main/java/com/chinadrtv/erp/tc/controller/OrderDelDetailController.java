package com.chinadrtv.erp.tc.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.service.OrderDelDetailService;
import com.chinadrtv.erp.tc.core.service.OrderHistoryService;

/**
 * Author :taoyawei Message: 删除订单明细信息
 */
@Controller
@RequestMapping({ "/orderdet" })
public class OrderDelDetailController extends TCBaseController {
	private static final Logger logger = LoggerFactory.getLogger(OrderDelDetailController.class);
	@Autowired
	private OrderDelDetailService orderDelDetailService;
	@Autowired
	private OrderHistoryService orderHistoryService;

	@RequestMapping(value = { "/delorderdetail" }, method = RequestMethod.POST, headers = { "Content-Type=application/json" })
	@ResponseBody
	public OrderReturnCode getCouponRevisebyProc(@RequestBody Map<String, Object> map) {
		logger.debug("start get DelOrderDetailStart");
		String str = "Y";
		int detid = 0;
		String orderid = "";
		try {
			OrderDetail orderdet1 = orderDelDetailService.queryOrderdetbydetid(map.get("orderdetid").toString());
			if (orderdet1.getOrderid() != null) {
				orderid = orderdet1.getOrderid();
				 orderDelDetailService.insertTcHistory(orderid);//生成快照
				detid = orderDelDetailService.delOrderDetail(orderdet1);// 删除明细
			}
			if (detid != 0) {
				List<OrderDetail> orderdetList = orderDelDetailService.queryOrderdetbyorid(orderid);
				orderDelDetailService.totalOrderHistprice(orderdetList, orderid);// 重算金额及运费
				str = orderDelDetailService.conticketProc(map.get("mdusr").toString(), orderid);// 重算返券
				return new OrderReturnCode("000", "删除订单明细成功");
			} else {
				return new OrderReturnCode("100", "删除订单明细失败");
			}

		} catch (Exception e) {
			logger.debug("start get DelOrderDetailError");
			e.printStackTrace();
			return new OrderReturnCode("100", "订单明细错误:" + e.getMessage());
		}
	}

}