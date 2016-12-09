/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service;

import com.chinadrtv.erp.marketing.core.exception.MarketingException;
import com.chinadrtv.erp.sales.dto.AssignHistTask;
import com.chinadrtv.erp.user.model.AgentUser;

/**
 * 2013-7-10 下午3:04:41
 * @version 1.0.0
 * @author yangfei
 *
 */
public interface SalesTaskService {
	void tryAssignHist(AgentUser user, String jobId,AssignHistTask assignHistTask) throws MarketingException;
	
	void assignHist(AgentUser user, String jobId, AssignHistTask assignHistTask);
}
