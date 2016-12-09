package com.chinadrtv.erp.sms.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.sms.common.Constants;
import com.chinadrtv.erp.smsapi.dto.CouponCrDto;
import com.chinadrtv.erp.smsapi.service.CouponConfigService;
import com.chinadrtv.erp.smsapi.service.CouponCrService;

/***
 * 
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-3-6 下午3:05:05
 * 
 */

@Controller
@RequestMapping("/couponService")
public class SendController {
	private static final Logger logger = LoggerFactory
			.getLogger(SendController.class);
	@Autowired
	private CouponCrService couponCrService;
	@Autowired
	private CouponConfigService couponConfigService;

	/***
	 * 发送单条优惠券
	 * 
	 * @Description: TODO
	 * @param xml
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/singleCoupon", method = RequestMethod.POST)
	public Map<String, Object> group(@RequestBody CouponCrDto couponCr)
			throws Exception {
		Boolean flag = false;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			flag = couponCrService.couponSingleSend(couponCr);
			if (flag == true) {
				result.put("code", Constants.COUPON_STATUS_SUCCESS);
			} else {
				result.put("code", Constants.COUPON_STATUS_FAILED);
				result.put("desc", "发送失败");
			}
		} catch (Exception e) {
			result.put("code", Constants.COUPON_STATUS_FAILED);
			result.put("desc", "单条发送优惠券失败" + e.getMessage());
			logger.error("单条发送优惠券失败" + e.getStackTrace());
			// TODO: handle exception
		}
		return result;
	}

	/**
	 * 
	 * @Description: 优惠券验证
	 * @param couponCr
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/couponChecking", method = RequestMethod.POST)
	public Map<String, Object> couponChecking(@RequestBody CouponCrDto couponCr) {
		Integer flag = 0;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			flag = couponCrService.checkCoupon(couponCr);
			if (flag == -2) {
				result.put("code", Constants.CHECKING_COUPON_STATUS1_CODE);
				result.put("desc", Constants.CHECKING_COUPON_STATUS1);
			} else if (flag == -1) {
				result.put("code", Constants.CHECKING_COUPON_STATUS2_CODE);
				result.put("desc", Constants.CHECKING_COUPON_STATUS2);
			} else if (flag == -3) {
				result.put("code", Constants.CHECKING_COUPON_STATUS3_CODE);
				result.put("desc", Constants.CHECKING_COUPON_STATUS3);
			} else if (flag == -4) {
				result.put("code", Constants.CHECKING_COUPON_STATUS4_CODE);
				result.put("desc", Constants.CHECKING_COUPON_STATUS4);
			} else if (flag == -5) {
				result.put("code", Constants.CHECKING_COUPON_STATUS5_CODE);
				result.put("desc", Constants.CHECKING_COUPON_STATUS5);
			} else {
				result.put("code", Constants.COUPON_STATUS_SUCCESS);
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			result.put("code", Constants.COUPON_STATUS_FAILED);
			result.put("desc", "验证优惠券异常" + e.getMessage());
			logger.error("验证优惠券异常");
		}
		return result;
	}

	/**
	 * 
	 * @Description:优惠券使用
	 * @param coupon
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/couponUse", method = RequestMethod.POST)
	public Map<String, Object> couponUse(@RequestBody CouponCrDto coupon) {
		Boolean flag = false;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			flag = couponCrService.useCoupon(coupon);
			if (flag == true) {
				result.put("code", Constants.COUPON_STATUS_SUCCESS);
			} else {
				result.put("code", Constants.COUPON_STATUS_FAILED);
				result.put("desc", "失败");
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			result.put("code", Constants.COUPON_STATUS_FAILED);
			result.put("desc", "使用优惠券异常" + e.getMessage());
			logger.error("使用优惠券异常");
		}
		return result;
	}

	/**
	 * 
	 * @Description: TODO
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/couponConfigList", method = RequestMethod.POST)
	public Map<String, Object> couponConfigList(@RequestBody CouponCrDto coupon) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("code", Constants.COUPON_STATUS_SUCCESS);
			result.put("resultdt",
					couponConfigService.queryList(coupon.getDepartmentId()));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			result.put("code", Constants.COUPON_STATUS_FAILED);
			result.put("desc", "查询异常" + e.getMessage());
		}

		return result;

	}

}