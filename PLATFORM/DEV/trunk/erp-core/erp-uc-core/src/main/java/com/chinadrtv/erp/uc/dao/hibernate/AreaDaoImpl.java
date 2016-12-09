/*
 * @(#)AreaDaoImpl.java 1.0 2013-5-3下午3:43:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.uc.dao.AreaDao;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-5-3 下午3:43:20 
 * 
 */
@Repository
public class AreaDaoImpl extends GenericDaoHibernate<AreaAll, Integer> implements AreaDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public AreaDaoImpl() {
		super(AreaAll.class);
	}


	public List<AreaAll> getAreaByCountryId(Integer countryId) {
		Session session = getSession();
		Query query = session.createQuery("from AreaAll a where a. countyid = '" + countryId + "'");
		return query.list();
	}
}
