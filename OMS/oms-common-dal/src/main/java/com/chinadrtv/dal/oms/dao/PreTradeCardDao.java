/*
 * @(#)PreTradeDao.java 1.0 2013年10月30日上午11:30:02
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.dal.oms.dao;

import java.util.List;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.oms.PreTradeCard;

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
public interface PreTradeCardDao extends BaseDao<PreTradeCard> {

	/**
	 * 批量插入
	 * <p></p>
	 * @param preTradeCardList
	 */
	void insertBatch(List<PreTradeCard> preTradeCardList);

}
