/*
 * @(#)OrderhistTrackLogServiceImpl.java 1.0 2013年12月24日下午3:53:03
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.agent.OrderhistTrackLog;
import com.chinadrtv.erp.sales.core.dao.OrderhistTrackLogDao;
import com.chinadrtv.erp.sales.core.service.OrderhistTrackLogService;

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
 * @since 2013年12月24日 下午3:53:03 
 * 
 */
@Service
public class OrderhistTrackLogServiceImpl extends GenericServiceImpl<OrderhistTrackLog, Long> implements OrderhistTrackLogService {

	@Autowired
	private OrderhistTrackLogDao orderhistTrackLogDao;
	
	/** 
	 * <p>Title: getGenericDao</p>
	 * <p>Description: </p>
	 * @return
	 * @see com.chinadrtv.erp.core.service.impl.GenericServiceImpl#getGenericDao()
	 */ 
	@Override
	protected GenericDao<OrderhistTrackLog, Long> getGenericDao() {
		return orderhistTrackLogDao;
	}

	
}
