/*
 * @(#)NamesDaoImpl.java 1.0 2013-1-22下午2:24:24
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.tc.core.dao.NamesDao;

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
 * @since 2013-1-22 下午2:24:24 
 * 
 */
@Repository("com.chinadrtv.erp.tc.core.dao.hibernate.NamesDaoImpl")
public class NamesDaoImpl extends GenericDaoHibernateBase<Names, java.lang.String> implements NamesDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public NamesDaoImpl() {
		super(Names.class);
		// TODO Auto-generated constructor stub
	}

	/* (非 Javadoc)
	* <p>Title: setSessionFactory</p>
	* <p>Description: </p>
	* @param sessionFactory
	* @see com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase#setSessionFactory(org.hibernate.SessionFactory)
	*/ 
	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
	
	/* (非 Javadoc)
	* <p>Title: queryNamesList</p>
	* <p>Description: </p>
	* @return
	* @see com.chinadrtv.erp.marketing.dao.NamesDao#queryNamesList()
	*/ 
	public List<Names> queryNamesList() {
		String hql = "select t from Names t where t.tid='GRPDEPT' order by t.id";
		Map<String, Parameter> params = new HashMap<String, Parameter>();
		List<Names> namesList = this.findList(hql, params);
		return namesList;
	}

	/* (非 Javadoc)
	* <p>Title: getValueByTidAndId</p>
	* <p>Description: </p>
	* @param tid
	* @param id
	* @return
	* @see com.chinadrtv.erp.tc.dao.NamesDao#getValueByTidAndId(java.lang.String, java.lang.String)
	*/ 
	public String getValueByTidAndId(String tid, String id) {
		String hql = "select n from Names n where n.id.tid=:tid and n.id.id=:id";
		Parameter<String> tidParam = new ParameterString("tid", tid);
		Parameter<String> idParam = new ParameterString("id", id);
		Names names = this.find(hql, tidParam, idParam);
		return names.getDsc();
	}

    public Names getNamesByTidAndId(String tid, String id)
    {
        String hql = "select n from Names n where n.id.tid=:tid and n.id.id=:id";
        Parameter<String> tidParam = new ParameterString("tid", tid);
        Parameter<String> idParam = new ParameterString("id", id);
        return this.find(hql, tidParam, idParam);
    }

    public void addNames(Names names)
    {
        this.hibernateTemplate.save(names);
    }

}
