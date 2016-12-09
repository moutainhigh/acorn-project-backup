/*
 * @(#)CouponCrService.java 1.0 2013-8-19下午3:10:53
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.service;

import java.util.Map;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.CouponCr;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dto.CouponCrDto;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-19 下午3:10:53
 * 
 */
public interface CouponCrService {

	public Map<String, Object> getListForPage(CouponCrDto coupon,
			DataGridModel dataModel);

	public CouponCr getCouponCrByCampaignId(Long campaignId);

	public Boolean couponSingleSend(CouponCrDto coupon) throws ServiceException;

	public Integer checkCoupon(CouponCrDto coupon) throws ServiceException;

	public Boolean useCoupon(CouponCrDto coupon) throws ServiceException;

}
