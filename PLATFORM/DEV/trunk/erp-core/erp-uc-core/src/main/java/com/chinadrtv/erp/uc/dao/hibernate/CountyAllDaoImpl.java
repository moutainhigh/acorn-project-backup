/*
 * @(#)CountyAllDaoImpl.java 1.0 2013-6-7上午10:30:37
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.uc.dao.CountyAllDao;

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
 * @since 2013-6-7 上午10:30:37 
 * 
 */
@Repository
public class CountyAllDaoImpl extends GenericDaoHibernate<CountyAll, Integer> implements CountyAllDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public CountyAllDaoImpl() {
		super(CountyAll.class);
	}

	/** 
	 * <p>Title: queryAreaList</p>
	 * <p>Description: </p>
	 * @param countyCode
	 * @param countyName
	 * @param areaCode
	 * @return List<CountyAll>
	 * @see com.chinadrtv.erp.uc.dao.CountyAllDao#queryAreaList(java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@SuppressWarnings("rawtypes")
	@Override
	public List<CountyAll> queryAreaList(String countyCode, String countyName, String areaCode) {
		String hql = "select ca from CountyAll ca where 1=1 ";
		StringBuffer sb = new StringBuffer();
		sb.append(hql);
		
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		if(null!=countyCode && !"".equals(countyCode)){
			sb.append(" and ca.code=:countyCode ");
			params.put("countyCode", new ParameterString("countyCode", countyCode));
		}
		if(null!=countyName && !"".equals(countyName)){
			sb.append(" and ca.countyname like '%"+countyName+"%'");
			//params.put("countyName", new ParameterString("countyName", countyName));
		}
		if(null!=areaCode && !"".equals(areaCode)){
			sb.append(" and ca.areacode=:areaCode ");
			params.put("areaCode", new ParameterString("areaCode", areaCode));
		}
		
		List<CountyAll> countyList = this.findList(sb.toString(), params);
		
		return countyList;
	}

	
}
