/*
 * @(#)LeadTypeValueService.java 1.0 2013-6-4上午10:49:25
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.ScmSetDto;
import com.chinadrtv.erp.model.marketing.LeadTypeValue;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-4 上午10:49:25
 * 
 */
public interface LeadTypeValueService {
	/***
	 * 
	 * @Description: 插入实体
	 * @param leadTypeValue
	 * @return void
	 * @throws
	 */
	public void insertLeadTypeValue(LeadTypeValue leadTypeValue);
	
	/**
	 * 
	* @Description: 根据营销计划id查询leadtype参数值
	* @param campaignId
	* @return
	* @return Map<String,String>
	* @throws
	 */
	public Map<String, String> queryParamTypeValue(Long campaignId);
	
	/**
	 * 
	* @Description: 保存leadTypeValue
	* @param campaignId
	* @param user
	* @param scmSetDto
	* @return void
	* @throws
	 */
	public void saveLeadParamTypeValue(Long campaignId, String user,
			ScmSetDto scmSetDto);

}
