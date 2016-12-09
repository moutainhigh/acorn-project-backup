package com.chinadrtv.erp.user.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.security.FieldPermission;

/**
 * User: zhaizhanyi
 * Date: 12-11-20
 */
public interface FieldPermissionDao extends GenericDao<FieldPermission,Long>{
	
	public FieldPermission getFieldPermission(String roleName,String url,String fieldName);
}
