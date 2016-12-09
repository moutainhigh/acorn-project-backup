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
import com.chinadrtv.erp.model.agent.Conpointuse;
import com.chinadrtv.erp.uc.dao.ConpointuseDao;

/**
 * 
 * 积分消费 DaoImpl 
 * @author haoleitao
 * @date 2013-5-6 下午2:01:23
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class ConpointuseDaoImpl extends GenericDaoHibernate<Conpointuse, String> implements ConpointuseDao{

	public ConpointuseDaoImpl() {
	    super(Conpointuse.class);
	}
	
	
	public Conpointuse getConpointuseById(String conpointuseId) {
        Session session = getSession();
        return (Conpointuse)session.get(Conpointuse.class, conpointuseId);
    }
	
	
	public List<Conpointuse> getAllConpointuse()
    {
        Session session = getSession();
        Query query = session.createQuery("from Conpointuse");
        return query.list();
    }
	    

    public List<Conpointuse> getAllConpointuseByContactId(String contactId,int index, int size){
    	StringBuffer sql = new StringBuffer("from Conpointuse where contactid=:contactid and pointvalue > 0");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactParams = new ParameterString("contactid", contactId);
		Parameter page = new ParameterInteger("page", index);
		page.setForPageQuery(true);
		Parameter rows = new ParameterInteger("rows", size);
		rows.setForPageQuery(true);
		params.put("contactParams", contactParams);
		params.put("page", page);
		params.put("rows", rows);
		sql.append(" order by crdt desc");
		List<Conpointuse> objList = findPageList(sql.toString(), params);
		return objList;
    }
    
    
    public int getAllConpointuseByContactIdCount(String contactId){        
    	StringBuffer sql = new StringBuffer("select count(id) from Conpointuse where contactid=:contactid ");
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		Parameter contactIdParams = new ParameterString("contactid", contactId);
		params.put("contactIdParams", contactIdParams);
		Integer count = findPageCount(sql.toString(), params);
		return count;
    }
    
    public Double getUseIntegralByContactId(String contactId){        
    	StringBuffer sql = new StringBuffer("select sum(pointvalue) from Conpointuse where contactid=:contactid ");
        Query q = getSession().createQuery(sql.toString());
        q.setString("contactid", contactId);
        List list = q.list();
        Double Integral = 0.00;
        if(list.size() >0){
        	Integral = Double.valueOf(list.get(0).toString());
        }
		
		return Integral;
    }
	
	
    public void saveOrUpdate(Conpointuse conpointuse){
        getSession().saveOrUpdate(conpointuse);
    }
	

}
