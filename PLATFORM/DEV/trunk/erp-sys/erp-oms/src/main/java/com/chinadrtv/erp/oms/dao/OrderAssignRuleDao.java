/*
 * @(#)DeliveryRegulationDao.java 1.0 2013-3-25上午10:38:17
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.oms.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
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
 * @since 2013-3-25 上午10:38:17 
 * 
 */
public interface OrderAssignRuleDao extends GenericDao<OrderAssignRule, Long>{

	/**
	 * 分布查询
	* @Description: 
	* @param string
	* @param paramMap
	* @return
	* @return List<OmsDeliveryRegulation>
	* @throws
	*/ 
	List<OrderAssignRule> queryPageList(OrderAssignRuleDto oar, DataGridModel dataModel);

	/**
	 * 分布查询数量
	* @Description: 
	* @param string
	* @param paramMap
	* @return
	* @return int
	* @throws
	*/ 
	int queryPageCount(OrderAssignRuleDto oar);

	/**
	 * 根据渠道与优先级查询List
	* @Description: 
	* @param id
	* @param priority
	* @return
	* @return List<OrderAssignRule>
	* @throws
	*/ 
	List<OrderAssignRule> queryCrossList(OrderAssignRule oar, Long channelId, Long priority);

}
