/*
 * @(#)CountyAllService.java 1.0 2013-6-7上午10:32:09
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
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
 * @since 2013-6-7 上午10:32:09 
 * 
 */
public interface CountyAllService extends GenericService<CountyAll, Integer> {

	/**
	 * <p>API-UC-43. 查询邮政编码</p>
	 * @param countyId
	 * @return String
	 */
	String queryPostcode(Integer countyId);
	
	/**
	 * <p>API-UC-44.查询区简码对应第三级地址</p>
	 * @param countyCode
	 * @param countyName
	 * @param areaCode
	 * @return List<CountyAll>
	 */
	List<CountyAll> queryAreaList(String countyCode, String countyName, String areaCode);
	
	/**
	 * <p>API-UC-45.查询区地址对应省和市</p>
	 * @param countyId
	 * @return CountyAll
	 */
	CountyAll queryByCountyId(Integer countyId);
}
