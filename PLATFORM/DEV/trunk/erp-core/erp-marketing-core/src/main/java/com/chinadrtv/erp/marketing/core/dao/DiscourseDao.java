/*
 * @(#)DiscourseDao.java 1.0 2013-4-8上午9:37:38
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;

import com.chinadrtv.erp.marketing.core.dto.DiscourseDto;
import com.chinadrtv.erp.model.marketing.Discourse;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-4-8 上午9:37:38
 * 
 */
public interface DiscourseDao {

	public List<DiscourseDto> query(DiscourseDto discourse,
			DataGridModel dataModel);

	public Integer queryCounts(DiscourseDto discourse);

	public String saveDiscourse(Discourse discourse);

	public Discourse getById(Long id);

	public Boolean update(Discourse discourse);

	public List<Discourse> queryByProductCode(String product, String department);

	public Discourse queryByProductCode(String productCode);

}
