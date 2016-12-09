/*
 * @(#)DiscourseApiTest.java 1.0 2013-5-20上午10:57:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.service;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.JunitTestBase;
import com.chinadrtv.erp.marketing.core.service.DiscourseApiService;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-20 上午10:57:19
 * 
 */
public class DiscourseApiTest extends JunitTestBase {

	@Autowired
	private DiscourseApiService discourseApiService;

	/**
	 * 话术查询
	 * 
	 * @Description: TODO
	 * @return void
	 * @throws
	 */
	@Test
	public void Discoursetest() {
		DataGridModel dataModel = new DataGridModel();
		dataModel.setStartRow(1);
		Map<String, Object> map = discourseApiService.findDiscoursePageList(
				"10", "1130410000", dataModel);
		System.out.println("rows" + map.get("rows") + "total"
				+ map.get("total"));
	}
}
