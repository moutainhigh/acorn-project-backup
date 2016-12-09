/*
 * @(#)LeadInteractionOrderService.java 1.0 2013-7-3下午3:46:02
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

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
 * @since 2013-7-3 下午3:46:02 
 * 
 */
public interface LeadInteractionOrderService extends GenericService<LeadInteractionOrder, Long> {

	/**
	 * <p>查询发送历史</p>
	 * @param contactId
	 * @return Map<String, Object>
	 */
	Map<String, Object> querySendHistory(String contactId, DataGridModel dataGridModel);
	
	/**
	 * 查询所成单所在的营销计划
	 * queryCampaignByOrderId
	 * @param orderId
	 * @return 
	 * String
	 * @exception 
	 * @since  1.0.0
	 */
	String queryCampaignByOrderId(String orderId);
	
	List<Map<String, Object>> queryLeadCampaignByOrderId(String orderId);

}
