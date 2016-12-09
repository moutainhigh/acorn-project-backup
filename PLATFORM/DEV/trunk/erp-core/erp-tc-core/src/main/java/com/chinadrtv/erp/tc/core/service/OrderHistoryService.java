/*
 * @(#)OrderHistoryService.java 1.0 2013-1-30上午10:51:56
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.service;

import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.model.agent.OrderDetail;
import com.chinadrtv.erp.model.agent.OrderHistory;

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
 * @since 2013-1-30 上午10:51:56 
 * 
 */
public interface OrderHistoryService extends GenericService<OrderHistory, Long> {


	/**
	 * 插入快照表
	* @Description: 
	* @param oh
	* @return void
	* @throws
	*/
    OrderHistory insertTcHistory(Order oh);
	
	/**
	 * 插入快照表
	* @Description: 
	* @param orderhist
	* @param orderdetList
	* @return void
	* @throws
	 */
    OrderHistory insertTcHistory(Order orderhist, List<OrderDetail> orderdetList);

	/**
	 * 根据订单业务号和业务版本号获取订单历史
	* @Description: 
	* @param orderid
	* @param revision
	* @return
	* @return OrderHistory
	* @throws
	*/ 
	OrderHistory getByOrderIdAndVersion(String orderid, Integer revision);

}
