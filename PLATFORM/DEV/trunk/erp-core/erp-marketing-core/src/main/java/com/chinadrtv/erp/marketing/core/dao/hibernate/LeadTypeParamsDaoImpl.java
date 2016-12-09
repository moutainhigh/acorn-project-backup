/*
 * @(#)LeadTypeParamsDaoImpl.java 1.0 2013-6-4上午10:07:41
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.marketing.core.dao.LeadTypeParamsDao;
import com.chinadrtv.erp.model.marketing.LeadTypeParams;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-4 上午10:07:41
 * 
 */
@Repository
public class LeadTypeParamsDaoImpl extends
		GenericDaoHibernate<LeadTypeParams, java.lang.Long> implements
		LeadTypeParamsDao {

	public LeadTypeParamsDaoImpl() {
		super(LeadTypeParams.class);
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getParamsList
	 * </p>
	 * <p>
	 * Description: 根据leadtypeid 查询LeadTypeParams
	 * </p>
	 * 
	 * @param leadTypeId
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.marketing.core.dao.LeadTypeParamsDao#getParamsList(java.lang.Long)
	 */
	public List<LeadTypeParams> getParamsList(Long leadTypeId) {
		// TODO Auto-generated method stub
		String hql = "from LeadTypeParams where leadTypeId = ? ";
		List<LeadTypeParams> list = getHibernateTemplate().find(hql,
				new Object[] { leadTypeId });
		return list;
	}
}
