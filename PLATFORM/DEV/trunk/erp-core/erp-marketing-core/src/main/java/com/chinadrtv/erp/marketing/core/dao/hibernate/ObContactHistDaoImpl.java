/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.ObContactHistDao;
import com.chinadrtv.erp.model.agent.ObContactHist;

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
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:24:24 
 * 
 */
@Repository
public class ObContactHistDaoImpl extends GenericDaoHibernate<ObContactHist, java.lang.String> implements ObContactHistDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public ObContactHistDaoImpl() {
		super(ObContactHist.class);
	}

	/* (非 Javadoc)
	* <p>Title: find</p>
	* <p>Description: </p>
	* @param pdCustomerId
	* @return
	* @see com.chinadrtv.erp.marketing.core.dao.ObContactHistDao#find(java.lang.String)
	*/ 
	@Override
	public ObContactHist findById(String pdCustomerId) {
		String sql = "FROM ObContactHist WHERE pd_customerid=:pdCustomerId";
		
		Parameter parameter = new ParameterString("pdCustomerId", pdCustomerId);
		return this.find(sql,parameter);
	}

	

}
