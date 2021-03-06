package com.chinadrtv.erp.uc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.Conpointcr;
import com.chinadrtv.erp.uc.dao.ConpointcrDao;

/**
 * 生成积分DaoImpl
 *  
 * @author haoleitao
 * @date 2013-5-6 下午2:03:04
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class ConpointcrDaoImpl extends GenericDaoHibernate<Conpointcr, String> implements ConpointcrDao{

	public ConpointcrDaoImpl() {
	    super(Conpointcr.class);
	}
	
	
	public Conpointcr getConpointcrById(String conpointcrId) {
        Session session = getSession();
        return (Conpointcr)session.get(Conpointcr.class, conpointcrId);
    }
	
	
	 public List<Conpointcr> getAllConpointcr()
    {
        Session session = getSession();
        Query query = session.createQuery("from Conpointcr");
        return query.list();
    }
	 
	 
    public List<Conpointcr> getAllConpointcrByContactId(String contactId,int index, int size){
    	StringBuffer sql = new StringBuffer("from Conpointcr where contactid=:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		Parameter page = new ParameterInteger("page", index);
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", size);
		rows.setForPageQuery(true);
		params.put("contactIdParams", contactIdParams);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by startdt desc");
		List<Conpointcr> objList = findPageList(sql.toString(), params);
		return objList;
    }
	
    
    
    public int getAllConpointcrByContactIdCount(String contactId){        
    	StringBuffer sql = new StringBuffer("select count(corderid) from Conpointcr where contactid=:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		params.put("contactIdParams", contactIdParams);
		Integer count = findPageCount(sql.toString(), params);
		return count;
    }
	
    public void saveOrUpdate(Conpointcr conpointcr){
        getSession().saveOrUpdate(conpointcr);
    }
	

}
