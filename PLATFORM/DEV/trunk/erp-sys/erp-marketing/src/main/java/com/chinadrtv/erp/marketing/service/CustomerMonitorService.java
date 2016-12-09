/*
 * @(#)CustomerMonitorService.java 1.0 2014-2-26下午2:23:58
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service;

import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.model.marketing.CustomerMonitor;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-2-26 下午2:23:58
 * 
 */
public interface CustomerMonitorService {
	/**
	 * 
	 * @Description: 分页查询
	 * @param customerMonitor
	 * @param dataModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	public Map<String, Object> list(CustomerMonitor customerMonitor,
			DataGridModel dataModel);

	/**
	 * 
	 * @Description: 保存
	 * @param customerMonitor
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean saves(CustomerMonitor customerMonitor);

	/**
	 * 
	 * @Description: 更新
	 * @param customerMonitor
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean updates(CustomerMonitor customerMonitor);

	/**
	 * 
	 * @Description: seq 值
	 * @return
	 * @return Long
	 * @throws
	 */
	public Long getSeq();

}
