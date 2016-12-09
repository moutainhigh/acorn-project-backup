/*
 * @(#)SystemMonitorController.java 1.0 2014-2-27下午1:50:09
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.dto.QuartzTriggerDto;
import com.chinadrtv.erp.marketing.service.CustomerMonitorService;
import com.chinadrtv.erp.marketing.service.SchedulerService;
import com.chinadrtv.erp.model.marketing.CustomerMonitor;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-2-27 下午1:50:09
 * 
 */
@Controller
@RequestMapping("system")
public class SystemMonitorController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(SystemMonitorController.class);
	@Autowired
	private CustomerMonitorService customerMonitorService;
	@Autowired
	private SchedulerService schedulerService;

	/**
	 * 
	 * 客户群监控初始化页面
	 */
	@RequestMapping("customerMonitorInit")
	public ModelAndView CustomerMonitorInit() {
		ModelAndView result = new ModelAndView("system/customerMonitorInit");

		return result;
	}

	/**
	 * 
	 * 客户群监控分面
	 * 
	 */
	@ResponseBody
	@RequestMapping("customerMonitorList")
	public Map<String, Object> list(CustomerMonitor customerMonitor,
			DataGridModel dataGridModel) {

		return customerMonitorService.list(customerMonitor, dataGridModel);

	}

	@RequestMapping("quartzInit")
	public ModelAndView quartzInit() {
		ModelAndView result = new ModelAndView("system/quartzInit");

		return result;
	}

	@ResponseBody
	@RequestMapping("quartzList")
	public Map<String, Object> quartzList(QuartzTriggerDto quartzTriggerDto,
			DataGridModel dataGridModel) {

		return schedulerService
				.getQrtzTriggers(quartzTriggerDto, dataGridModel);
	}

}
