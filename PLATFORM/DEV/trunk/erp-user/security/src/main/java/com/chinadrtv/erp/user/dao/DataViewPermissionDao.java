package com.chinadrtv.erp.user.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.security.DataViewPermission;

/**
 * User: zhaizhanyi
 * Date: 12-11-20
 */
public interface DataViewPermissionDao extends GenericDao<DataViewPermission,Long>{
	
	public DataViewPermission query(String viewUrl,String roleId);
}
