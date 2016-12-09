/*
 * @(#)DwsjosShopSalesDao.java 1.0 2014-5-20下午2:57:45
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.jingdong.common.dal.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.jingdong.common.dal.model.DwsjosShopSales;

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
 * @since 2014-5-20 下午2:57:45 
 * 
 */
public interface DwsjosShopSalesDao extends BaseDao<DwsjosShopSales> {

	/**
	 * <p></p>
	 * @param dwsjosShopSales
	 * @return
	 */
	DwsjosShopSales queryDailyByOrderType(DwsjosShopSales dwsjosShopSales);

}
