/*
 * @(#)CouponCrServiceImpl.java 1.0 2013-8-19下午3:12:16
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.exception.ExceptionConstant;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.CouponConfig;
import com.chinadrtv.erp.model.marketing.CouponCr;
import com.chinadrtv.erp.model.marketing.CouponUse;
import com.chinadrtv.erp.model.marketing.Customers;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.CouponConfigDao;
import com.chinadrtv.erp.smsapi.dao.CouponCrDao;
import com.chinadrtv.erp.smsapi.dao.CouponUseDao;
import com.chinadrtv.erp.smsapi.dto.CouponCrDto;
import com.chinadrtv.erp.smsapi.service.CouponCrService;
import com.chinadrtv.erp.smsapi.service.SingleSmsSendService;
import com.chinadrtv.erp.smsapi.util.CouponIdUtil;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.UUidUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-19 下午3:12:16
 * 
 */
@Service("couponCrService")
public class CouponCrServiceImpl implements CouponCrService {
	@Autowired
	private CouponCrDao couponCrDao;
	@Autowired
	private CouponUseDao couponUseDao;
	@Autowired
	private SingleSmsSendService singleSmsSendService;
	@Autowired
	private CouponConfigDao couponConfigDao;
	private static final Logger logger = LoggerFactory
			.getLogger(CouponCrServiceImpl.class);

	public CouponCr getCouponCrByCampaignId(Long campaignId) {

		String hql = "from  CouponCr  where campaignId=:campaignId";
		Parameter camid = new ParameterLong("campaignId", campaignId);
		CouponCr couponCr = null;
		if (couponCrDao.find(hql, camid) != null) {
			couponCr = couponCrDao.find(hql, camid);
		}
		return couponCr;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getListForPage
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @param dataModel
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponCrService#getListForPage(com.chinadrtv
	 *      .erp.model.marketing.CouponCr,
	 *      com.chinadrtv.erp.smsapi.constant.DataGridModel)
	 */
	public Map<String, Object> getListForPage(CouponCrDto coupon,
			DataGridModel dataModel) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<CouponCr> list = couponCrDao.query(coupon, dataModel);
		Integer total = couponCrDao.queryCount(coupon);
		result.put("rows", list);
		result.put("total", total);

		return result;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: couponSingleSend
	 * </p>
	 * <p>
	 * Description:单条发送优惠券
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @return
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponCrService#couponSingleSend(com
	 *      .chinadrtv.erp.smsapi.dto.CouponCrDto)
	 */

	public Boolean couponSingleSend(CouponCrDto coupon) throws ServiceException {
		// TODO Auto-generated method stub
		if (coupon == null) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION,
					"couponDto is null");
		}
		if (coupon.getCouponConfigId() == null) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION,
					"CouponConfigId is null");
		}
		CouponConfig couponConfig = couponConfigDao.get(coupon
				.getCouponConfigId());
		String template = couponConfig.getTemplateContent();
		String couponId = CouponIdUtil.getCouponId();

		Customers customers = new Customers();
		if (template.contains("{user.money}")
				|| template.contains("{user.couponId}")
				|| template.contains("{user.startTime}")
				|| template.contains("{user.endTime}")
				|| template.contains("{user.useMoney}")) {
			customers.setCouponId(couponId);
			customers.setMoney(couponConfig.getCouponValue());
			customers.setStartTime(DateTimeUtil.sim6.format(couponConfig
					.getStardt()));
			customers.setEndTime(DateTimeUtil.sim6.format(couponConfig
					.getEnddt()));
			customers.setUseMoney(couponConfig.getUseMoney());
			template = template
					.replace("{user.money}", customers.getUseMoney());
			template = template.replace("{user.couponId}",
					customers.getCouponId());
			template = template.replace("{user.startTime}",
					customers.getStartTime());
			template = template.replace("{user.endTime}",
					customers.getEndTime());
			template = template.replace("{user.useMoney}",
					customers.getUseMoney());
		}
		CouponCr couponCr = new CouponCr();

		String smsContent = template;
		String uuid = UUidUtil.getUUid();
		couponCr = new CouponCr();
		couponCr.setCampaignId(0l);
		couponCr.setContactId(coupon.getContactId());
		couponCr.setCouponId(couponId);
		couponCr.setCouponValue(couponConfig.getCouponValue());
		couponCr.setCrdt(new Date());
		couponCr.setCrusr(coupon.getCrusr());
		couponCr.setDeliverStatus("0");
		couponCr.setSmsBatchid(uuid);
		couponCr.setSmsUuid(uuid);
		couponCr.setStartdt(couponConfig.getStardt());
		couponCr.setEnddt(couponConfig.getEnddt());
		couponCr.setStatus("0");
		couponCr.setType(couponConfig.getCouponType());
		couponCr.setPhone(coupon.getPhone());
		couponCr.setMoneyUse(couponConfig.getUseMoney());
		couponCr.setUsedDepartment(couponConfig.getUseDeparment());

		CouponUse couponUse = new CouponUse();
		couponUse.setCampaignId(0l);
		couponUse.setContactId(coupon.getContactId());
		couponUse.setCouponType(couponConfig.getCouponType());
		couponUse.setCouponValue(couponConfig.getCouponValue());
		couponUse.setCrdt(new Date());
		couponUse.setCrusr(coupon.getCrusr());
		couponUse.setPhone(coupon.getPhone());
		couponUse.setSmsBatchid(uuid);
		couponUse.setSmsUuid(uuid);
		couponUse.setCouponId(couponId);

		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		List list = new ArrayList();

		Boolean result = false;

		try {
			CouponCr couponCr2 = couponCrDao.save(couponCr);
			if (couponCr2 != null) {
				couponUseDao.save(couponUse);
				singleSmsSendService.singleSend("Y", "", "", listmap, 9l, "N",
						"Y", "橡果国际", coupon.getCrusr(), smsContent, list,
						"iagent:coupon", coupon.getDepartmentId(),
						coupon.getPhone(), coupon.getContactId(), "", uuid, "",
						"", "");
			}
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("单条发送优惠券失败" + e.getStackTrace());
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}

		return result;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: checkCoupon
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponCrService#checkCoupon(com.chinadrtv
	 *      .erp.smsapi.dto.CouponCrDto)
	 */

	public Integer checkCoupon(CouponCrDto coupon) throws ServiceException {
		// TODO Auto-generated method stub
		Integer result = 0;
		if (coupon == null || coupon.getCouponId() == null) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION,
					"couponDto is null");
		}
		CouponCr couponCr = couponCrDao.getCouponCrByCouponId(coupon);
		if (couponCr == null) {
			return -3;
		} else {
			Date nowDate = new Date();
			if (!couponCr.getStatus().equals("0")) {
				result = -5;
			} else {
				if (couponCr.getUsedDepartment().contains(
						coupon.getDepartmentId())) {
					if (couponCr.getStartdt().getTime() < nowDate.getTime()
							&& couponCr.getEnddt().getTime() > nowDate
									.getTime()) {
						if (coupon.getOrderMoney() != null) {
							Integer money = Integer.valueOf(coupon
									.getOrderMoney());
							if (money < Integer.valueOf(couponCr.getMoneyUse())) {
								result = -4;
							} else {
								result = 1;
							}
						}
					} else {
						result = -1;
					}
				} else {
					result = -2;
				}
			}
		}
		return result;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: useCoupon
	 * </p>
	 * <p>
	 * Description:优惠券使用
	 * </p>
	 * 
	 * @param coupon
	 * 
	 * @return
	 * 
	 * @throws ServiceException
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.CouponCrService#useCoupon(com.chinadrtv
	 *      .erp.smsapi.dto.CouponCrDto)
	 */
	public Boolean useCoupon(CouponCrDto coupon) throws ServiceException {
		// TODO Auto-generated method stub
		Boolean result = false;
		if (coupon == null || coupon.getCouponId() == null
				|| coupon.getUseType() == null) {
			throw new ServiceException(
					ExceptionConstant.SERVICE_PARAMETER_EXCEPTION,
					"coupon is null");
		}
		CouponCr couponCr = couponCrDao.getCouponCrByCouponId(coupon);
		if (couponCr == null) {
			return result;
		}
		CouponUse couponUse = couponUseDao.getCouponUseByCouponId(coupon);
		if (couponUse == null) {
			return result;
		}
		if (!("0").equals(couponCr.getStatus())) {
			return result;
		}

		couponUse.setUseContactId(coupon.getContactId());
		couponUse.setUsePhone(coupon.getPhone());
		couponUse.setOrderId(coupon.getOrderId());
		if (coupon.getUseType().equals("0")) {
			couponCr.setStatus("0");
			couponUse.setType("0");
		}
		if (coupon.getUseType().equals("1")) {
			couponCr.setStatus("1");
			couponUse.setType("1");
		}
		if (coupon.getUseType().equals("5")) {
			couponCr.setStatus("-1");
			couponUse.setType("5");
		}
		try {
			couponCrDao.updateCouponCr(couponCr);
			result = couponUseDao.updateCouponUse(couponUse);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("更改状态失败" + couponCr.getCouponId());
			throw new ServiceException(ExceptionConstant.SERVICE_EXCEPTION, e);
		}
		return result;
	}
}
