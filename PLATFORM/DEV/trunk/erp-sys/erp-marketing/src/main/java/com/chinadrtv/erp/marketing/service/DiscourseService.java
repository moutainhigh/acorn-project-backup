/*
 * @(#)Discourse.java 1.0 2013-4-8上午10:41:15
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.DiscourseDto;
import com.chinadrtv.erp.model.marketing.Discourse;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-8 上午10:41:15
 * 
 */
public interface DiscourseService {
	public Map<String, Object> getDiscourseList(DiscourseDto discourseDto,
			DataGridModel dataModel);

	public String svaeDiscourse(Discourse discourse);

	public Discourse getDiscourseById(Long id);

	public Boolean updateDiscourse(Discourse discourse);

	public Map<String, Object> queryByProductCode(String product,
			String department);

	public List<Discourse> queryByProduct(String product, String department);

	public void deleteDiscourse(String ids);

}
