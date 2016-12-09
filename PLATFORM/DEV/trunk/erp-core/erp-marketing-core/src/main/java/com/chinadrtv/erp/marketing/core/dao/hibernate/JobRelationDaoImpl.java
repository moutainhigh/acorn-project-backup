/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.marketing.core.dao.JobRelationDao;
import com.chinadrtv.erp.model.agent.JobsRelation;

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
public class JobRelationDaoImpl extends GenericDaoHibernate<JobsRelation, java.lang.String> implements JobRelationDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public JobRelationDaoImpl() {
		super(JobsRelation.class);
		// TODO Auto-generated constructor stub
	}

	/* (非 Javadoc)
	* <p>Title: queryNamesList</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.marketing.dao.NamesDao#queryNamesList()
	*/ 
	public List<JobsRelation> queryList() {
		String hql = "select t from JobsRelation t";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		List<JobsRelation> list = this.findList(hql, params);
		return list;
	}


}
