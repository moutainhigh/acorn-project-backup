/*
 * @(#)CouponDao.java 1.0 2013-8-12下午3:43:30
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CouponCr;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dto.CouponCrDto;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-12 下午3:43:30
 * 
 */
public interface CouponCrDao extends GenericDao<CouponCr, java.lang.Long> {
	public List<CouponCr> query(CouponCrDto coupon, DataGridModel dataModel);

	public Integer queryCount(CouponCrDto coupon);

	public void saveCouponList(List<CouponCr> coupon);

	public CouponCr getCouponCrByCouponId(CouponCrDto coupon);

	public Boolean updateCouponCr(CouponCr couponCr);

}
