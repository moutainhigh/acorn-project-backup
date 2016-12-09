/*
 * @(#)OrderService.java 1.0 2013-5-7下午1:22:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.uc.dto.OrderDetailDto;

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
 * @since 2013-5-7 下午1:22:08 
 * 
 */
public interface OrderService extends GenericService<Order, Long> {

	/**
	 * <p>API-UC-12.查询客户主地址关联订单</p>
	 * @param addressId
	 * @return Boolean
	 */
	boolean haveExWarehouseOrder(String contactId);
	
	/**
	 * <p>查询地址是否有未出库订单</p>
	 * @param addressId
	 * @return Boolean
	 */
	boolean haveExWarehouseOrderByAddress(String addressId);
	
	
	/**
	 * <p>查询联系人未出库的订单</p>
	 * @param contactId
	 * @return List<Order>
	 */
	List<Order> queryExWarehouseOrder(String contactId);
	
	/**
	 * <p>API-UC-34.查询订购历史</p>
	 * @param dataGridModel
	 * @param contactId
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryOrderListByContactId(DataGridModel dataGridModel, String contactId);

	/**
	 * <p>根据订单号查询订单</p>
	 * @param orderId
	 * @return List<OrderDetail>
	 */
	List<OrderDetailDto> queryOrderDetail(String orderId);
	
	
	/**
	 * <p>根据联系人查询创建坐席</p>
	 * @param contactId
	 * @return List<String>
	 */
	List<String> queryOrderCreaterByContact(String contactId);
	
	/**
	 * 
	 * <p>API-UC-38.查询客户历史成单的座席是否当前座席</p>
	 * @param contactId
	 * @return Boolean
	 */
	Boolean isOrderCreator(String userId, String contactId);

    /**
     * 查找客户购买总金额
     * @param customerId
     * @return
     */
    BigDecimal queryTotalAmountByContactId(String customerId);

    /**
     * 查找创建时间在某个时间段内的所有订单
     * @param minOrderCreateDate
     * @param maxOrderCreateDate
     * @return
     */
//    List<Order> findByCreateDateRange(Date minOrderCreateDate, Date maxOrderCreateDate);

    /**
     * 计算配送黑名单
     * @param contactId
     * @return
     */
//    BigDecimal calculateOrderTransferBlackList(String contactId);

    /**
     * 计算该客户的下单数目
     * @param contactid
     * @return
     */
//    Integer queryOrderCountByContactId(String contactid);
}
