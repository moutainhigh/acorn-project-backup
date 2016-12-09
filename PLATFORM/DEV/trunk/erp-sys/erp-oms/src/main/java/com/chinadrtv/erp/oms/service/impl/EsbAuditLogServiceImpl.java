package com.chinadrtv.erp.oms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.model.EsbAuditLog;
import com.chinadrtv.erp.oms.dao.EsbAuditLogDao;
import com.chinadrtv.erp.oms.service.EsbAuditLogService;

/**
 * 
 * Esb错误日志服务
 *  
 * @author haoleitao
 * @date 2013-2-20 上午9:56:57
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class EsbAuditLogServiceImpl implements EsbAuditLogService  {

	@Autowired
	private EsbAuditLogDao esbAuditLogDao;
	 
	public int listCount(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt, Date edt) {
		return esbAuditLogDao.listCount(esbName, esbDesc, errorCode, errorDesc, companyId, remark, sdt, edt);
	}
	
	public List<EsbAuditLog> list(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt, Date edt,int index, int size) {
		return esbAuditLogDao.list(esbName, esbDesc, errorCode, errorDesc, companyId, remark, sdt, edt, index, size);
	}

	
}
