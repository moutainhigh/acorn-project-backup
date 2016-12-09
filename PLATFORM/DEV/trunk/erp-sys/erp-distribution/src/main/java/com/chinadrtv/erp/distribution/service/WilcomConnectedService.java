/*
 * @(#)WilcomConnectedService.java 1.0 2014年1月13日下午2:11:27
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.distribution.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.LeadInteractionSearchDto;

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
 * @since 2014年1月13日 下午2:11:27 
 * 
 */
public interface WilcomConnectedService {

	/**
	 * <p>查询IVR接通可分配数量</p>
	 * @param searchDto
	 * @return Integer
	 */
	Integer queryIvrConnectedAvaliableQty(LeadInteractionSearchDto searchDto);

	/**
	 * <p>分配接通（井星）类型到坐席</p>
	 * @param liDto
	 * @param param
	 * @return String
	 */
	Map<String, Object> assignToAgent(LeadInteractionSearchDto liDto, List<Map<String, Object>> agentUsers) throws Exception;
	
}
