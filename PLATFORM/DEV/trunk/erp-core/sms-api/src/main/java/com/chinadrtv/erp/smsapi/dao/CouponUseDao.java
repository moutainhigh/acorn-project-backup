/*
 * @(#)CouponUseDao.java 1.0 2013-8-15下午3:02:54
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.CouponUse;
import com.chinadrtv.erp.smsapi.dto.CouponCrDto;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-15 下午3:02:54
 * 
 */
public interface CouponUseDao extends GenericDao<CouponUse, java.lang.Long> {

	public void saveCouponUseList(List<CouponUse> coupon);

	public CouponUse getCouponUseByCouponId(CouponCrDto coupon);

	public Boolean updateCouponUse(CouponUse couponUse);

}
