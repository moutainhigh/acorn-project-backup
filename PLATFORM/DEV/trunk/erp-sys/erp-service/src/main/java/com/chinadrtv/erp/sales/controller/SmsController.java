/*
 * @(#)SmsController.java 1.0 2013-7-2下午1:52:54
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.marketing.core.service.LeadInteractionOrderService;
import com.chinadrtv.erp.marketing.core.service.SmsApiService;
import com.chinadrtv.erp.model.Phone;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;
import com.chinadrtv.erp.sales.core.service.OrderhistService;
import com.chinadrtv.erp.sales.service.OrderExtendService;
import com.chinadrtv.erp.service.core.service.SmsSendService;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;
import com.chinadrtv.erp.uc.dto.PhoneAddressDto;
import com.chinadrtv.erp.uc.dto.PhoneDto;
import com.chinadrtv.erp.uc.service.PhoneService;
import com.chinadrtv.erp.user.aop.Mask;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.PojoUtils;

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
 * @since 2013-7-2 下午1:52:54 
 * 
 */
@Controller
@RequestMapping(value = "/sms")
public class SmsController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(SmsController.class);
	
//	@Autowired
//	private SmsService smsService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private SmsApiService smsApiService;
	@Autowired
	private PhoneService phoneService;
	@Autowired
	private LeadInteractionOrderService leadInteractionOrderService;
//	@Autowired
//	private LeadInterActionService leadInterActionService;
	@Autowired
	private OrderExtendService orderExtendService;
	@Autowired
	private OrderhistService orderService;
	
	/**
	 * <p>短信页面初始化</p>
	 * @param contactId
	 * @param leadId
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(String contactId, String leadId){
		ModelAndView mav = new ModelAndView("sms/sms");
		
		AgentUser user = SecurityHelper.getLoginUser();
		//主管权限
        boolean isManager = user.hasRole("COMPLAIN_MANAGER");
        boolean isKFWL = false;
        
        mav.addObject("isManager", isManager);
        mav.addObject("isKFWL", isKFWL);
		mav.addObject("contactId", contactId);
		mav.addObject("leadId", leadId);
		return mav;
	}
	
	/**
	 * <p>发送信息</p>
	 * @param smsSendDto
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/send")
	@ResponseBody
	public Map<String, Object> send(SmsSendDto smsSendDto, String orderId){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		try {
			smsSendService.sendSms(smsSendDto, orderId);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "发送短信提交失败"+e.getMessage();
			logger.error("发送短信提交失败", e);
		}
		rsMap.put("message", message);
		rsMap.put("success", success);
		
		return rsMap;
	}
	
	@RequestMapping(value = "/resend")
	@ResponseBody
	public Map<String, Object> resend(String uuid, String contactId){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		SmsSendDto smsSendDto = null;
		try {
			smsSendDto = smsApiService.getSmsByUuid(uuid);
			smsSendDto.setCustomerId(contactId);
		} catch (ServiceException e1) {
		}
		
		try {
			smsSendService.sendSms(smsSendDto, null);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = "发送短信提交失败"+e.getMessage();
			logger.error("发送短信提交失败", e);
		}
		rsMap.put("message", message);
		rsMap.put("success", success);
		
		return rsMap;
	}
	
	/**
	 * <p>查询短信模板列表</p>
	 * @param dataGridModel
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/queryTemplateList")
	@ResponseBody
	public Map<String, Object> queryTemplateList(DataGridModel dataGridModel){
		AgentUser user = SecurityHelper.getLoginUser();
		Map<String, Object> rsMap = new HashMap<String, Object>();
		try {
			rsMap = smsApiService.findSmsTemplatePageList(user.getDepartment() , "", dataGridModel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询短信模板列表失败", e);
		}
		return rsMap;
	}
	
	/**
	 * <p>查询客户手机列表</p>
	 * @param contactId
	 * @return List<PhoneDto>
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryCustomerMobile")
	@ResponseBody
	@Mask
	public List<PhoneDto> queryCustomerMobile(String contactId){
		List<Phone> mobileList = phoneService.queryMobilePhoneByContact(contactId);
		List<PhoneDto> phoneDtoList = PojoUtils.convertPojoList2DtoList(mobileList, PhoneDto.class);
		for(PhoneDto dto : phoneDtoList){
			if(null == dto.getPhoneNum() || "".equals(dto.getPhoneNum())) {
				PhoneAddressDto paDto = phoneService.splicePhone(dto.getPhn2());
				dto.setPhoneNum(paDto.getPhn2());
				dto.setPhoneNumMask(paDto.getPhn2());
			}else{
				dto.setPhoneNumMask(dto.getPhn2());
			}
		}
		return phoneDtoList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryOrders")
	public List<Map<String, String>> queryOrders(@RequestParam String contactId) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		List<String> ids = orderExtendService.listOrderIds(contactId);
		for (String id : ids) {
			Map<String, String> orderMap = new HashMap<String, String>();
			orderMap.put("orderId", id);
			result.add(orderMap);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryContactByOrderId")
	public String queryContactByOrderId(@RequestParam String orderId) {
		Order order = null;
		try {
			order = orderService.getOrderHistByOrderid(orderId);
		} catch (Exception e) {
		}
		if (order != null) {
			return order.getContactid();
		}
		return "";
	}
	
	/**
	 * <p>查询短信模板内容</p>
	 * @param smsTemplateId
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/querySmsContent")
	@ResponseBody
	public Map<String, Object> querySmsContent(Long smsTemplateId){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		try {
			SmsTemplate smsTemplate = smsApiService.getSmsTemplateById(smsTemplateId);
			rsMap.put("smsTemplate", smsTemplate);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			logger.error("查询短信模板失败", e);
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}
	
	/**
	 * <p>查询发送历史</p>
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/querySendHistory")
	@ResponseBody
	@Mask
	public Map<String, Object> querySendHistory(String contactId, DataGridModel dataGridModel){
		Map<String, Object> rsMap = smsSendService.queryHistory(contactId, dataGridModel);
		//leadInteractionOrderService.querySendHistory(contactId, dataGridModel);
		return rsMap;
	}
	
	/**
	 * <p>订单创建成功后 回写lead_interaction_order表</p>
	 * @param leadInteractionOrder
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/createOrderInteraction")
	@ResponseBody
	@Deprecated
	public Map<String, Object> createOrderInteraction(LeadInteractionOrder leadInteractionOrder){
		Map<String, Object> rsMap = new HashMap<String, Object>();
		boolean success = false;
		String message = "";
		
		try {
			leadInteractionOrderService.save(leadInteractionOrder);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			message = e.getMessage();
			logger.error("回写lead_interaction_order表失败", e);
		}
		
		rsMap.put("success", success);
		rsMap.put("message", message);
		
		return rsMap;
	}

}
