/*
 * @(#)RestController.java 1.0 2013-4-10下午1:59:18
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinadrtv.erp.marketing.service.DiscourseService;
import com.chinadrtv.erp.model.marketing.Discourse;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-10 下午1:59:18
 * 
 */
@RequestMapping("rest")
@Controller
public class RestController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(RestController.class);
	@Autowired
	private DiscourseService discourseService;

	/**
	 * 接口 根据产品id和部门号返回话术内容
	 * 
	 * @Description: TODO
	 * @param productId
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */

	@RequestMapping(value = "/getDiscourseContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getContent(@RequestBody Discourse discourse) {
		logger.info("调用话术接口 productcode" + discourse.getProductCode()
				+ "department" + discourse.getDepartmentCode());
		return discourseService.queryByProductCode(discourse.getProductCode(),
				discourse.getDepartmentCode());
	}

}
