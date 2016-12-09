/*
 * @(#)PlubasInfoService.java 1.0 2014-10-17上午10:32:03
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms;

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
 * @since 2014-10-17 上午10:32:03 
 * 
 */
public interface PlubasInfoService {
	/**
	 * <p></p>
	 * @param skuCode
	 * @return
	 */
	Boolean checkSkuCode(String skuCode);

	/**
	 * <p></p>
	 * @param outSkuId
	 * @return
	 */
	String getSkuTitle(String outSkuId);
}
