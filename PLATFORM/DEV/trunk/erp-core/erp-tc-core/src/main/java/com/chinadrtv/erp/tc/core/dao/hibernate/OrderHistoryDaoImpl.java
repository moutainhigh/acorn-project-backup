/*
 * @(#)OrderHistoryDaoImpl.java 1.0 2013-1-30上午10:43:38
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
import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.OrderHistory;
import com.chinadrtv.erp.tc.core.dao.OrderHistoryDao;

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
 * @since 2013-1-30 上午10:43:38 
 * 
 */
@Repository
public class OrderHistoryDaoImpl extends GenericDaoHibernateBase<OrderHistory, Long> implements OrderHistoryDao {

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param persistentClass
	*/ 
	public OrderHistoryDaoImpl() {
		super(OrderHistory.class);
	}
	
	@Autowired
	@Required
	@Qualifier("sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.doSetSessionFactory(sessionFactory);
	}

	/* 
	 * 根据订单业务号和业务版本号获取订单历史
	* <p>Title: getByOrderIdAndVersion</p>
	* <p>Description: </p>
	* @param orderid
	* @param revision
	* @return
	* @see com.chinadrtv.erp.shipment.dao.OrderHistoryDao#getByOrderIdAndVersion(java.lang.String, java.lang.Integer)
	*/ 
	public OrderHistory getByOrderIdAndVersion(String orderid, Integer revision) {
		String hql = "select oh from OrderHistory oh where oh.orderid=:orderid and oh.revision=:revision";
		//String hql = "select oh from OrderHistory oh where oh.orderid=:orderid";
		Parameter<String> orderidParam = new ParameterString("orderid", orderid);
		Parameter<Integer> revisionParam = new ParameterInteger("revision", revision);
		return this.find(hql, orderidParam, revisionParam);
	}
}
