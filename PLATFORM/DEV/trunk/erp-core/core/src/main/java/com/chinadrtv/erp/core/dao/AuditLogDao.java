package com.chinadrtv.erp.core.dao;


import com.chinadrtv.erp.core.model.AuditLog;
import com.chinadrtv.erp.core.dao.GenericDao;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 12-8-10
 * Time: 上午10:12
 * To change this template use File | Settings | File Templates.
 */
public interface AuditLogDao extends GenericDao<AuditLog, Long> {
    public int getAuditLogCountByAppDate(String appName, String funcName, String treadid,Date beginDate, Date endDate);

    public List<AuditLog> searchPaginatedAuditLogByAppDate(String appName, String funcName,String treadid, Date beginDate, Date endDate,
                                                    int startIndex, Integer numPerPage);

    void saveOrUpdate(AuditLog auditLog);
}
