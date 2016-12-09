/*
 * @(#)StockSyncService.java 1.0 2014-7-14上午10:25:23
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.service;

import java.util.List;

import com.chinadrtv.taobao.common.dal.dto.ProductSuiteDto;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;

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
 * @since 2014-7-14 上午10:25:23 
 * 
 */
public interface StockSyncService {

	/**
	 * <p>synchronize online item list</p>
	 * @param configList
	 */
	void syncItemList(List<TaobaoOrderConfig> configList);

	/**
	 * <p>synchronize local stock</p>
	 * @param configList
	 */
	void syncStockByShop(List<TaobaoOrderConfig> configList);
	
	/**
	 * <p>query unconfirmed item stock</p>
	 * @param itemId
	 * @return Long
	 */
	Long queryUnConfirmedStockByItem(String itemId);
	
	/**
	 * <p>query product suite info with product suite id</p>
	 * @param suiteId
	 * @return List<ProductSuiteDto>
	 */
	List<ProductSuiteDto> querySuiteInfoBySuiteId(String suiteId);

}
