/*
 * @(#)ProvinceDao.java 1.0 2013年10月31日上午10:21:24
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.dal.iagent.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.iagent.Province;

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
 * @since 2013年10月31日 上午10:21:24 
 * 
 */
public interface ProvinceDao extends BaseDao<Province> {

	/**
	 * <p></p>
	 * @param provinceid
	 * @return
	 */
	Province queryById(String provinceId);

}
