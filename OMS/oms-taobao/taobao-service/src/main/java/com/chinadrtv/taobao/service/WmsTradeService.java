/*
 * @(#)WmsTradeService.java 1.0 2014-7-21上午9:57:56
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.service;


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
 * @since 2014-7-21 上午9:57:56 
 * 
 */
public interface WmsTradeService {

	/**
	 * <p>Calculate remain stock of item.</p>
	 * @param outerId
	 * @return Long 
	 */
	Long calcItemStock(String itemId);
}
