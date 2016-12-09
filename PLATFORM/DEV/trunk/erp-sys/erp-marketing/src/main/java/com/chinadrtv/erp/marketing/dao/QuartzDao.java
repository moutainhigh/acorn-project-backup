/*
 * @(#)QuartzDao.java 1.0 2013-1-25下午1:22:44
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.dto.QuartzTriggerDto;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-1-25 下午1:22:44
 * 
 */
public interface QuartzDao {
	public List<QuartzTriggerDto> getQrtzTriggers(
			QuartzTriggerDto quartzTriggerDto, DataGridModel dataGridModel);

	/**
	 * 查询Trigger总数
	 */

	public Long getQrtzTriggersCount(QuartzTriggerDto quartzTriggerDto);
}
