/*
 * @(#)LeadTypeValueDao.java 1.0 2013-6-4上午10:25:36
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.LeadTypeValue;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-4 上午10:25:36
 * 
 */
public interface LeadTypeValueDao extends
		GenericDao<LeadTypeValue, java.lang.Long> {

	/**
	 * 
	 * @Description: 查询某campaign的所有列表
	 * @param campaignId
	 * @return
	 * @return List<CampaignTypeValue>
	 * @throws
	 */
	public List<LeadTypeValue> queryList(Long campaignId);

	/**
	 * 
	 * @Description: 根据campaignid 删除
	 * @param campaignId
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer removeAll(Long campaignId);

}
