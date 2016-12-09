/*
 * @(#)ShipmentRefundService.java 1.0 2013-3-28上午10:40:35
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.oms.dto.OrderRefundDto;
import com.chinadrtv.erp.oms.dto.OrderdetRefundDto;
import com.chinadrtv.erp.oms.dto.OrderhistRefundDto;
import com.chinadrtv.erp.oms.dto.RefundSearchDto;
import com.chinadrtv.erp.oms.dto.ShipmentRefundDto;

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
 * @since 2013-3-28 上午10:40:35 
 * 
 */
public interface ShipmentRefundService extends GenericService<ShipmentRefund, Long> {

	/**
	 * 查询当前用户已检验数量
	* @return Map<String,Object>
	* @throws
	*/ 
	Map<String, Object> queryCheckCount(String entityId);

	/**
	* @param mailId
	* @param shipmentId
	* @return Map<String,Object>
	*/ 
	Map<String, Object> queryRefundList(String mailId, String shipmentId, String entityId);



	/**
	 * <p>半拒收退货确认查询</p>
	 * @param shipmentRefund
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryRefundSendList(ShipmentRefundDto srDto);


	/**
	 * <p>导出半拒收退货确认列表 </p>
	 * @param srDto
	 * @return HSSFWorkbook
	 */
	HSSFWorkbook exportRefundSendList(String ids);
	

	/**
	 * <p>生成退包入库单</p>
	 * @param ids
	 * @return
	 */
	public String generateRefundList(ShipmentRefundDto srDto);



	/*=========================================================================================*/


	

    //start by xzk
    void addShipmentRefund(OrderRefundDto orderRefundDto);
    void updateShipmentRefund(ShipmentRefund shipmentRefund);
    ShipmentRefund getShipmentRefund(Long id);

    List<OrderhistRefundDto> findContactOrderhist(RefundSearchDto refundSearchDto);
    List<OrderdetRefundDto> findRefundOrderdet(String orderId);

}
