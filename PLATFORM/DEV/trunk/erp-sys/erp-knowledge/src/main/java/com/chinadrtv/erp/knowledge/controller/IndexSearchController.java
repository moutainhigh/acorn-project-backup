/*
 * @(#)IndexSearch.java 1.0 2013-12-26上午10:35:08
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.knowledge.service.LuceneIndexService;
import com.chinadrtv.erp.knowledge.service.LuceneSearchService;
import com.chinadrtv.erp.knowledge.util.DataModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-26 上午10:35:08
 * 
 */
@Controller
@RequestMapping("/search")
public class IndexSearchController extends BaseController {
	@Autowired
	private LuceneIndexService luceneIndexService;
	@Autowired
	private LuceneSearchService luceneSearchService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView initList(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("/search/search");
		return modelAndView;

	}

	@ResponseBody
	@RequestMapping(value = "/searchList", method = RequestMethod.POST)
	public Map<String, Object> list(HttpServletRequest request,
			HttpServletResponse response, DataModel dataModel, String filed,
			String searchType) {
		return luceneSearchService.list(filed, dataModel, getGoupType(request),
				searchType);
	}

	/**
	 * 
	 * @Description: 手动创建索引
	 * @param request
	 * @param response
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "/indexNew", method = RequestMethod.GET)
	public String indexNew(HttpServletRequest request,
			HttpServletResponse response) {
		luceneIndexService.index("in", "1");
		luceneIndexService.index("in", "2");
		luceneIndexService.index("out", "1");
		luceneIndexService.index("out", "2");
		return "123";

	}

}
