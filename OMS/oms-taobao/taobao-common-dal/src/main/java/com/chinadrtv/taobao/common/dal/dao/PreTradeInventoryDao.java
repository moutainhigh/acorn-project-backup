/*
 * @(#)PreTradeInventoryDao.java 1.0 2014-7-14下午2:58:18
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.common.dal.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.taobao.common.dal.dto.ItemStockSyncDto;
import com.chinadrtv.taobao.common.dal.model.PreTradeInventory;

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
 * @since 2014-7-14 下午2:58:18 
 * 
 */
public interface PreTradeInventoryDao extends BaseDao<PreTradeInventory> {

	/**
	 * <p></p>
	 * @param pti
	 * @return Boolean
	 */
	Boolean exist(PreTradeInventory pti);

	/**
	 * <p></p>
	 * @param tradeType
	 * @return
	 */
	List<ItemStockSyncDto> querySyncList(String tradeType);


	/**
	 * <p></p>
	 * @param outerId
	 */
	void updateSynchronizedStock(Map<String, Object> params);
}
