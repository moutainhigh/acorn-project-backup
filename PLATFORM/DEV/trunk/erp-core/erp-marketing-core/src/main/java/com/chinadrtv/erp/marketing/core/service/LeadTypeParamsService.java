/*
 * @(#)LeadTypeParamsService.java 1.0 2013-6-4上午10:14:37
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;

import javax.xml.rpc.ServiceException;

import com.chinadrtv.erp.model.marketing.LeadTypeParams;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-4 上午10:14:37
 * 
 */
public interface LeadTypeParamsService {
	/**
	 * 
	 * @Description: 根据leadtypeid 查询LeadTypeParams
	 * @param leadTypeId
	 * @return
	 * @return List<LeadTypeParams>
	 * @throws
	 */
	public List<LeadTypeParams> getParamsList(Long leadTypeId)
			throws ServiceException;
}
