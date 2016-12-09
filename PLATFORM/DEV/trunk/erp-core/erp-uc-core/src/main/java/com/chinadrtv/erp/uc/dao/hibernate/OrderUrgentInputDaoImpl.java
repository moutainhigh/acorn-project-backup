/*
 * @(#)OrderUrgentInputDaoImpl.java 1.0 2013-7-10下午4:18:35
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.cntrpbank.OrderUrgentInput;
import com.chinadrtv.erp.uc.dao.OrderUrgentInputDao;

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
 * @since 2013-7-10 下午4:18:35 
 * 
 */
public class OrderUrgentInputDaoImpl extends GenericDaoHibernateBase<OrderUrgentInput, Long> implements OrderUrgentInputDao {
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public OrderUrgentInputDaoImpl() {
		super(OrderUrgentInput.class);
	}

	@Autowired
	@Qualifier
	@Required
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

}
