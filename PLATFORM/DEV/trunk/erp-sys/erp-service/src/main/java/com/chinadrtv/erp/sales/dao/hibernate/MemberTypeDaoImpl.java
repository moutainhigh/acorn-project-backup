/*
 * @(#)MemberTypeDaoImpl.java 1.0 2013-5-23下午1:37:04
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.dao.hibernate;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.model.agent.MemberType;
import com.chinadrtv.erp.sales.dao.MemberTypeDao;

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
 * @since 2013-5-23 下午1:37:04 
 * 
 */
@Repository
public class MemberTypeDaoImpl extends GenericDaoHibernate<MemberType, String> implements MemberTypeDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public MemberTypeDaoImpl() {
		super(MemberType.class);
	}

	/** 
	 * <p>Title: queryAll</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.chinadrtv.erp.sales.dao.MemberTypeDao#queryAll()
	 */ 
	@SuppressWarnings("rawtypes")
	public List<MemberType> queryAll() {
		String hql = "from MemberType";
		return this.findList(hql, new HashMap<String, Parameter>());
	}

}
