/*
 * @(#)AddressDaoImpl.java 1.0 2013-2-19下午4:02:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.tc.core.constant.SchemaNames;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.Address;
import com.chinadrtv.erp.tc.core.dao.AddressDao;

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
 * @since 2013-2-19 下午4:02:28 
 * 
 */
@Repository("com.chinadrtv.erp.tc.core.dao.hibernate.AddressDaoImpl")
public class AddressDaoImpl extends GenericDaoHibernateBase<Address, String>
		implements AddressDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public AddressDaoImpl() {
		super(Address.class);
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

    @Autowired
    private SchemaNames schemaNames;

    public String getNewContactId()
    {
        Query q = getSession().createSQLQuery("select "+ schemaNames.getAgentSchema()+"seqcontact_notdefault.nextval from dual");
        return  q.list().get(0).toString();
    }

}
