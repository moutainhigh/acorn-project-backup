package com.chinadrtv.erp.core.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.NamesDao;
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Names;
import com.chinadrtv.erp.model.NamesId;

/**
 * NamesDaoImpl
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:43:40
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class NamesDaoImpl extends GenericDaoHibernateBase<Names, java.lang.String> implements NamesDao{

	public NamesDaoImpl() {
	    super(Names.class);
	}
	
	
	public Names getNamesById(String namesId) {
        Session session = getSession();
        return (Names)session.get(Names.class, namesId);
    }
	
	
	    public List<Names> getAllNames()
    {
        Session session = getSession();
        Query query = session.createQuery("from Names");
        return query.list();
    }
    public List<Names> getAllNames(int index, int size)
    {
        Session session = getSession();
        Query query = session.createQuery("from Names");
        query.setFirstResult(index*size);
        query.setMaxResults(size);
        return query.list();
    }
	
	
    public void saveOrUpdate(Names names){
        getSession().saveOrUpdate(names);
    }


	public List<Names> getAllNames(String tid) {
		// TODO Auto-generated method stub
        Session session = getSession();
        Query query = session.createQuery("from Names a where a.id.tid = :tid and a.valid = '-1'");
        query.setString("tid", tid);
        
		return query.list();
	}
	
	public Names getNamesByID(NamesId namesId) {
		// TODO Auto-generated method stub
        Session session = getSession();
        
		return (Names)session.load(Names.class, namesId);
	}
	
	/** 
	 * <p>Title: getNamesById</p>
	 * <p>Description: </p>
	 * @param tid
	 * @param id
	 * @return Names
	 * @see com.chinadrtv.erp.core.dao.NamesDao#getNamesById(java.lang.String, java.lang.String)
	 */ 
	public Names getNamesById(String tid, String id) {
		String hql = "select n from Names n where n.id.tid=:tid and n.id.id=:id";
		Parameter<String> tidParam = new ParameterString("tid", tid);
		Parameter<String> idParam = new ParameterString("id", id);
		Names names = this.find(hql, tidParam, idParam);
		return names;
	}


	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	@Autowired
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

    public Names getNamesByTidAndId(String tid, String id)
    {
        String hql = "select n from Names n where n.id.tid=:tid and n.id.id=:id and n.valid = '-1' ";
        Parameter<String> tidParam = new ParameterString("tid", tid);
        Parameter<String> idParam = new ParameterString("id", id);
        return this.find(hql, tidParam, idParam);
    }

    public void addNames(Names names)
    {
        this.hibernateTemplate.save(names);
    }

    public List<Names> getAllNames(String tid, String tdsc) {
        Session session = getSession();
        Query query = session.createQuery("from Names a where a.id.tid = :tid and a.tdsc=:tdsc and a.valid='-1'");
        query.setString("tid", tid);
        query.setString("tdsc", tdsc);
        return query.list();
    }
}
