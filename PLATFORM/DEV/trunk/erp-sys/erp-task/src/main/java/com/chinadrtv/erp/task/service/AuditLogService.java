package com.chinadrtv.erp.task.service;


import com.chinadrtv.erp.task.core.service.BaseService;
import com.chinadrtv.erp.task.entity.AuditLog;

/**
 * User: liuhaidong
 * Date: 12-8-10
 */
public interface AuditLogService extends BaseService<AuditLog, Long>{

    void addAuditLog(AuditLog auditLog);

}
