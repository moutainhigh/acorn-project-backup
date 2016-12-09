package com.chinadrtv.erp.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.EmsDao;
import com.chinadrtv.erp.model.Ems;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EmsDaoImpl
 *  
 * @author haoleitao
 * @date 2013-3-4 下午2:43:14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */

@Repository
public class EmsDaoImpl extends GenericDaoHibernateBase<Ems, java.lang.Integer> implements EmsDao{

	public EmsDaoImpl() {
	    super(Ems.class);
	}
	
	
	public Ems getEmsById(String emsId) {
        Session session = getSession();
        return (Ems)session.get(Ems.class, emsId);
    }
	
	
	    public List<Ems> getAllEms()
    {
        Session session = getSession();
        Query query = session.createQuery("from Ems");
        return query.list();
    }
    public List<Ems> getAllEms(int index, int size)
    {
        Session session = getSession();
        Query query = session.createQuery("from Ems");
        query.setFirstResult(index*size);
        query.setMaxResults(size);
        return query.list();
    }
	
    public String getCityNameById(String cityId){
    	  Session session = getSession();
          Query query = session.createQuery("select e.name from Ems e where e.cityid = :cityId ");
          return (String)query.list().get(0);         
    }


    public Ems getEmsBySpellid(Integer spellid) {
        Query hqlQuery = getSession().createQuery("from Ems s where s.spellid =:spellid");
        hqlQuery.setParameter("spellid", spellid);
        return (Ems) hqlQuery.uniqueResult();

    }

    public void saveOrUpdate(Ems ems){
        getSession().saveOrUpdate(ems);
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
	

}
