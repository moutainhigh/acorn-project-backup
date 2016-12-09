package com.chinadrtv.erp.oms.dao;


import java.util.Date;
import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.EsbAuditLog;

/**
 * 
 * @author haoleitao
 * @date 2013-2-20 上午9:49:16
 *
 */
public interface EsbAuditLogDao extends GenericDao<EsbAuditLog,Long> {
	public int listCount(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt, Date edt);
	public List<EsbAuditLog> list(String esbName,String esbDesc,String errorCode,String errorDesc,String companyId,String remark,Date sdt, Date edt,int index, int size);
}
