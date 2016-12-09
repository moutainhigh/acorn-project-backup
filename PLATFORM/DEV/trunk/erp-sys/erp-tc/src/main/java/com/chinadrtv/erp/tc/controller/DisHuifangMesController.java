package com.chinadrtv.erp.tc.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.tc.core.model.OrderReturnCode;
import com.chinadrtv.erp.tc.core.service.DisHuifangMesService;

/**
 * Author :taoyawei 
 * Message:批量处理回访信息
 */
@Controller
@RequestMapping({ "/dishuifang" })
public class DisHuifangMesController extends TCBaseController {
	private static final Logger logger = LoggerFactory.getLogger(DisHuifangMesController.class);
	@Autowired
	private DisHuifangMesService disHuifangMesService;

	@RequestMapping(value = { "/update" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST }, headers = { "Content-Type=application/json" })
	@ResponseBody
	public OrderReturnCode upOrderHistByfive(@RequestBody Map<String, Object> map) {
		logger.debug("start get Dist");
		try {
			logger.debug("start update BatchingHuifangMessage");
			if (map.size() != 10) {
				return new OrderReturnCode("100", "处理回访信息参数错误");
			}
			boolean upid = disHuifangMesService.upOrderHistByfive(map);
			if (upid) {
				return new OrderReturnCode("000", "处理回访信息成功");
			}
			return new OrderReturnCode("100", "处理回访信息参数失败");

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("end update BatchingHuifangMessage");
			return new OrderReturnCode("100", "处理回访信息错误:" + e.getMessage());
		}
	}

	@RequestMapping(value = { "/couponrevice" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST }, headers = { "Content-Type=application/json" })
	@ResponseBody
	public OrderReturnCode couponRevisebyProc(@RequestBody Map<String, Object> params) {
		try {
			logger.debug("start Revice couponRevisebyProc");
			if (params.size() != 2) {
				return new OrderReturnCode("100", "处理回访信息的赠券校正参数错误");
			}
			disHuifangMesService.couponReviseproc(params);
			return new OrderReturnCode("000", "处理回访信息的赠券校正成功");
		} catch (Exception e) {
			logger.debug("end Revise couponRevisebyProc");
			return new OrderReturnCode("100", "处理回访信息的赠券校正错误:" + e.getMessage());
		}
	}
}