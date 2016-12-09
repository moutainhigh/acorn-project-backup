/*
 * @(#)CountyAllDao.java 1.0 2013-6-7上午10:29:32
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.CountyAll;

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
 * @since 2013-6-7 上午10:29:32 
 * 
 */
public interface CountyAllDao extends GenericDao<CountyAll, Integer> {

	/**
	 * <p>API-UC-44.查询区简码对应第三级地址</p>
	 * @param countyCode
	 * @param countyName
	 * @param areaCode
	 * @return List<CountyAll>
	 */
	List<CountyAll> queryAreaList(String countyCode, String countyName, String areaCode);

}
