/*
 * @(#)OrderUrgentInputServiceImpl.java 1.0 2013-7-10下午4:20:45
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.cntrpbank.OrderUrgentInput;
import com.chinadrtv.erp.uc.dao.OrderUrgentInputDao;
import com.chinadrtv.erp.uc.service.OrderUrgentInputService;

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
 * @since 2013-7-10 下午4:20:45 
 * 
 */
public class OrderUrgentInputServiceImpl extends GenericServiceImpl<OrderUrgentInput, Long> implements OrderUrgentInputService {

	@Autowired
	private OrderUrgentInputDao orderUrgentInputDao;
	
	@Override
	protected GenericDao<OrderUrgentInput, Long> getGenericDao() {
		return orderUrgentInputDao;
	}

}
