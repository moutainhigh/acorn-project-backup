/*
 * @(#)DiscourseController.java 1.0 2013-4-7下午6:00:56
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.marketing.core.dto.DiscourseDto;
import com.chinadrtv.erp.marketing.service.DiscourseService;
import com.chinadrtv.erp.marketing.util.HtmlUtil;
import com.chinadrtv.erp.model.marketing.Discourse;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-7 下午6:00:56
 * 
 */
@Controller
@RequestMapping("/discourse")
public class DiscourseController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(DiscourseController.class);
	@Autowired
	private DiscourseService discourseService;

	/***
	 * 保存话术
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param discourse
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("save")
	@ResponseBody
	public Map<String, Object> saveDiscourse(HttpServletRequest request,
			HttpServletResponse response, Discourse discourse) {
		Map<String, Object> resulet = new HashMap<String, Object>();
		Discourse discourse2 = null;
		String temps = "";
		try {
			if (discourse.getId() != null) {
				discourse2 = discourseService.getDiscourseById(discourse
						.getId());
				discourse2.setModifyer(this.getUserId(request));
				discourse2.setModifyTime(new Date());
				discourse2.setDiscourseName(discourse.getDiscourseName());
				discourse2.setDiscourseHtmlContent(discourse
						.getDiscourseHtmlContent());
				discourse2.setProductCode(discourse.getProductCode());
				discourse2.setProductName(discourse.getProductName());
				discourse2.setDiscourseHtmlUrl(HtmlUtil.newHtml(discourse
						.getDiscourseHtmlContent()));
				discourse2.setDepartment(discourse.getDepartment());
				discourse2.setDepartmentCode(discourse.getDepartmentCode());
				discourseService.updateDiscourse(discourse2);
				resulet.put("success", "保存成功");
			} else {
				discourse.setCreator(this.getUserId(request));
				discourse.setCreateTime(new Date());
				discourse.setStatus("0");
				discourse.setDiscourseHtmlUrl(HtmlUtil.newHtml(discourse
						.getDiscourseHtmlContent()));
				temps = discourseService.svaeDiscourse(discourse);
				if (temps.equals("0")) {
					resulet.put("success", "保存成功");
				} else {
					resulet.put("success", "该产品话术已存在");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存失败====" + e);
			resulet.put("success", "保存失败");
		}
		return resulet;
	}

	/**
	 * 话术列表初始化页面
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "discourseIndex", method = RequestMethod.GET)
	public ModelAndView msgDetailList(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("discourse/discourseIndex");
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("startTime", sim.format(new Date()) + " 00:00:00");
		request.setAttribute("endTime", sim.format(new Date()) + " 23:59:59");
		return mav;
	}

	/***
	 * 话术列表json数据显示
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "discourseList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> discourseList(HttpServletRequest request,
			HttpServletResponse response, DiscourseDto discourseDto,
			DataGridModel dataModel) {
		Map<String, Object> result = null;
		try {
			result = discourseService.getDiscourseList(discourseDto, dataModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更改 话术
	 * 
	 * @Description: TODO
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("openWinEditDiscourse")
	public ModelAndView editDiscourse(HttpServletRequest request,
			HttpServletResponse response, Long id, Model model) {
		Discourse discourse = null;
		if (id != null) {
			discourse = discourseService.getDiscourseById(id);
		}
		ModelAndView mav = new ModelAndView("discourse/editDiscourse");
		mav.addObject("discourse", discourse);
		return mav;
	}

	/**
	 * 通过code 查找该话术是否存在
	 * 
	 * @Description: TODO
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("queryByCode")
	@ResponseBody
	public Map<String, Object> queryByCode(HttpServletRequest request,
			HttpServletResponse response, String productCode) {
		List<Discourse> discourse = discourseService.queryByProduct(
				productCode, "");
		Map<String, Object> map = new HashMap<String, Object>();
		if (discourse != null && !discourse.isEmpty()) {
			map.put("flag", "该产品已存在话术");
		} else {
			map.put("flag", "可以新建话术");
		}
		return map;
	}

	/**
	 * 设置无效
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("deleteDiscourse")
	@ResponseBody
	public Map<String, Object> deleteDiscourse(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		try {
			discourseService.deleteDiscourse(id);
			map.put("resulet", "设置无效成功");
		} catch (Exception e) {
			map.put("resulet", "设置无效失败");
			// TODO: handle exception\
			e.printStackTrace();
		}
		return map;
	}

}
