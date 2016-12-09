/*
 * @(#)OrderhistTrackLogDaoImpl.java 1.0 2013年12月24日下午3:42:26
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.agent.OrderhistTrackLog;
import com.chinadrtv.erp.sales.core.dao.OrderhistTrackLogDao;

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
 * @since 2013年12月24日 下午3:42:26 
 * 
 */
@Repository
public class OrderhistTrackLogDaoImpl extends GenericDaoHibernate<OrderhistTrackLog, Long> implements OrderhistTrackLogDao {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param persistentClass
	 */ 
	public OrderhistTrackLogDaoImpl() {
		super(OrderhistTrackLog.class);
	}


}
