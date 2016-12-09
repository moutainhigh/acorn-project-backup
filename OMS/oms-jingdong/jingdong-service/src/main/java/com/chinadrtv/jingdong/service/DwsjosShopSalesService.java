/*
 * @(#)DwsjosShopSalesService.java 1.0 2014-5-20下午3:12:57
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.jingdong.service;

import java.util.Date;
import java.util.List;

import com.chinadrtv.jingdong.model.JingdongOrderConfig;

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
 * @since 2014-5-20 下午3:12:57 
 * 
 */
public interface DwsjosShopSalesService {

	/**
	 * <p>抽取PV</p>
	 * @param date
	 */
	void importPv(List<JingdongOrderConfig> configList, Date date) throws Exception;

}
