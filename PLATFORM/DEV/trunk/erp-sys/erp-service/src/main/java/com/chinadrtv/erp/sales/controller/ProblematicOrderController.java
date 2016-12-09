/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.NamesService;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.ProblematicOrder;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.sales.service.ProblematicOrderService;
import com.chinadrtv.erp.tc.core.dto.ProblematicOrderDto;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;

/**
 * 2013-7-23 下午2:10:53
 * @version 1.0.0
 * @author yangfei
 *
 */
@Controller
@RequestMapping(value = "problematicOrder")
public class ProblematicOrderController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ProblematicOrderController.class);
    
	@Autowired
	private ProblematicOrderService problematicOrderService;
	
    @Autowired
    private NamesService namesService;
    
	@Autowired
	private SysMessageService sysMessageService;
	
	@Autowired
	private OrderhistService orderhistService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/query")
	@ResponseBody
    public Map<String, Object> queryProblematicOrder(DataGridModel dataGrid, ProblematicOrderDto problematicOrderDto) {
		Map<String, Object> pageMap = null;
		try {
			pageMap = problematicOrderService.query(problematicOrderDto, dataGrid);
		} catch (Exception e) {
			logger.error("error occurs!",e);
		}
		return pageMap;
    }

	@RequestMapping(value = "/replyProblematicOrder")
	@ResponseBody
	public String replyProblematicOrder(
			@RequestParam(required = true, defaultValue = "") String replyContent,
			@RequestParam(required = true, defaultValue = "") String problemId
			){
		ProblematicOrderDto problematicOrderDto = new ProblematicOrderDto();
		problematicOrderDto.setProblemId(problemId);
		problematicOrderDto.setReplyContent(replyContent);
		boolean isSuc = problematicOrderService.replyProblematicOrder(problematicOrderDto);
		try {
			if(isSuc) {
				//如果座席已处理，通知消息发送端，不要再发送消息
				ProblematicOrder pOrder = problematicOrderService.get(problemId);
				sysMessageService.handleMessage(MessageType.PROBLEM_ORDER, pOrder.getOrderId(), pOrder.getCreatedDate());
			}
		} catch(Exception e) {
			logger.error("通知失败",e);
		}
		return "success";
	}

	@RequestMapping(value = "/queryContent")
	@ResponseBody
	public String replyProblematicOrder(
			@RequestParam(required = true, defaultValue = "") String problemId
			){
		String content = "";
		ProblematicOrderDto problematicOrderDto = new ProblematicOrderDto();
		problematicOrderDto.setProblemId(problemId);
		ProblematicOrder pOrder = problematicOrderService.get(problemId);
		if(pOrder.getReplyDsc() != null) {
			content = pOrder.getReplyDsc();
		}
		return content;
	}
	
	@RequestMapping(value = "/problematicOrderMarking")
	@ResponseBody
	public ModelAndView problematicOrderMarking(
			@RequestParam(required = true, defaultValue = "") String orderId ){
		ModelAndView mav = new ModelAndView();
		mav.addObject("orderId",orderId);
		mav.addObject("problemTypes",LookupNames("PROBLEMTYPE"));
		mav.setViewName("myorder/problematicOrderMark");
		return mav;
	}
	
	@RequestMapping(value = "/markProblematicOrder")
	@ResponseBody
	public String markProblematicOrder(
			@RequestParam(required = true, defaultValue = "") String orderId,
			@RequestParam(required = true, defaultValue = "") String problemDsc,
			@RequestParam(required = true, defaultValue = "") String problemType
			){
		AgentUser agentUser = SecurityHelper.getLoginUser();
		problematicOrderService.markProblematicOrder(agentUser.getUserId(), orderId, problemType, problemDsc);
		
		try {
			SysMessage sMessage = new SysMessage();
			Order order = orderhistService.getOrderHistByOrderid(orderId);
			sMessage.setContent(orderId);
			sMessage.setReceiverId(order.getCrusr());
			sMessage.setCreateUser(order.getCrusr());
			sMessage.setRecivierGroup(userService.getUserGroup(order.getCrusr()));
			sMessage.setCreateDate(new Date());
			sMessage.setDepartmentId(userService.getDepartment(order.getCrusr()));
			sMessage.setSourceTypeId(String.valueOf(MessageType.PROBLEM_ORDER.getIndex()));
			sysMessageService.addMessage(sMessage);
		} catch (Exception e) {
			logger.error("发送消息失败",e);
		}
		return "success";
	}
	
	public Map<String, String> LookupNames(String tid) {
		List<Names> orderTypeList = null;
		orderTypeList = namesService.getAllNames(tid);
		Map<String, String> map = new HashMap<String, String>();
		for (Names orderType : orderTypeList) {
			map.put(orderType.getId().getId(),orderType.getDsc());
		}
		return map;
	}

}
