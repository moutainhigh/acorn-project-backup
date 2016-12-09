/*
 * @(#)SmsTemplatesService.java 1.0 2013-5-15下午4:16:41
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-15 下午4:16:41
 * 
 */
public interface SmsTemplatesService {

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
	public List<SmsTemplate> getSmsTemplateList(String department, String theme);

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
			String theme, DataGridModel dataGridModel);

}
