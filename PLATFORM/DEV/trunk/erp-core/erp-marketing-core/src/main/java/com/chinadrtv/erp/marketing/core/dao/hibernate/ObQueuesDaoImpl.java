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
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.ObQueuesDao;
import com.chinadrtv.erp.model.agent.ObQueues;

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
public class ObQueuesDaoImpl extends GenericDaoHibernate<ObQueues, java.lang.String> implements ObQueuesDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public ObQueuesDaoImpl() {
		super(ObQueues.class);
		// TODO Auto-generated constructor stub
	}

	/**
	* <p>Title: queryNamesList</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.marketing.dao.NamesDao#queryNamesList()
	*/ 
	public List<ObQueues> queryList(String jobId) {
		String hql = "select t from ObQueues t where t.jobId=:jobId and t.type='public'";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter jobIdPara = new ParameterString("jobId",jobId);
		params.put("jobId", jobIdPara);
		List<ObQueues> list = this.findList(hql, params);
		return list;
	}


}
