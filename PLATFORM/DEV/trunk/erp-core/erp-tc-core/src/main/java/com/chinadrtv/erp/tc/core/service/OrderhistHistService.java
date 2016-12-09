/*
 * @(#)OrderhistHistService.java 1.0 2013-1-28下午3:05:30
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderhistHist;

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
 * @since 2013-1-28 下午3:05:30 
 * 
 */
public interface OrderhistHistService extends GenericService<OrderhistHist, java.lang.Long> {
     void saveOrderhist(Order orderhist);
	void saveOrderhistHist(OrderhistHist orderhistHist);
}
