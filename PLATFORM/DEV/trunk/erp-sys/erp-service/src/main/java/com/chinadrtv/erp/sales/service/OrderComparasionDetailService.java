/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service;

import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.erp.marketing.core.exception.MarketingException;

/**
 * 2013-6-24 上午10:25:13
 * @version 1.0.0
 * @author yangfei
 *
 */
public interface OrderComparasionDetailService {
	public void instancializeOrderChange(ModelAndView mav, String batchId, boolean isAdd) throws MarketingException;
	
	public boolean isOrderExist(String orderId);
}
