/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.marketing.core.dao.ObContactDao;
import com.chinadrtv.erp.model.agent.ObContact;

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
public class ObContactDaoImpl extends GenericDaoHibernate<ObContact, java.lang.String> implements ObContactDao {

	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate jdbcTemplateAgent;
	
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public ObContactDaoImpl() {
		super(ObContact.class);
	}

	/* (非 Javadoc)
	* <p>Title: query</p>
	* <p>Description: </p>
	* @param jobId
	* @param limit
	* @return
	* @see com.chinadrtv.erp.marketing.core.dao.ObContactDao#query(java.lang.String, int)
	*/ 
	@Override
	public List<ObContact> query(String jobId, int limit) {
		StringBuffer hsql = new StringBuffer("from ObContact t where t.enddate>SYSDATE and  t.startdate<SYSDATE and pd_jobid=:jobId and status='3'");

		Map<String, Parameter> params = new HashMap<String, Parameter>();

		Parameter jobIdPara = new ParameterString("jobId", jobId);
		
		Parameter page = new ParameterInteger("page", 0);
		page.setForPageQuery(true);

		Parameter rows = new ParameterInteger("rows", limit);
		rows.setForPageQuery(true);

		params.put("page", page);
		params.put("rows", rows);
		params.put("jobId", jobIdPara);
//		hsql.append(" order by priority");
		List<ObContact> obcontactList = findPageList(hsql.toString(), params);
		return obcontactList;
	}
	
	@Override
	public void updateStatus(String jobId, int limit) {
		
		StringBuilder sql = new StringBuilder("");
		
		sql.append("update iagent.Ob_Contact t " +
				"set t.status='3' " +
				"where t.enddate>SYSDATE " +
				"and  t.startdate<SYSDATE " +
				"and pd_jobid=? " +
				"and (status='0' or status is null)  " +
				"and campaign_id>0 " +
				"and rownum<=?");
		
		Object[] params = null;
		params = new Object[] {jobId,limit};
		
		jdbcTemplateAgent.update(sql.toString(), params);
	}



}
