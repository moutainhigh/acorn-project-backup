/*
 * @(#)PreTradeItemServiceImpl.java 1.0 2014-6-18下午1:21:46
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.dal.oms.dao.PreTradeItemDao;
import com.chinadrtv.model.oms.PreTradeItem;
import com.chinadrtv.service.oms.PreTradeItemService;

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
 * @since 2014-6-18 下午1:21:46 
 * 
 */
@Service
public class PreTradeItemServiceImpl implements PreTradeItemService {

	@Autowired
	private PreTradeItemDao preTradeItemDao;

	/** 
	 * <p>Title: queryByItemId</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @return
	 * @see com.chinadrtv.service.oms.PreTradeItemService#queryByItemId(java.lang.String)
	 */ 
	@Override
	public PreTradeItem queryByItemId(String itemId) {
		return preTradeItemDao.queryByItemId(itemId);
	}
}
