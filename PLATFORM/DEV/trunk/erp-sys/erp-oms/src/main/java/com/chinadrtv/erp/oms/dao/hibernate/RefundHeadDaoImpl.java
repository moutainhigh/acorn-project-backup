/*
 * @(#)RefundHeadDaoImpl.java 1.0 2013-4-24下午2:31:37
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.trade.RefundHead;
import com.chinadrtv.erp.oms.dao.RefundHeadDao;

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
 * @since 2013-4-24 下午2:31:37 
 * 
 */
@Repository
public class RefundHeadDaoImpl extends GenericDaoHibernate<RefundHead, Long> implements RefundHeadDao {
 
	public RefundHeadDaoImpl() {
		super(RefundHead.class);
	}

}
