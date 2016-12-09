package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.marketing.LeadInteraction;
import com.chinadrtv.erp.model.agent.Cases;
import com.chinadrtv.erp.model.agent.Conpointcr;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.uc.dao.LeadDao;
/**
 * API-UC-30.	查询用户交互历史
 *  
 * @author haoleitao
 * @date 2013-5-14 下午1:46:06
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository("ucLeadDao")
public class LeadDaoImpl extends GenericDaoHibernate<Lead, Long> implements LeadDao {

	public LeadDaoImpl() {
		super(Lead.class);
	}

	public List<Lead> getAllLeadByContactId(String contactId,int index, int size) {
    	StringBuffer sql = new StringBuffer("from Lead a where a.customerId =:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		Parameter page = new ParameterInteger("page", index);
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", size);
		rows.setForPageQuery(true);
		params.put("contactIdParams", contactIdParams);
		params.put("page", page);
		params.put("rows", rows);
		//sql.append(" order by addDate desc");
		List<Lead> objList = findPageList(sql.toString(), params);
		return objList;
	}

	public int getAllLeadByContactIdCount(String contactId) {
		StringBuffer sql = new StringBuffer("select count(id) from Lead a  where  a.customerId=:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		params.put("contactIdParams", contactIdParams);
		Integer count = findPageCount(sql.toString(), params);
		return count;
	}

}
