/*
 * @(#)OrderhistTrackTaskDao.java 1.0 2013年12月24日下午3:28:33
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.OrderhistTrackTask;
import com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto;

import java.util.List;

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
 * @since 2013年12月24日 下午3:28:33 
 * 
 */
public interface OrderhistTrackTaskDao extends GenericDao<OrderhistTrackTask, Long> {

	/**
	 * <p></p>
	 * @param orderId
	 * @return OrderhistTrackTask
	 */
	OrderhistTrackTask queryByOrderId(String orderId);

	/**
	 * <p></p>
	 * @param orderhistTrackTaskDto
	 * @return
	 */
	Integer queryPageCount(OrderhistTrackTaskDto orderhistTrackTaskDto);

	/**
	 * <p></p>
	 * @param dataGridModel
	 * @param orderhistTrackTaskDto
	 * @return
	 */
	List<OrderhistTrackTaskDto> queryPageList(DataGridModel dataGridModel, OrderhistTrackTaskDto orderhistTrackTaskDto);


    List<OrderhistTrackTask> queryByOrderIds(List<String> orderIdList);

}
