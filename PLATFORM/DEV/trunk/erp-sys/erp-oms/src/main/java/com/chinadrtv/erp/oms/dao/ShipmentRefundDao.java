/*
 * @(#)ShipmentRefundDao.java 1.0 2013-3-28上午10:35:22
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentRefund;
import com.chinadrtv.erp.oms.dto.ShipmentRefundDto;
import com.chinadrtv.erp.user.model.AgentUser;

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
 * @since 2013-3-28 上午10:35:22 
 * 
 */
public interface ShipmentRefundDao extends GenericDao<ShipmentRefund, Long> {

	/**
	 * 根据mailId 与 shipmentId 查询
	* @param mailId
	* @param shipmentId
	* @return ShipmentRefund
	*/ 
	ShipmentRefund queryByParam(String mailId, String shipmentId, String entityId);

	/**
	* @param agentUser
	* @return Map<String,Object>
	*/ 
	Map<String, Object> queryCheckCount(String entityId, AgentUser agentUser);

	/**
	 * <p>半拒收退货确认查询</p>
	 * @param shipmentRefund
	 * @return List<ShipmentRefund>
	 */
	List<Map<String, Object>> queryRefundSendList(ShipmentRefundDto srDto);

	/**
	 * <p>导出列表</p>
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> exportRefundSendList(String ids);




	/*==============================================================================*/







    //start by xzk
    void addShipmentRefund(ShipmentRefund shipmentRefund);
    void updateShipmentRefund(ShipmentRefund shipmentRefund);
    ShipmentRefund getShipmentRefund(Long id);
    List<ShipmentRefund> getShipmentRefundFromOrderId(String orderId);


}
