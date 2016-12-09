/*
 * @(#)TaskService.java 1.0 2013年8月28日上午10:51:15
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.CampaignReceiverDto;

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
 * @since 2013年8月28日 上午10:51:15 
 * 
 */
public interface TaskAssignService {

	/**
	 * <p>根据组查询分配的坐席</p>
	 * @param groupList
	 * @return 
	 */
	List<Map<String, Object>> queryAgent(List<String> groupList) throws Exception;

	/**
	 * <p>分配到坐席</p>
	 * @param crDto
	 * @param param
	 * @param assignStrategy [cycle, order]
	 */
	void assignToAgent(CampaignReceiverDto crDto, List<Map<String, Object>> param, String assignStrategy) throws Exception;

}
