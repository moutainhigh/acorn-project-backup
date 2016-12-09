/*
 * @(#)PreTradeItemDao.java 1.0 2014-6-17上午11:00:53
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.dal.oms.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.oms.PreTradeItem;

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
 * @since 2014-6-17 上午11:00:53 
 * 
 */
public interface PreTradeItemDao extends BaseDao<PreTradeItem> {

	public PreTradeItem queryByItemId(String itemId);
}
