/*
 * @(#)ShipmentRefundDetailService.java 1.0 2013-4-1下午1:48:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service;

import java.util.List;

import com.chinadrtv.erp.core.service.GenericService;
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
 * @since 2013-4-1 下午1:48:28 
 * 
 */
public interface ShipmentRefundDetailService extends GenericService<ShipmentRefundDetail, Long> {

	/**
	 * 批量保存
	* @Description: 
	* @param dataList
	* @return void
	* @throws
	*/ 
	void saveBatch(List<ShipmentRefundDetail> dataList, String rejectCode, ShipmentRefund sr);

	/**
	 * 删除退包
	* @Description: 
	* @param sr
	* @return void
	* @throws
	*/ 
	void deleteRefund(ShipmentRefund sr);

}
