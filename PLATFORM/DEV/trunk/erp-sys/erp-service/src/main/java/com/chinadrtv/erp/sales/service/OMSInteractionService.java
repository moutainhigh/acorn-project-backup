/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service;

import java.util.List;

import com.chinadrtv.erp.exception.ErpException;
import com.chinadrtv.erp.sales.dto.OrderInfo4AssistantReview;

/**
 * 2013-8-21 上午10:21:44
 * @version 1.0.0
 * @author yangfei
 *
 */
public interface OMSInteractionService {
	/**
	 * 
	 * queryDistributionChangeQuery
	 * @param startDate
	 * @param endDate
	 * @param isEms
	 * @param comment
	 * @param description 
	 * 订单号，订购时间，客户地址（三级），仓库，默认送货公司，指派送货公司
	 * @exception 
	 * @since  1.0.0
	 */
	List<OrderInfo4AssistantReview> viewDistributionChangeQuery(String startDate, String endDate, boolean isEms, int start , int end);
	
	/**
	 * 物流 助理 返回处理结果
	 * action
	 * @param actionType
	 * @param instId
	 * @param orderId
	 * @param comapny 物流助理指定的送货公司
	 * @return 
	 * boolean
	 * @throws ErpException 
	 * @throws Exception 
	 * @exception 
	 * @since  1.0.0
	 */
	boolean action(int actionType, String instId, String orderId, String company) throws ErpException, Exception;
	
	
}
