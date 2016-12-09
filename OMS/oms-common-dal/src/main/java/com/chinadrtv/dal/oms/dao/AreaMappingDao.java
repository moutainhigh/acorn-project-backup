/*
 * @(#)AreaMappingDao.java 1.0 2013年10月30日下午1:41:17
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.dal.oms.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.oms.AreaMapping;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年10月30日 下午1:41:17 
 * 
 */
public interface AreaMappingDao extends BaseDao<AreaMapping>{

	/**
	 * <p></p>
	 * @param areaId
	 * @return
	 */
	AreaMapping findById(int areaId);

	/**
	 * <p></p>
	 * @param area
	 * @return
	 */
	AreaMapping findByName(String area);

}
