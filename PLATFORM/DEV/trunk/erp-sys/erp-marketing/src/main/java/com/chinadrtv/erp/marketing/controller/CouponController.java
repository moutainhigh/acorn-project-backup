/*
 * @(#)CouponController.java 1.0 2013-8-23下午3:55:51
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.model.marketing.CouponConfig;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dto.CouponCrDto;
import com.chinadrtv.erp.smsapi.service.CouponConfigService;
import com.chinadrtv.erp.smsapi.service.CouponCrService;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-8-23 下午3:55:51
 * 
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {
	@Autowired
	private CouponConfigService couponConfigService;
	@Autowired
	private CouponCrService couponCrService;
	private static final Logger logger = LoggerFactory
			.getLogger(CouponController.class);

	/**
	 * 
	 * @Description: 初始化couponConfig页面
	 * @param from
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "init", method = RequestMethod.GET)
	public ModelAndView main(
			@RequestParam(required = false, defaultValue = "") String from,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		ModelAndView mav = new ModelAndView("coupon/index");
		logger.info("进入couponConfig功能");
		return mav;
	}

	/**
	 * 获取营销计划列表
	 * 
	 * @param campaign
	 * @param dataModel
	 * @throws IOException
	 * @throws JSONException
	 * @throws ParseException
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request,
			CouponConfig couponConfig, DataGridModel dataModel)
			throws IOException, JSONException, ParseException {
		Map<String, Object> reuslt = couponConfigService.getListForPage(
				couponConfig, dataModel);
		return reuslt;
	}

	/**
	 * 
	 * @Description: 打开编辑窗口
	 * @param request
	 * @param response
	 * @param groupCode
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "openWinEditCouponConfig", method = RequestMethod.GET)
	public ModelAndView openWinEditRecommendSet(HttpServletRequest request,
			HttpServletResponse response, Long id) throws IOException {
		ModelAndView mav = new ModelAndView("coupon/editCouponConfig");
		if (id != null) {
			CouponConfig couponConfig = couponConfigService.getById(id);
			// Campaign campaign = campaignService.getCampaignById(id);
			mav.addObject("couponConfig", couponConfig);
		}
		return mav;
	}

	/**
	 * 
	 * @Description: 保存或更新营couponConfig
	 * @param request
	 * @param response
	 * @param group
	 * @param model
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "saveCouponConfig", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCampaign(HttpServletRequest request,
			HttpServletResponse response, CouponConfig couponConfig,
			String returnUrl) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean flag = false;
		if (couponConfig.getId() == null) {
			couponConfig.setCrdt(new Date());
			couponConfig.setCrusr(getUserId(request));
		} else {
			couponConfig.setMddt(new Date());
			couponConfig.setMdusr(getUserId(request));
		}
		couponConfig.setStatus("1");
		flag = couponConfigService.saveCouponConfig(couponConfig);
		result.put("result", flag);
		return result;
	}

	/**
	 * 
	 * @Description: 删除couponConfig
	 * @param request
	 * @param response
	 * @param group
	 * @param model
	 * @return
	 * @throws IOException
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "deleteCouponConfig", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCouponConfig(HttpServletRequest request,
			HttpServletResponse response, String id) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean flag = false;
		flag = couponConfigService.deleteCouponConfig(id);
		result.put("result", flag);
		return result;
	}

	/**
	 * 
	 * @Description: 优惠券查看分页
	 * @param request
	 * @param couponCr
	 * @param dataModel
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "couponCrList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> couponCrList(HttpServletRequest request,
			CouponCrDto coupon, DataGridModel dataModel) {
		Map<String, Object> result = new HashMap<String, Object>();

		result = couponCrService.getListForPage(coupon, dataModel);

		return result;

	}

	/**
	 * 
	 * @Description: 初始化couponConfig页面
	 * @param from
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "couponCrInit", method = RequestMethod.GET)
	public ModelAndView couponCrInit(
			@RequestParam(required = false, defaultValue = "") String from,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		ModelAndView mav = new ModelAndView("coupon/couponListForCampaign");
		return mav;
	}

}
