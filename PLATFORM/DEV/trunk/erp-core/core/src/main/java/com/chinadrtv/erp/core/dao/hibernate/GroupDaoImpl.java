/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.GroupDao;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.Grp;

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
public class GroupDaoImpl extends GenericDaoHibernate<Grp, java.lang.String> implements GroupDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public GroupDaoImpl() {
		super(Grp.class);
		// TODO Auto-generated constructor stub
	}

	/* (非 Javadoc)
	* <p>Title: queryNamesList</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.marketing.dao.NamesDao#queryNamesList()
	*/ 
	public List<Grp> queryList(String department) {
		String hql = "select t from Grp t where t.dept=:department";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter dept = new ParameterString("department",department);
		params.put("department", dept);
		List<Grp> list = this.findList(hql, params);
		return list;
	}


}
