/*
 * @(#)KnowledgeRelationshipController.java 1.0 2014-1-7下午2:15:10
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.knowledge.model.KnowledgetTag;
import com.chinadrtv.erp.knowledge.service.KnowledgeRelationshipService;
import com.chinadrtv.erp.knowledge.service.KnowledgeTagService;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-7 下午2:15:10
 * 
 */
@Controller
@RequestMapping("knowledgeTag")
public class KnowledgeTagController extends BaseController {
	@Autowired
	private KnowledgeRelationshipService knowledgeRelationshipService;
	@Autowired
	private KnowledgeTagService knowledgeTagService;

	/***
	 * 
	 * @Description: 标签初始化
	 * @param request
	 * @param response
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("/knowledgeList")
	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView(
				"/knowledgeTag/knowledgeList");

		return modelAndView;

	}

	/**
	 * 
	 * @Description: 标签列表
	 * @param request
	 * @param response
	 * @param knowledgetTag
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response, KnowledgetTag knowledgetTag,
			DataGridModel dataGridModel) {
		Map<String, Object> result = new HashMap<String, Object>();
		return knowledgeTagService.query(knowledgetTag, dataGridModel);
	}

	/**
	 * 
	 * @Description: 查看标签
	 * @param request
	 * @param response
	 * @param knowledgetTag
	 * @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping("view")
	public ModelAndView viewOrEdit(HttpServletRequest request,
			HttpServletResponse response, KnowledgetTag knowledgetTag) {
		ModelAndView modelAndView = new ModelAndView("/knowledgeTag/view");
		if (knowledgetTag != null && knowledgetTag.getId() != null) {
			KnowledgetTag knowledgeTag = knowledgeTagService.view(knowledgetTag
					.getId());
			modelAndView.addObject("knowledgeTag", knowledgeTag);
		}

		return modelAndView;
	}

	/**
	 * 
	 * @Description: 保存标签
	 * @param request
	 * @param response
	 * @param knowledgetTag
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/saves")
	public Map<String, Object> save(HttpServletRequest request,
			HttpServletResponse response, KnowledgetTag knowledgetTag) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			knowledgetTag.setStatus("1");

			if (knowledgetTag.getId() != null) {
				KnowledgetTag knowledgeRelationship2 = knowledgeTagService
						.view(knowledgetTag.getId());
				knowledgetTag.setUpdateDate(new Date());
				knowledgetTag.setUpdateUser(getUserId(request));
				knowledgetTag.setCreateDate(knowledgeRelationship2
						.getCreateDate());
				knowledgetTag.setCreateUser(knowledgeRelationship2
						.getCreateUser());
			} else {
				knowledgetTag.setCreateDate(new Date());
				knowledgetTag.setCreateUser(getUserId(request));
			}
			knowledgeTagService.saves(knowledgetTag);
			result.put("result", "保存成功");
		} catch (Exception e) {
			result.put("result", "保存失败");
			e.printStackTrace();
			// TODO: handle exception
		}

		return result;

	}

	@ResponseBody
	@RequestMapping("/getList")
	public List<KnowledgetTag> getList(KnowledgetTag knowledgetTag) {
		return knowledgeTagService.list(knowledgetTag);

	}

	@ResponseBody
	@RequestMapping("/getLists")
	public List<KnowledgetTag> getLists(String ids) {
		return knowledgeTagService.getListById(ids);

	}

	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(String ids) {
		return knowledgeTagService.delete(ids);
	}

}
