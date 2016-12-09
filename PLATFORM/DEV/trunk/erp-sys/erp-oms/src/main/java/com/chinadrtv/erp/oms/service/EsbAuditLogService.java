package com.chinadrtv.erp.oms.service;

import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.model.EsbAuditLog;

/**
 * ESB错误日志服务
 *  
 * @author haoleitao
 * @date 2013-4-27 上午11:27:40
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface EsbAuditLogService {

	public int listCount(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt, Date edt);
	public List<EsbAuditLog> list(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt, Date edt,int index, int size);
	
}
