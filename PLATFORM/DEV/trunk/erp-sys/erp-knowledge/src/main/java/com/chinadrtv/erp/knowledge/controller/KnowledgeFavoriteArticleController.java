/*
 * @(#)KnowledgeFavoriteArticleController.java 1.0 2013-12-3下午2:44:48
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.model.KnowledgeFavoriteArticle;
import com.chinadrtv.erp.knowledge.service.KnowledgeFavoriteArticleService;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-3 下午2:44:48
 * 
 */
@Controller
@RequestMapping("/favorite")
public class KnowledgeFavoriteArticleController extends BaseController {
	@Autowired
	private KnowledgeFavoriteArticleService knowledgeFavoriteArticleService;

	/**
	 * 
	 * @Description: 添加到收藏
	 * @param knowledgeFavoriteArticle
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/addFavotite")
	@ResponseBody
	public Map<String, Object> addFavorite(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle,
			HttpServletRequest request) {
		knowledgeFavoriteArticle.setCreateDate(new Date());
		knowledgeFavoriteArticle.setCreateUser(getUserId(request));
		knowledgeFavoriteArticle.setStatus("1");
		knowledgeFavoriteArticle.setDeptType(getGoupType(request));

		return knowledgeFavoriteArticleService
				.saveMyFavoriteArticle(knowledgeFavoriteArticle);
	}

	/**
	 * 
	 * @Description: 取消收藏
	 * @param knowledgeFavoriteArticle
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/removeFavotite")
	@ResponseBody
	public Map<String, Object> removeFavorite(
			KnowledgeFavoriteArticle knowledgeFavoriteArticle,
			HttpServletRequest request) {
		knowledgeFavoriteArticle.setCreateUser(getUserId(request));
		knowledgeFavoriteArticle.setDeptType(getGoupType(request));
		return knowledgeFavoriteArticleService
				.removeFavorite(knowledgeFavoriteArticle);
	}

	/**
	 * 
	 * @Description: 我的收藏
	 * @param request
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/myFavotite")
	@ResponseBody
	public Map<String, Object> myFavorites(HttpServletRequest request) {
		return knowledgeFavoriteArticleService.MyFavoriteArticles(
				getUserId(request), getGoupType(request));
	}

	/**
	 * 
	 * @Description: 我的收藏
	 * @param request
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/hotFavotite")
	@ResponseBody
	public Map<String, Object> hotFavorites(HttpServletRequest request) {
		return knowledgeFavoriteArticleService.hotFavoriteArticles(
				getGoupType(request), getUserId(request));
	}

	/**
	 * 
	 * @Description: 商品列表初始化页面
	 * @param request
	 * @param response
	 * @param type
	 * @param categoryId
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/initList", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest request,
			HttpServletResponse response, Long categoryId, String keyWord) {
		ModelAndView modelAndView = new ModelAndView("/favorite/knowledgeList");
		return modelAndView;
	}

	@RequestMapping("/queryFavotites")
	@ResponseBody
	public Map<String, Object> queryFavorites(HttpServletRequest request,
			KnowledgeFavoriteArticle knowledgeFavoriteArticle,
			DataGridModel dataGridModel) {
		knowledgeFavoriteArticle.setCreateUser(getUserId(request));
		knowledgeFavoriteArticle.setDeptType(getGoupType(request));
		return knowledgeFavoriteArticleService.query(knowledgeFavoriteArticle,
				dataGridModel);
	}
}
