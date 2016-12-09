/*
 * @(#)MemberLevelService.java 1.0 2013-5-23下午1:44:15
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.MemberLevel;

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
 * @since 2013-5-23 下午1:44:15 
 * 
 */
public interface MemberLevelService extends GenericService<MemberLevel, String> {

	/**
	 * <p>查询所有的MemberLevel</p>
	 * @return List<MemberLevel>
	 */
	List<MemberLevel> queryAll();
	/**
	 * 查询所有的MemberLevel
	 * @return Map,key:级别,value:memberLevel对象 
	 */
	Map<String,MemberLevel> queryAlltoMap();
	

}


