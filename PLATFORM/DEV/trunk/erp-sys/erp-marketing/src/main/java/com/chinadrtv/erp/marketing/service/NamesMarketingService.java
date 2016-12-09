/*
 * @(#)NamesService.java 1.0 2013-1-22下午2:20:10
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service;

import java.util.List;

import com.chinadrtv.erp.model.Names;

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
 * @since 2013-1-22 下午2:20:10 
 * 
 */
public interface NamesMarketingService {

	/**
	* @Description: TODO
	* @param uid
	* @return
	* @return List<Names>
	* @throws
	*/ 
	List<Names> queryNamesList();

}
