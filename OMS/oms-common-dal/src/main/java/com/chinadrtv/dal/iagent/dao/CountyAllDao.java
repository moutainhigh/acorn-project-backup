/*
 * @(#)CountyAllDao.java 1.0 2013年10月31日上午10:20:38
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.dal.iagent.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.iagent.CountyAll;

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
 * @since 2013年10月31日 上午10:20:38 
 * 
 */
public interface CountyAllDao extends BaseDao<CountyAll> {

	/**
	 * <p></p>
	 * @param mappingCountyid
	 * @return
	 */
	CountyAll queryById(Integer countyId);

}
