/*
 * @(#)CouponConfigService.java 1.0 2013-8-23下午3:36:16
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.CouponConfig;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-23 下午3:36:16
 * 
 */
public interface CouponConfigService {
	public Map<String, Object> getListForPage(CouponConfig coupon,
			DataGridModel dataModel);

	public CouponConfig getById(Long id);

	public Boolean saveCouponConfig(CouponConfig couponConfig);

	public Boolean deleteCouponConfig(String ids);

	public List<CouponConfig> queryList(String department)
			throws ServiceException;

}
