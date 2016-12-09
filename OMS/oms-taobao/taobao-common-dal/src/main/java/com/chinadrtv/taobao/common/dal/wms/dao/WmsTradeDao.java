/*
 * @(#)WmsTradeDao.java 1.0 2014-7-17下午3:56:26
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.common.dal.wms.dao;

import java.util.Map;

import com.chinadrtv.common.dal.BaseDao;

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
 * @since 2014-7-17 下午3:56:26 
 * 
 */
@SuppressWarnings("rawtypes")
public interface WmsTradeDao extends BaseDao {

	/**
	 * <p></p>
	 * @param outerId
	 * @return Long
	 */
	Map<String, Object> calcItemStock(String outerId);

}
