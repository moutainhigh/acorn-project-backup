/*
 * @(#)SmsTemplateServiceImpl.java 1.0 2013-5-15下午4:15:38
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.SmsTemplatesDao;
import com.chinadrtv.erp.smsapi.model.SmsTemplate;
import com.chinadrtv.erp.smsapi.service.SmsTemplatesService;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-15 下午4:15:38
 * 
 */
@Service("smsTemplatesService")
public class SmsTemplatesServiceImpl implements SmsTemplatesService {

	@Autowired
	private SmsTemplatesDao smsTemplatesDao;

	/**
	 * 
	 * 根据部门 和主题查模板
	 * 
	 * @param department
	 * 
	 * @param theme
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.SmsTemplatesService#getSmsTemplateList
	 *      (java.lang.String, java.lang.String)
	 */
	public List<SmsTemplate> getSmsTemplateList(String department, String theme) {
		// TODO Auto-generated method stub
		return smsTemplatesDao.getByDepartment(department, theme);
	}

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
			String theme, DataGridModel dataGridModel) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<SmsTemplate> list = smsTemplatesDao.query(department, theme,
				dataGridModel);
		int count = smsTemplatesDao.queryCount(department, theme);

		result.put("total", count);
		result.put("rows", list);
		return result;
	}

}
