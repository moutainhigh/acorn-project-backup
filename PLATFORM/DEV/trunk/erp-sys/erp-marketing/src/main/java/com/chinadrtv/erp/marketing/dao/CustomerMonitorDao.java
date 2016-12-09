/*
 * @(#)CustomerMonitorDao.java 1.0 2014-2-26下午2:12:17
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CustomerMonitor;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-2-26 下午2:12:17
 * 
 */
public interface CustomerMonitorDao extends
		GenericDao<CustomerMonitor, java.lang.Long> {
	/**
	 * 
	 * @Description: 分页查询
	 * @param customerMonitor
	 * @param dataModel
	 * @return
	 * @return List<CustomerMonitor>
	 * @throws
	 */
	public List<CustomerMonitor> query(CustomerMonitor customerMonitor,
			DataGridModel dataModel);

	/**
	 * 
	 * @Description: 查询总数用于分页
	 * @param customerMonitor
	 * @param dataModel
	 * @return
	 * @return List<CustomerMonitor>
	 * @throws
	 */
	public Integer queryCount(CustomerMonitor customerMonitor);

	/**
	 * 
	 * @Description: 保存监控记录
	 * @param customerMonitor
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public void saves(CustomerMonitor customerMonitor);

	/**
	 * 
	 * @Description: 更新
	 * @param customerMonitor
	 * @return void
	 * @throws
	 */

	public void updates(CustomerMonitor customerMonitor);

	/**
	 * 
	 * @Description: seq值
	 * @return
	 * @return Long
	 * @throws
	 */

	public Long getSeq();

}
