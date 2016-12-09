package com.chinadrtv.erp.core.service.impl;

import com.chinadrtv.erp.core.dao.AuditLogDao;
import com.chinadrtv.erp.core.model.AuditLog;
import com.chinadrtv.erp.core.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User: liuhaidong
 * Date: 12-8-10
 */
@Service("auditLogService")
public class AuditLogServiceImpl implements AuditLogService {
    @Autowired
    private AuditLogDao auditLogDao;


    public void addAuditLog(AuditLog auditLog) {
        auditLogDao.save(auditLog);
    }


    public int getAuditLogCountByAppDate(String appName, String funcName,String treadid ,Date beginDate, Date endDate) {
        return auditLogDao.getAuditLogCountByAppDate(appName,funcName,treadid,beginDate,endDate);
    }


    public List<AuditLog> searchPaginatedAuditLogByAppDate(String appName, String funcName, String treadid,Date beginDate, Date endDate, int startIndex, Integer numPerPage) {
        return auditLogDao.searchPaginatedAuditLogByAppDate(appName,funcName,treadid,beginDate,endDate,startIndex,numPerPage);
    }

    public void saveAuditLog(AuditLog auditLog) {
        auditLogDao.saveOrUpdate(auditLog);
    }

    public void delAuditLog(Long id) {
        auditLogDao.remove(id);
    }
}
