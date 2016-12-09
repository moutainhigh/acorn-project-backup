/*
 * @(#)NamesDao.java 1.0 2013-1-22下午2:21:44
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.UsrTaskRecommend;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizy
 * @version 1.0
 * @since 2013-1-22 下午2:21:44
 * 
 */
public interface UsrTaskRecommendDao extends
		GenericDao<UsrTaskRecommend, java.lang.Long> {

	public int updateProductId(Integer id, String productId, String usr);
	
	UsrTaskRecommend getRecommendByInstId(String instId);

}
