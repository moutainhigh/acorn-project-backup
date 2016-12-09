/*
 * @(#)ShipmentRefundDetailDao.java 1.0 2013-3-28上午10:35:51
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.model.trade.ShipmentRefundDetail;

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
 * @since 2013-3-28 上午10:35:51 
 * 
 */
public interface ShipmentRefundDetailDao extends GenericDao<ShipmentRefundDetail, Long> {

	/**
	* @Description: 
	* @param sr
	* @return
	* @return List<ShipmentRefundDetail>
	* @throws
	*/ 
	List<ShipmentRefundDetail> getDetailList(ShipmentRefund sr);

}
