/*
 * @(#)UrgentOrderController.java 1.0 2013-7-16下午1:10:57
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.cntrpbank.OrderUrgentApplication;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.service.OrderUrgentApplicationService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
import com.chinadrtv.erp.uc.dto.UrgentOrderDto;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.user.util.SecurityHelper;


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
 * @since 2013-7-16 下午1:10:57 
 * 
 */
@Controller
@RequestMapping(value = "/reminder")
public class OrderDeliveryReminderController extends BaseController {

	private static transient final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderDeliveryReminderController.class);
	
	@Autowired
	private OrderUrgentApplicationService orderUrgentApplicationService;
	@Autowired
	private OrderhistService orderhistService;
	@Autowired
	private SysMessageService sysMessageService;
	@Autowired
	private UserService userService;
	
    @InitBinder
    public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
    }
	
	@ResponseBody
	@RequestMapping(value = "/queryPageList")
	public Map<String, Object> queryPageList(UrgentOrderDto dto, DataGridModel dataGrid){
		Map<String, Object> pageMap = orderUrgentApplicationService.queryPageList(dto, dataGrid);
		return pageMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/apply")
	public Map<String, Object> applyReminder(OrderUrgentApplication model) throws Exception{
		Map<String, Object> rsMap = new HashMap<String, Object>();
		AgentUser user = SecurityHelper.getLoginUser();
		
		boolean success = false;
		String message = "";
		boolean isCreator = false;
		
		try {
			orderUrgentApplicationService.apply(model);
			success = true;
			
			String orderId = model.getOrderid();
			Order order = orderhistService.getOrderHistByOrderid(orderId);
			if(order.getCrusr().equals(user.getUserId())){
				isCreator = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单催送货提交失败", e);
			message = "订单催送货提交失败";
		}
		
		//产生消息
		try {
			String orderId = model.getOrderid();
			Order order = orderhistService.getOrderHistByOrderid(orderId);
			String createUser = order.getCrusr();
			String createGroup = userService.getUserGroup(createUser);

			SysMessage sysMessage = new SysMessage();
			sysMessage.setSourceTypeId(MessageType.URGE_ORDER.getIndex() + "");
			sysMessage.setContent(model.getOrderid());
			sysMessage.setCreateUser(user.getUserId());
			sysMessage.setCreateDate(new Date());
			sysMessage.setRecivierGroup(createGroup);
			sysMessage.setReceiverId(createUser);
			sysMessage.setDepartmentId(user.getDepartment());
			sysMessageService.addMessage(sysMessage);
		} catch (Exception e) {
            logger.error(e.getMessage(),e); //e.printStackTrace();
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		rsMap.put("isCreator", isCreator);
		return rsMap;
	}
	
	/**
	 * <p>查看订单催送货回复</p>
	 * @param orderId
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryReplay")
	@ResponseBody
	public Map<String, Object> queryReplay(String orderId){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		try {
			OrderUrgentApplication oua = orderUrgentApplicationService.queryLatest(orderId);
			rsMap.put("oua", oua);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "查询订单催送货回复失败";
			logger.error(message, e);
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		return rsMap;
	}
}
