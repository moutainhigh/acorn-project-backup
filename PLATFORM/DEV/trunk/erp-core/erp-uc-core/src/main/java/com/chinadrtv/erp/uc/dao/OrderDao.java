/*
 * @(#)OrderDao.java 1.0 2013-5-7下午1:17:42
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import java.math.BigDecimal;
import java.util.List;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Order;

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
 * @since 2013-5-7 下午1:17:42 
 * 
 */
public interface OrderDao extends GenericDao<Order, Long> {

	/**
	 * <p>查询联系人是否有未出库订单</p>
	 * @param contactId
	 * @return List<Order>
	 */
	List<Order> querySpecialOrder(String contactId);
	
	/**
	 * <p>查询地址是否有未出库订单</p>
	 * @param addressId
	 * @return List<Order>
	 */
	List<Order> haveExWarehouseOrderByAddress(String addressId);

	/**
	 * <p>查询该联系人是否有未出库订单</p>
	 * @param contactId
	 * @return Integer
	 */
	int queryOrderCountByContactId(String contactId);

	/**
	 * <p>查询联系人的订单</p>
	 * @param dataGridModel
	 * @param contactId
	 * @return List<Order> 
	 */
	List<Order> queryOrderListByContactId(DataGridModel dataGridModel, String contactId);

	/**
	 * <p>根据订单号查询订单</p>
	 * @param orderId
	 * @return Order
	 */
	Order queryByOrderId(String orderId);

	/**
	 * <p>API-UC-38.查询客户历史成单的座席是否当前座席</p>
	 * @param contactId
	 * @return List<String>
	 */
	List<String> queryOrderCreaterByContact(String contactId);

    /**
     *
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
//    List<Order> findByCreateDateRange(String minOrderCreateDate, String maxOrderCreateDate);

    /**
     * 计算配送黑名单
     * @param contactId
     * @return
     */
//    BigDecimal calculateOrderTransferBlackList(String contactId);
}
