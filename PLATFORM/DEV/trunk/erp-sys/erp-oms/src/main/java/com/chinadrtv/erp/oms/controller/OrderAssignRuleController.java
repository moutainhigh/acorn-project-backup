/*
 * @(#)OmsDeliveryRegulationController.java 1.0 2013-3-25上午11:03:12
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.model.OrderChannel;
import com.chinadrtv.erp.model.Warehouse;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.OrderAssignRuleDto;
import com.chinadrtv.erp.oms.service.OrderAssignRuleService;
import com.chinadrtv.erp.oms.service.OrderChannelService;
import com.chinadrtv.erp.oms.service.WarehouseService;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-3-25 上午11:03:12 
 * 
 */
@Controller
@RequestMapping("/carrier")
public class OrderAssignRuleController {
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderAssignRuleController.class);
	
	@Autowired
	private OrderAssignRuleService orderAssignRuleService;
	@Autowired
	private OrderChannelService orderChannelService;
	@Autowired
	private WarehouseService warehouseService;

	@RequestMapping(value = "/index")
	public ModelAndView index(){
		List<OrderChannel> channelList = orderChannelService.getAllOrderChannel();
		List<Warehouse> warehouseList = warehouseService.getAllWarehouses();
		
		ModelAndView mav = new ModelAndView("carrier/index");
		mav.addObject("channelList", channelList);
		mav.addObject("warehouseList", warehouseList);
		return mav;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryPageList")
	@ResponseBody
	public Map<String, Object> queryPageList(OrderAssignRuleDto oar, DataGridModel dataGridModel) {

		Map<String, Object> resultMap = orderAssignRuleService.queryPageList(oar, dataGridModel);
		List<OrderAssignRule> pageList = (List<OrderAssignRule>) resultMap.get("rows");
		for(OrderAssignRule orderAssignRule : pageList){
			orderAssignRule.getOrderChannel().setOrderPayTypes(null);
			if(null!=orderAssignRule.getAreaGroup()){
				orderAssignRule.getAreaGroup().setAreaGroupDetails(null);
				orderAssignRule.getAreaGroup().setOrderAssignRules(null);
				orderAssignRule.getAreaGroup().setOrderChannel(null);
			}
		}

		return resultMap;
	}
	
	/**
	 * 新增修改
	* @Description: 
	* @param oar
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/persist")
	@ResponseBody
	public Map<String, Object> persist(OrderAssignRule oar){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		try {
			orderAssignRuleService.saveOrUpdate(oar);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "保存地址规则失败: " + e.getMessage();
			logger.error("保存地址规则失败: ", e);
		}
		resultMap.put("success", success);
		resultMap.put("message", message);
		
		return resultMap;
	}
	
	/**
	 * 启用禁用
	* @Description: 
	* @param oar
	* @return
	* @return Map<String,Object>
	* @throws
	 */
	@RequestMapping(value = "/changeStatus")
	@ResponseBody
	public Map<String, Object> changeStatus(OrderAssignRule oar){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		try {
			orderAssignRuleService.changeStatus(oar);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "变更地址规则状态失败: " + e.getMessage();
			logger.error("变更地址规则状态失败: ", e);
		}
		resultMap.put("success", success);
		resultMap.put("message", message);
		
		return resultMap;
	}
}
