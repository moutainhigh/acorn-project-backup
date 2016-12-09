/*
 * @(#)ShipmentHeaderDao.java 1.0 2013-6-17下午3:04:31
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

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
 * @since 2013-6-17 下午3:04:31 
 * 
 */
public interface ShipmentHeaderDao extends GenericDao<ShipmentHeader, Long> {

	ShipmentHeader queryShipmentHeaderByOrderId(String orderId);
}
