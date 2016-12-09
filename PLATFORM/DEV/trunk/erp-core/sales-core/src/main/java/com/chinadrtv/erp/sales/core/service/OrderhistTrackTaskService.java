/*
 * @(#)OrderhistTrackTaskService.java 1.0 2013年12月24日下午3:38:56
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.OrderhistTrackTask;
import com.chinadrtv.erp.sales.core.dto.OrderhistTrackTaskDto;
import com.chinadrtv.erp.sales.core.model.OrderExtDto;

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
 * @since 2013年12月24日 下午3:38:56 
 * 
 */
public interface OrderhistTrackTaskService extends GenericService<OrderhistTrackTask, Long> {

	/**
	 * <p>指定跟单人</p>
	 * @param orderTrackTaskDto
	 */
	Integer assignToAgent(String[] orderIdArr, String trackUser, String ownerUser) throws Exception;

	/**
	 * <p>查询跟单列表</p>
	 * @param dataGridModel
	 * @param orderhistTrackTaskDto
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryPageList(DataGridModel dataGridModel, OrderhistTrackTaskDto orderhistTrackTaskDto);

}
