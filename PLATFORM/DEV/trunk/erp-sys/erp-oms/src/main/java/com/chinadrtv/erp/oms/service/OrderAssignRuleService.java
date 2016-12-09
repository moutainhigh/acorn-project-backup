/*
 * @(#)OmsDeliveryRegulationService.java 1.0 2013-3-25上午10:59:48
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.service;

import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.OrderAssignRule;
import com.chinadrtv.erp.oms.common.DataGridModel;
import com.chinadrtv.erp.oms.dto.OrderAssignRuleDto;

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
 * @since 2013-3-25 上午10:59:48 
 * 
 */
public interface OrderAssignRuleService extends GenericService<OrderAssignRule, Long>{

	
	public void releaseMemcached();
	
	/**
	 * 分页查询
	* @Description: 
	* @param omsDeliveryRegulation
	* @param dataGridModel
	* @return
	* @return Map<String,Object>
	* @throws
	*/ 
	Map<String, Object>  queryPageList(OrderAssignRuleDto oar, DataGridModel dateModel);

	/**
	 * 启用禁用
	* @Description: 
	* @param oar
	* @return void
	* @throws
	*/ 
	void changeStatus(OrderAssignRule oar);
	

}
