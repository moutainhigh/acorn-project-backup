/*
 * @(#)PreTradeItemService.java 1.0 2014-6-18下午1:21:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms;

import com.chinadrtv.model.oms.PreTradeItem;

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
 * @since 2014-6-18 下午1:21:28 
 * 
 */
public interface PreTradeItemService {

	public PreTradeItem queryByItemId(String itemId);
	
}
