/*
 * @(#)OrderUrgentApplicationDao.java 1.0 2013-7-10下午3:52:12
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.core.dao.GenericDao;
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
 * @since 2013-7-10 下午3:52:12 
 * 
 */
public interface OrderUrgentApplicationDao extends GenericDao<OrderUrgentApplication, Long> {

	/**
	 * <p>查询总行数 </p>
	 * @param dto
	 * @param dataModel
	 * @return Integer
	 */
	Integer queryPageCount(UrgentOrderDto dto, DataGridModel dataModel);

	/**
	 * <p>查询分页数据</p>
	 * @param dto
	 * @param dataModel
	 * @return List<UrgentOrderDto>
	 */
	List<Map<String, Object>> queryPageList(UrgentOrderDto dto, DataGridModel dataModel);
	
	/**
	 * <p>Dto适配器</p>
	 * @param rowMap
	 * @return UrgentOrderDto
	 */
	UrgentOrderDto dtoAdaptor(Map<String, Object> rowMap);

	/**
	 * <p>查询最近的催送单</p>
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
	List<OrderUrgentApplication> queryFinishedUrgentOrder(String agentId, Date beginDate, Date endDate);


}
