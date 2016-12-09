/*
 * @(#)OrderImportService.java 1.0 2014-11-12上午10:58:38
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.suning.service;

import java.util.Date;
import java.util.List;

import com.chinadrtv.oms.suning.dto.OrderConfig;

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
 * @since 2014-11-12 上午10:58:38 
 * 
 */
public interface OrderImportService {

	/**
	 * <p></p>
	 * @param configList
	 * @param startDate
	 * @param endDate
	 */
	void importPreTrade(List<OrderConfig> configList, Date startDate, Date endDate);

}
