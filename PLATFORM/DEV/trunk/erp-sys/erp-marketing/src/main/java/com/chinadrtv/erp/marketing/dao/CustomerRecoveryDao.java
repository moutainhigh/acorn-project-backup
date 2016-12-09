/*
 * @(#)CustomerRecoveryDao.java 1.0 2013-4-17下午3:21:11
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.dao;

import java.util.List;

import com.chinadrtv.erp.model.marketing.CustomerRecovery;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-17 下午3:21:11
 * 
 */
public interface CustomerRecoveryDao {

	public void insert(CustomerRecovery customerRecovery);

	public List<CustomerRecovery> getCustomerRecovery(String batchCode);

	public Long getCountByBatchId(String batchid);

	public void insertRecoverCount(String groupCode);

	public Long queryObContactUnuse(String batchid);

	public Long queryObContactUsed(String batchid);

	public Long queryTotalObContactUsed(String batchid, String status);

}
