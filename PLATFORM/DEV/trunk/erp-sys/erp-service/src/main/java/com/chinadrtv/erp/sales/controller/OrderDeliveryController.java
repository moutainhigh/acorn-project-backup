/*
 * @(#)OrderDeliveryController.java 1.0 2013-6-13下午1:53:07
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.marketing.core.service.UserBpmTaskService;
import com.chinadrtv.erp.model.SysMessage;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.marketing.UserBpmTask;
import com.chinadrtv.erp.sales.core.constant.MessageType;
import com.chinadrtv.erp.sales.core.service.OrderDeliveryService;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.core.service.SysMessageService;
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
 * @since 2013-6-13 下午1:53:07 
 * 
 */
@Controller
@RequestMapping("/delivery")
public class OrderDeliveryController extends BaseController {
	
	private static transient final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderDeliveryController.class);
	
	@Autowired
	private OrderDeliveryService orderDeliveryService;
	@Autowired
	private UserBpmTaskService userBpmTaskService;
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    private SysMessageService sysMessageService;
    @Autowired
    private OrderhistService orderhistService;

	/**
	 * <p>匹配承运商、仓库、配送时效</p>
	 * @param orderId
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/matchDelivery")
	@ResponseBody
	public Map<String, Object> matchDelivery(String orderId){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		boolean isAuditing = false;

		try {
			rsMap = orderDeliveryService.matchDelivery(orderId);
			success = true;
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("匹配承运商、仓库、配送时效 失败：", e);
			message = "订单匹配承运商失败，将进行手工挑单";
		}
		
		//判断订单中否在审批流程中
		List<UserBpmTask> tasks = userBpmTaskService.queryUnterminatedOrderChangeTask(orderId);
		if(null!=tasks && tasks.size()>0){
			isAuditing = true;
		}
		
		rsMap.put("isAuditing", isAuditing);
		rsMap.put("success", success);
		rsMap.put("message", message);
		return rsMap;
	}


	/**
	 * <p>指定EMS送货</p>
	 * @param orderid
	 * @param remark
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "assignEms")
	@ResponseBody
	public Map<String, Object> assignEms(String orderId, String remark){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		
		boolean success = false;
		String message = "";
		try {
			int rs = orderDeliveryService.assignEms(orderId, remark);
			if(rs==0){
				success = true;	
			}else if(rs==-1){
				message = "指定EMS失败,订单["+orderId+"]审批流程未结束！";
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			message = "指定EMS失败";
			logger.error(message, e);
		}
        if(success==true)
        {
            try{
            SysMessage sysMessage=new SysMessage();
            sysMessage.setContent("订单号:" + orderId);
            sysMessage.setCreateDate(new Date());

            Order order=orderhistService.getOrderHistByOrderid(orderId);
            sysMessage.setRecivierGroup(userService.getUserGroup(order.getCrusr()));
            sysMessage.setDepartmentId(userService.getDepartment(order.getCrusr()));
            sysMessage.setSourceTypeId(String.valueOf(MessageType.MODIFY_ORDER.getIndex()));
            sysMessage.setCreateUser(SecurityHelper.getLoginUser().getUserId());
            sysMessage.setContent(orderId);
            sysMessageService.addMessage(sysMessage);
            }catch (Exception exp)
            {
                //TODO:异常处理
                logger.error("save message error:",exp);
            }
        }
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		return rsMap;
	}
}
