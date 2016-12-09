/*
 * @(#)MemberLevelDaoImpl.java 1.0 2013-5-23下午1:39:26
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
import com.chinadrtv.erp.model.agent.MemberLevel;
import com.chinadrtv.erp.sales.dao.MemberLevelDao;

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
 * @since 2013-5-23 下午1:39:26 
 * 
 */
@Repository
public class MemberLevelDaoImpl extends GenericDaoHibernate<MemberLevel, String> implements MemberLevelDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public MemberLevelDaoImpl() {
		super(MemberLevel.class);
	}

	/** 
	 * <p>Title: queryAll</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.chinadrtv.erp.sales.dao.MemberLevelDao#queryAll()
	 */ 
	public List<MemberLevel> queryAll() {
		String hql = "select ml from MemberLevel ml order by ml.memberlevelid";
		return this.findList(hql, new HashMap());
	}

}
