/*
 * @(#)ProvinceMappingDao.java 1.0 2013年10月30日下午1:42:43
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.dal.oms.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.oms.ProvinceMapping;

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
 * @since 2013年10月30日 下午1:42:43 
 * 
 */
public interface ProvinceMappingDao extends BaseDao<ProvinceMapping> {

	/**
	 * <p></p>
	 * @param provinceId
	 * @return
	 */
	ProvinceMapping findById(int provinceId);

	/**
	 * <p></p>
	 * @param province
	 * @return
	 */
	ProvinceMapping findByName(String province);

}
