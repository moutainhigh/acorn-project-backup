/*
 * @(#)OrderUrgentApplicationService.java 1.0 2013-7-10下午3:58:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.cntrpbank.OrderUrgentApplication;
import com.chinadrtv.erp.uc.dto.UrgentOrderDto;

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
 * @since 2013-7-10 下午3:58:08 
 * 
 */
public interface OrderUrgentApplicationService extends GenericService<OrderUrgentApplication, Long> {

	/**
	 * <p>分页查询订单催送货列表</p>
	 * @param dto
	 * @param dataModel
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryPageList(UrgentOrderDto dto, DataGridModel dataModel);

	/**
	 * <p>申请催送货</p>
	 * @param model
	 */
	void apply(OrderUrgentApplication model) throws Exception;
	
	/**
	 * <p>判断订单是否可以催送货</p>
	 * @param orderId
	 * @param id
	 * @return Boolean
	 */
	Boolean couldReApply(String orderId, Date appDate);

	/**
	 * <p>判断订单是否可以催送货</p>
	 * @param orderId
	 * @return Boolean
	 */
	Boolean couldReApply(String orderId);

	/**
	 * <p>获取订单最新的催送货回复</p>
	 * @param orderId
	 * @return OrderUrgentApplication
	 */
	OrderUrgentApplication queryLatest(String orderId);
	
	
	/**
	 * <p>按坐席和时间查询已完成的催送货订单</p>
	 * @param agentId
	 * @param beginDate
	 * @param endDate
	 * @return Model list
	 */
	List<OrderUrgentApplication> queryFinishedUrgentOrder(String agentId, Date beginDate, Date endDate) throws Exception;
}
