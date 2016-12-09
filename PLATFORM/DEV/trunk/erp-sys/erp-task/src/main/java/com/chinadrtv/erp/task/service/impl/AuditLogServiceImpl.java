package com.chinadrtv.erp.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.core.service.BaseServiceImpl;
import com.chinadrtv.erp.task.dao.oms.AuditLogDao;
import com.chinadrtv.erp.task.entity.AuditLog;
import com.chinadrtv.erp.task.service.AuditLogService;

@Service
public class AuditLogServiceImpl extends BaseServiceImpl<AuditLog, Long> implements AuditLogService{

	@Autowired
	private AuditLogDao auditLogDao;
	
	@Override
	public BaseRepository<AuditLog, Long> getDao() {
		return auditLogDao;
	}

	@Override
	public void addAuditLog(AuditLog auditLog) {
		auditLogDao.save(auditLog);
	}

}
