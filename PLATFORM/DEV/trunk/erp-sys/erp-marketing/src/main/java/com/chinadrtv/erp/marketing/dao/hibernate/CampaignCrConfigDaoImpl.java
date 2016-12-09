/*
 * @(#)CampaignCrConfigDaoImpl.java 1.0 2013-9-9上午10:27:21
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.marketing.dao.CampaignCrConfigDao;
import com.chinadrtv.erp.model.marketing.CampaignCrConfig;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-9-9 上午10:27:21
 * 
 */
@Repository
public class CampaignCrConfigDaoImpl extends
		GenericDaoHibernate<CampaignCrConfig, java.lang.Long> implements
		CampaignCrConfigDao {

	CampaignCrConfigDaoImpl() {
		super(CampaignCrConfig.class);
	}

	/*
	 * (非 Javadoc) <p>Title: getByUserId</p> <p>Description: </p>
	 * 
	 * @param usedId
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.marketing.dao.CampaignCrConfigDao#getByUserId(java.
	 * lang.String)
	 */

	public CampaignCrConfig getByUserId(String usedId) {
		// TODO Auto-generated method stub
		String hql = "from CampaignCrConfig c where c.userId = '" + usedId
				+ "'";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		return find(hql, params);
	}

}
