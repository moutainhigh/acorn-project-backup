/*
 * @(#)PreTradeDao.java 1.0 2013年10月30日上午11:30:02
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.dal.oms.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.oms.PreTradeLot;

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
 * @since 2013年10月30日 上午11:30:02 
 * 
 */
public interface PreTradeLotDao extends BaseDao<PreTradeLot> {

	Long insert(PreTradeLot preTradeLot);
}
