/*
 * @(#)StockSyncDao.java 1.0 2014-7-17下午2:28:23
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.common.dal.dao;

import java.util.List;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.taobao.common.dal.dto.ProductSuiteDto;

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
 * @since 2014-7-17 下午2:28:23 
 * 
 */
@SuppressWarnings("rawtypes")
public interface StockSyncDao extends BaseDao {

	/**
	 * <p></p>
	 * @param itemId
	 * @return
	 */
	Long queryUnConfirmedStockByItem(String itemId);

	/**
	 * <p></p>
	 * @param suiteId
	 * @return
	 */
	List<ProductSuiteDto> querySuiteInfoBySuiteId(String suiteId);

	/**
	 * <p></p>
	 * @param outerId
	 * @return
	 */
	Boolean checkItemExistance(String outerId);

}
