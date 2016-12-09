package com.chinadrtv.erp.core.service;


import com.chinadrtv.erp.core.model.AuditLog;

import java.util.Date;
import java.util.List;

/**
 * User: liuhaidong
 * Date: 12-8-10
 */
public interface AuditLogService {

    void addAuditLog(AuditLog auditLog);

    int getAuditLogCountByAppDate(String appName, String funcName,String treadid, Date beginDate, Date endDate);

    List<AuditLog> searchPaginatedAuditLogByAppDate(String appName, String funcName,String treadid, Date beginDate, Date endDate,
                                                    int startIndex, Integer numPerPage);

    void saveAuditLog(AuditLog auditLog);

    void delAuditLog(Long id);
}
