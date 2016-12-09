/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service;

import com.chinadrtv.erp.sales.dto.AuditInfoDto;

/**
 * 2013-6-25 上午9:35:41
 * @version 1.0.0
 * @author yangfei
 *
 */
public interface AuditTaskProcessService {
	
	boolean updateAuditTaskBatch(AuditInfoDto[] auditInfos) throws Exception;
}
