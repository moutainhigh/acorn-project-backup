/*
 * @(#)OrderDeliveryService.java 1.0 2013-6-13下午2:23:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sales.core.service;

import java.util.Map;

import com.chinadrtv.erp.exception.ErpException;

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
 * @since 2013-6-13 下午2:23:08 
 * 
 */
public interface OrderDeliveryService {

	/**
	 * <p>匹配承运商、仓库、配送时效</p>
	 * @param orderId
	 * @return Map<String, Object>
	 */
	public Map<String, Object> matchDelivery(String orderId);

	/**
	 * <p>指定EMS送货</p>
	 * @param orderId
	 * @param remark
	 */
	public Integer assignEms(String orderId, String remark) throws ErpException;
}
