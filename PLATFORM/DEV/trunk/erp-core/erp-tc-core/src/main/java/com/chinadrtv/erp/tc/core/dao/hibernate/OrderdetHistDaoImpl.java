/*
 * @(#)OrderdetHistDaoImpl.java 1.0 2013-1-28下午3:15:59
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.agent.OrderdetHist;
import com.chinadrtv.erp.tc.core.dao.OrderdetHistDao;

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
 * @since 2013-1-28 下午3:15:59 
 * 
 */
@Repository
public class OrderdetHistDaoImpl extends GenericDaoHibernateBase<OrderdetHist, Long> implements OrderdetHistDao {

	public OrderdetHistDaoImpl() {
		super(OrderdetHist.class);
	}

	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}
}
