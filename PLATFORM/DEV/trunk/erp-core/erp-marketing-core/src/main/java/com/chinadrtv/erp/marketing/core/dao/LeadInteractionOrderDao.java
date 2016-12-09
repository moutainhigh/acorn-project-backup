/*
 * @(#)LeadInteractionOrderDao.java 1.0 2013-7-3下午3:41:46
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.dao;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.marketing.LeadInteractionOrder;

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
 * @since 2013-7-3 下午3:41:46 
 * 
 */
public interface LeadInteractionOrderDao extends GenericDao<LeadInteractionOrder, Long> {
	String queryCampaignByOrderId(String orderId);
	List<Map<String, Object>> queryLeadCampaignByOrderId(String orderId);
}
