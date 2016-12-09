/*
 * @(#)LuceneSearchService.java 1.0 2013-12-26下午4:14:54
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.service;

import java.util.Map;

import com.chinadrtv.erp.knowledge.util.DataModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-26 下午4:14:54
 * 
 */
public interface LuceneSearchService {

	public Map<String, Object> list(String filed, DataModel dataModel,
			String depType, String searchType);

}
