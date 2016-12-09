/*
 * @(#)KnowledgeController.java 1.0 2013-11-5下午4:02:16
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.dto.KnowledgeArticleDto;
import com.chinadrtv.erp.knowledge.model.KnowledgeArticle;
import com.chinadrtv.erp.knowledge.model.KnowledgeFavoriteArticle;
import com.chinadrtv.erp.knowledge.service.KnowledgeArticleService;
import com.chinadrtv.erp.knowledge.service.KnowledgeFavoriteArticleService;
import com.chinadrtv.erp.knowledge.service.KnowledgeRelationshipService;
import com.chinadrtv.erp.util.StringUtil;
import com.google.gson.Gson;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-5 下午4:02:16
 * 
 */
@Controller
@RequestMapping("/knowledge")
public class KnowledgeController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(KnowledgeController.class);
	@Autowired
	private KnowledgeArticleService knowledgeArticleService;
	@Autowired
	private KnowledgeFavoriteArticleService knowledgeFavoriteArticleService;
	@Autowired
	private KnowledgeRelationshipService knowledgeRelationshipService;

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response, Long id, Long categoryId, String type) {
		ModelAndView modelAndView = new ModelAndView("/knowledge/tabWrap");
		if (id != null) {
			KnowledgeArticle knowledgeArticle = knowledgeArticleService
					.getById(id);
			if (knowledgeArticle.getType().equals("1")) {
				modelAndView.addObject("title",
						knowledgeArticle.getProductName());
			} else {
				modelAndView.addObject("title", knowledgeArticle.getTitle());
			}
			modelAndView.addObject("id", id);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/initw", method = RequestMethod.GET)
	public ModelAndView initw(HttpServletRequest request,
			HttpServletResponse response, Long id, Long categoryId, String type) {
		ModelAndView modelAndView = new ModelAndView("/knowledge/init");
		KnowledgeArticle knowledgeArticle = null;
		KnowledgeFavoriteArticle knowledgeFavoriteArticle = new KnowledgeFavoriteArticle();
		knowledgeFavoriteArticle.setArticleId(id);
		knowledgeFavoriteArticle.setCreateUser(getUserId(request));
		knowledgeFavoriteArticle.setDeptType(getGoupType(request));
		Gson gson = new Gson();
		// 查看知识点
		if (id != null) {
			knowledgeArticle = knowledgeArticleService.getById(id);

			KnowledgeFavoriteArticle knowledgeFavoriteArticle2 = knowledgeFavoriteArticleService
					.getByArticleId(knowledgeFavoriteArticle);

			modelAndView.addObject("knowledge", knowledgeArticle);
			List<KnowledgeArticleDto> list = knowledgeRelationshipService
					.query(knowledgeArticle.getRelationshipIds(),
							knowledgeArticle.getId());
			if (!list.isEmpty()) {
				modelAndView.addObject("flag", "1");
			} else {
				modelAndView.addObject("flag", "0");

			}

			modelAndView.addObject("knowledgeDto", list);
		} else {
			// 新增知识点
			if (categoryId != null && type != null && !"".equals(type)) {
				knowledgeArticle = new KnowledgeArticle();
				// 设置目录节点和类型
				knowledgeArticle.setCategoryId(categoryId);
				knowledgeArticle.setType(type);
				modelAndView.addObject("knowledge", knowledgeArticle);
				modelAndView.addObject("knowledgeFavoriteArticle",
						knowledgeFavoriteArticle);
			}
		}

		return modelAndView;
	}

	@RequestMapping(value = "/getArticle", method = RequestMethod.POST)
	@ResponseBody
	public List<KnowledgeArticleDto> getArticle(Long id, String ids) {
		List<KnowledgeArticleDto> list = knowledgeRelationshipService.query(
				ids, id);

		return list;
	}

	/**
	 * 
	 * @Description: 新增或编辑知识点
	 * @param request
	 * @param response
	 * @param knowledgeArticle
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,
			HttpServletResponse response, KnowledgeArticle knowledgeArticle) {
		knowledgeArticle.setDepartType(getGoupType(request));
		return knowledgeArticleService.saveKnowledgeArticle(knowledgeArticle,
				request, getGoupType(request), getUserId(request));
	}

	/**
	 * 
	 * @Description: 查询商品
	 * @param request
	 * @param response
	 * @param productCode
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "/queryByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByCode(HttpServletRequest request,
			HttpServletResponse response, KnowledgeArticle knowledgeArticle) {
		Map<String, Object> result = new HashMap<String, Object>();
		knowledgeArticle.setDepartType(getGoupType(request));
		if (knowledgeArticleService.getByProductCode(knowledgeArticle) != null) {
			result.put("flag", "1");
		} else {
			result.put("flag", "2");
		}
		;
		return result;
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
		ModelAndView modelAndView = new ModelAndView("/knowledge/knowledgeList");
		try {
			if (!StringUtil.isNullOrBank(keyWord)) {
				keyWord = java.net.URLDecoder.decode(keyWord, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		modelAndView.addObject("categoryId", categoryId);
		modelAndView.addObject("keyWord", keyWord);
		return modelAndView;
	}

	/**
	 * 
	 * @Description: 知识点分页
	 * @param request
	 * @param response
	 * @param dataGridModel
	 * @param categoryId
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "/knowledgeList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> knowledgeList(HttpServletRequest request,
			HttpServletResponse response, DataGridModel dataGridModel,
			Long categoryId, String keyWord) {
		Map<String, Object> result = new HashMap<String, Object>();
		KnowledgeArticle knowledgeArticle = new KnowledgeArticle();
		if (keyWord != null) {
			knowledgeArticle.setProductName(keyWord);
			knowledgeArticle.setShortPinyin(keyWord);
			knowledgeArticle.setTitle(keyWord);
			knowledgeArticle.setProductCode(keyWord);
		}
		knowledgeArticle.setCategoryId(categoryId);
		knowledgeArticle.setDepartType(getGoupType(request));
		result = knowledgeArticleService.query(knowledgeArticle, dataGridModel);
		return result;
	}

	/**
	 * 
	 * @Description: 删除知识点
	 * @param request
	 * @param response
	 * @param knowledgeArticle
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping(value = "/deleteknowledge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteKnowledge(HttpServletRequest request,
			HttpServletResponse response, KnowledgeArticle knowledgeArticle) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		knowledgeArticle.setDepartType(getGoupType(request).toUpperCase());
		Boolean flag = knowledgeArticleService
				.deleteKnowledgeArticle(knowledgeArticle);
		resultMap.put("result", flag);
		return resultMap;

	}

}
