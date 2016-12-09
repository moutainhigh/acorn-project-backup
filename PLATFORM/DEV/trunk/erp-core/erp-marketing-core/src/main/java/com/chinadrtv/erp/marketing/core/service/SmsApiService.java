/*
 * @(#)SmsApiService.java 1.0 2013-5-15下午4:05:44
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-15 下午4:05:44
 * 
 */
public interface SmsApiService {
	/***
	 * 根据部门 和主题查模板
	 * 
	 * @Description: TODO
	 * @param department
	 * @param theme
	 * @return
	 * @return List<SmsTemplate>
	 * @throws
	 */
	public List<SmsTemplate> getSmsTemplateList(String department, String theme)
			throws ServiceException;

	/***
	 * 根据部门 和主题查模板 easyui 分页
	 * 
	 * @Description: TODO
	 * @param department
	 * @param theme
	 * @return
	 * @return List<SmsTemplate>
	 * @throws
	 */
	public Map<String, Object> findSmsTemplatePageList(String department,
			String theme, DataGridModel dataGridModel) throws ServiceException;

	/****
	 * 单条短信发送
	 * 
	 * @Description: TODO
	 * @param smsSendDto
	 * @return
	 * @return String
	 * @throws
	 */
	public String singleSmsSend(SmsSendDto smsSendDto) throws ServiceException;

	/**
	 * 根据uuid 查询短信
	 * 
	 * @Description: TODO
	 * @param uuid
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public SmsSendDto getSmsByUuid(String uuid) throws ServiceException;

	/**
	 * 
	 * @Description: 根据订单类型发短信
	 * @param smsSendDto
	 * @return
	 * @return String
	 * @throws
	 */
	public String SmsSendByOrderType(SmsSendDto smsSendDto)
			throws ServiceException;

	/**
	 * @throws ServiceException
	 * 
	 * @Description: 生成验证码
	 * @return
	 * @return String
	 * @throws
	 */
	public String getCheckCode(String orderId) throws ServiceException;

	/**
	 * 
	 * @Description: 根据模板id查询
	 * @param id
	 * @return
	 * @throws ServiceException
	 * @return SmsTemplate
	 * @throws
	 */
	public SmsTemplate getSmsTemplateById(Long id) throws ServiceException;

	/***
	 * 根据contactId easyui 分页
	 * 
	 * @Description: TODO
	 * @param contactId
	 * @return
	 * @return List<SmsTemplate>
	 * @throws
	 */
	public Map<String, Object> findSmsSendDetailPageList(String contactId,
			DataGridModel dataGridModel) throws ServiceException;

	/**
	 * 根据 easyui 分页
	 * 
	 * @Description: TODO
	 * @param mobile
	 * @param dataGridModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> findSmsAnswerPageList(String mobile,
			DataGridModel dataGridModel) throws ServiceException;

}
