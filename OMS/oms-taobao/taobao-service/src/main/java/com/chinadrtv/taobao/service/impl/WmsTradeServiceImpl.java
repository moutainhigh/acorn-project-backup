/*
 * @(#)WmsTradeServiceImpl.java 1.0 2014-7-21上午9:58:23
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.taobao.common.dal.wms.dao.WmsTradeDao;
import com.chinadrtv.taobao.service.WmsTradeService;

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
 * @since 2014-7-21 上午9:58:23 
 * 
 */
@Service
public class WmsTradeServiceImpl implements WmsTradeService {
	
	@Autowired
	private WmsTradeDao wmsTradeDao;
	
	/** 
	 * <p>Title: calcItemStock</p>
	 * <p>Description: </p>
	 * @param outerId
	 * @return Long
	 * @see com.chinadrtv.taobao.service.WmsTradeService#calcItemStock(java.lang.String)
	 */ 
	@Override
	public Long calcItemStock(String itemId) {
		Map<String, Object> rsMap = wmsTradeDao.calcItemStock(itemId);
		Long qty = 0L;
		if (null != rsMap) {
			qty = null == rsMap.get("qty") ? 0L : Long.parseLong(String.valueOf(rsMap.get("qty"))); 
		}
		return qty;
	}

}
