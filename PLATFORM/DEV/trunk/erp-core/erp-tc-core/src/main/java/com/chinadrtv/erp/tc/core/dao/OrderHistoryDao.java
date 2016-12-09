/*
 * @(#)OrderHistoryDao.java 1.0 2013-1-30上午10:42:50
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
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
 * @since 2013-1-30 上午10:42:50 
 * 
 */
public interface OrderHistoryDao extends GenericDao<OrderHistory, Long> {

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
