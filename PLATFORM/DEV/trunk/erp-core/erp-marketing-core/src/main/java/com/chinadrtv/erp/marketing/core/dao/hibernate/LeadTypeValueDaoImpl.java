/*
 * @(#)LeadTypeValueDaoImpl.java 1.0 2013-6-4上午10:29:51
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.marketing.core.dao.LeadTypeValueDao;
import com.chinadrtv.erp.model.marketing.LeadTypeValue;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-4 上午10:29:51
 * 
 */
@Repository
public class LeadTypeValueDaoImpl extends
		GenericDaoHibernate<LeadTypeValue, java.lang.Long> implements
		LeadTypeValueDao {

	public LeadTypeValueDaoImpl() {
		super(LeadTypeValue.class);
	}

	/**
	 * 
	 * 
	 * @param campaignId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.LeadTypeValueDao#queryList(java.
	 *      lang.Long)
	 */
	public List<LeadTypeValue> queryList(Long campaignId) {
		// TODO Auto-generated method stub
		String hql = "from  LeadTypeValue  where campaignId =? ";
		return getHibernateTemplate().find(hql, new Object[] { campaignId });
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: removeAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param campaignId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.LeadTypeValueDao#removeAll(java.
	 *      lang.Long)
	 */
	public Integer removeAll(Long campaignId) {
		// TODO Auto-generated method stub
		String hql = "DELETE FROM LeadTypeValue WHERE campaignId=:campaignId";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter type = new ParameterLong("campaignId", campaignId);
		params.put("campaignId", type);
		Integer result = this.execUpdate(hql, params);
		return result;
	}

}
