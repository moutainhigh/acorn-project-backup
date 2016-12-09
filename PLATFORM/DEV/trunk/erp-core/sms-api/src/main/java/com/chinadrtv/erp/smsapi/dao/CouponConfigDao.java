/*
 * @(#)CouponConfigDao.java 1.0 2013-8-23下午3:13:30
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CouponConfig;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-23 下午3:13:30
 * 
 */
public interface CouponConfigDao extends
		GenericDao<CouponConfig, java.lang.Long> {

	public List<CouponConfig> query(CouponConfig couponConfig,
			DataGridModel dataModel);

	public Integer queryCount(CouponConfig couponConfig);

	public Boolean updateStatus(Long id);

	public List<CouponConfig> queryList(String deparment);

}
