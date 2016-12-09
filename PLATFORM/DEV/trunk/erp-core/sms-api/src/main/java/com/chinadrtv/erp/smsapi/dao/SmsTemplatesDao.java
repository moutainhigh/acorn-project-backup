/*
 * @(#)SmsTemplatesDao.java 1.0 2013-5-15下午4:27:43
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-15 下午4:27:43
 * 
 */
public interface SmsTemplatesDao extends
		GenericDao<SmsTemplate, java.lang.Long> {
	/**
	 * 根据部门号,主题查询
	 * 
	 * @Description: TODO
	 * @param departMent
	 * @return
	 * @return List<SmsTemplate>
	 * @throws
	 */
	public List<SmsTemplate> getByDepartment(String department, String theme);

	/**
	 * 分页查询
	 * 
	 * @param smsTemplateDto
	 * @param dataGridModel
	 * @return
	 */
	List<SmsTemplate> query(String department, String theme,
			DataGridModel dataGridModel);

	/**
	 * 分页查询记录数
	 * 
	 * @param smsTemplateDto
	 * @return
	 */
	int queryCount(String department, String theme);

	/**
	 * 根据templateId 查询 模板
	 * 
	 * @Description: TODO
	 * @param templateId
	 * @return
	 * @return SmsTemplate
	 * @throws
	 */
	public SmsTemplate queryByTemplateId(String templateId);

}
