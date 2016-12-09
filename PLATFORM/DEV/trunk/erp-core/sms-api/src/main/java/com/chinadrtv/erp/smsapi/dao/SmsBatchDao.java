/*
 * @(#)SmsDao.java 1.0 2013-2-18下午6:11:56
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;

import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.model.SmsBatch;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-18 下午6:11:56
 * 
 */
public interface SmsBatchDao {
	/***
	 * 根据批次号获得数据
	 * 
	 * @Description: TODO
	 * @param batchId
	 * @return
	 * @return List
	 * @throws
	 */
	public List<SmsBatch> getBatchList(String batchId);

	/***
	 * 删除当前批量短信的数据
	 * 
	 * @Description: TODO
	 * @param batchId
	 * @return void
	 * @throws
	 */
	public void deleteAllByBatchId(String batchId);

	/***
	 * 插入批次表
	 * 
	 * @Description: TODO
	 * @param smsBatch
	 * @return void
	 * @throws
	 */
	public void insertSmsBatch(List<SmsBatch> smsBatch);

	/**
	 * 分页查询
	 * 
	 * @Description: TODO
	 * @param sql
	 * @param offset
	 * @param length
	 * @return
	 * @return List<SmsBatch>
	 * @throws
	 */
	public List<SmsBatch> getBatchPage(DataGridModel data, String batchid);

	/**
	 * 
	 * @Description: 根据batchId 查询总量
	 * @param batchId
	 * @return
	 * @return Integer
	 * @throws
	 */
	public Integer getBatchCount(String batchId);

}
