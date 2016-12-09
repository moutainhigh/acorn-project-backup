package com.chinadrtv.erp.sales.core.dao.hibernate;

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
import com.chinadrtv.erp.model.agent.Telephone;
import com.chinadrtv.erp.sales.core.dao.TelephoneDao;

/**
 * Created with (TC).
 * User: zhaizhanyi
 * Date: 13-5-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository(value="telephoneDao")
public class TelephoneDaoImpl extends GenericDaoHibernateBase<Telephone,String> implements TelephoneDao {
    public TelephoneDaoImpl()
    {
        super(Telephone.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

	/* (非 Javadoc)
	* <p>Title: query</p>
	* <p>Description: </p>
	* @param telephone
	* @param ip
	* @return
	* @see com.chinadrtv.erp.sales.core.dao.TelephoneDao#query(java.lang.String, java.lang.String)
	*/ 
	@Override
	public Telephone query(String ip) {
		String hql = "from Telephone where ipaddress=:ip";
		
		Map<String,Parameter> parameters = new HashMap<String,Parameter>();
		Parameter ipParam = new ParameterString("ip", ip);
		parameters.put("ip", ipParam);
		List<Telephone> list = this.findList(hql, parameters);
		
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
}
