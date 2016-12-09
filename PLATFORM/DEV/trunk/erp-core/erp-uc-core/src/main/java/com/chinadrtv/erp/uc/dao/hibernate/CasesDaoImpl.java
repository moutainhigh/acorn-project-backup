package com.chinadrtv.erp.uc.dao.hibernate;

import com.chinadrtv.erp.model.ShoppingCart;
import com.chinadrtv.erp.uc.dao.*;
import com.chinadrtv.erp.model.agent.*;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;

import org.springframework.stereotype.Repository;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API-UC-32.	查询服务历史
 *  
 * @author haoleitao
 * @date 2013-5-13 下午3:54:18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class CasesDaoImpl extends GenericDaoHibernate<Cases, java.lang.String> implements CasesDao{

	public CasesDaoImpl() {
	    super(Cases.class);
	}
	
	
	public Cases getCasesById(String casesId) {
        Session session = getSession();
        return (Cases)session.get(Cases.class, casesId);
    }
	
	
	    public List<Cases> getAllCases()
    {
        Session session = getSession();
        Query query = session.createQuery("from Cases");
        return query.list();
    }
    public List<Cases> getAllCases(int index, int size)
    {
        Session session = getSession();
        Query query = session.createQuery("from Cases");
        query.setFirstResult(index);
        query.setMaxResults(size);
        return query.list();
    }
	
	
    public void saveOrUpdate(Cases cases){
        getSession().saveOrUpdate(cases);
    }


	public List<Cases> getAllCasesByContactId(String contactId, int index,
			int size) {
		StringBuffer sql = new StringBuffer("from Cases where contactid=:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		Parameter page = new ParameterInteger("page", index);
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", size);
		rows.setForPageQuery(true);
		params.put("contactIdParams", contactIdParams);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by crdt desc");
		List<Cases> objList = findPageList(sql.toString(), params);
		return objList;
	}


	public int getAllCasesByContactIdCount(String contactId) {
    	StringBuffer sql = new StringBuffer("select count(caseid) from Cases where contactid=:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		params.put("contactIdParams", contactIdParams);
		Integer count = findPageCount(sql.toString(), params);
		return count;
	}


    public Cmpfback getCmpfbackByCaseId(String caseid) {
        Query hqlQuery = getSession().createQuery("from Cmpfback c where c.caseid =:caseid");
        hqlQuery.setParameter("caseid", caseid);
        return (Cmpfback) hqlQuery.uniqueResult();
    }

}
