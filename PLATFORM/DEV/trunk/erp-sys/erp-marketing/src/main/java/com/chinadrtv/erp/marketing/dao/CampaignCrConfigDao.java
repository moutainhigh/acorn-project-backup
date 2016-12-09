/*
 * @(#)CampaignCrConfigDao.java 1.0 2013-9-9上午10:26:13
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CampaignCrConfig;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-9-9 上午10:26:13
 * 
 */
public interface CampaignCrConfigDao extends
		GenericDao<CampaignCrConfig, java.lang.Long> {

	public CampaignCrConfig getByUserId(String usedId);

}
