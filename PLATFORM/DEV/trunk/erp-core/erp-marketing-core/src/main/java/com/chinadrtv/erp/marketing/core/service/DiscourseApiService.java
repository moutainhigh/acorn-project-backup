/*
 * @(#)DiscourseApiService.java 1.0 2013-5-20上午10:41:31
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.Map;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.Discourse;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-20 上午10:41:31
 * 
 */
public interface DiscourseApiService {
	/***
	 * 
	 * @Description: 查找产品话术
	 * @param deparmetnId
	 *            部门id
	 * @param productCode
	 *            产品码
	 * @param DataGridModel
	 *            分页类
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> findDiscoursePageList(String deparmetnId,
			String productCode, DataGridModel dataModel);

	/**
	 * 
	 * @Description: 根据商品编码查询话术
	 * @param productCode
	 * @return
	 * @return Discourse
	 * @throws
	 */
	public Discourse getDiscourseByProductCode(String productCode)
			throws ServiceException;

}
