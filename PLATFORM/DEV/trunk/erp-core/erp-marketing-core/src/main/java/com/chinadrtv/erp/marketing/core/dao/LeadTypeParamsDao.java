/*
 * @(#)LeadTypeParamsDao.java 1.0 2013-6-4上午10:05:02
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.LeadTypeParams;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-4 上午10:05:02
 * 
 */
public interface LeadTypeParamsDao extends
		GenericDao<LeadTypeParams, java.lang.Long> {
	/**
	 * 
	 * @Description: 根据leadtypeid 查询LeadTypeParams
	 * @param leadTypeId
	 * @return
	 * @return List<LeadTypeParams>
	 * @throws
	 */
	public List<LeadTypeParams> getParamsList(Long leadTypeId);

}
