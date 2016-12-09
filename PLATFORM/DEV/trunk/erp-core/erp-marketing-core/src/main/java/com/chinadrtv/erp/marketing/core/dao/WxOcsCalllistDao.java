/*
 * @(#)WxOcsCalllistDao.java 1.0 2013-12-16下午2:10:08
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.WxOcsCalllist;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-12-16 下午2:10:08
 * 
 */
public interface WxOcsCalllistDao extends
		GenericDao<WxOcsCalllist, java.lang.Integer> {
	/**
	 * 
	 * @Description: 批量插入
	 * @param list
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Integer batchInsert(List<Map<String, Object>> list);

}
