/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.marketing.Lead;

/**
 * 2013-11-19 下午1:02:33
 * @version 1.0.0
 * @author yangfei
 *
 */
public interface TransactionService {
	public String saveAndUpdate(Lead lead) throws ServiceException;
}
