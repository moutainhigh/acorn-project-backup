/*
 * @(#)WxOcsCalllistService.java 1.0 2013-12-16下午3:00:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.Date;

import com.chinadrtv.erp.model.marketing.Campaign;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-16 下午3:00:19
 * 
 */
public interface WxOcsCalllistService {
	/**
	 * 
	 * @Description: 执行批量插入cti数据
	 * @param campaign
	 * @param now
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean batchInsert(Campaign campaign, Date now);

}
