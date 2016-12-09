package com.chinadrtv.erp.user.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.RolePermission;
import com.chinadrtv.erp.user.dao.RolePermissionDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("rolePermissionDao")
public class RolePermissionDaoImpl extends GenericDaoHibernate<RolePermission,Long> implements RolePermissionDao{
	
    public RolePermissionDaoImpl() {
        super(RolePermission.class);
    }

}
