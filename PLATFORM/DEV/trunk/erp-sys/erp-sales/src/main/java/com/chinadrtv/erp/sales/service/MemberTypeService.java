/*
 * @(#)MemberTypeService.java 1.0 2013-5-23下午1:41:34
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.service;

import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.MemberType;

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
 * @since 2013-5-23 下午1:41:34 
 * 
 */
public interface MemberTypeService extends GenericService<MemberType, String> {

	/**
	 * <p>查询所有MemberType</p>
	 * @return List<MemberType>
	 */
	List<MemberType> queryAll();

}
