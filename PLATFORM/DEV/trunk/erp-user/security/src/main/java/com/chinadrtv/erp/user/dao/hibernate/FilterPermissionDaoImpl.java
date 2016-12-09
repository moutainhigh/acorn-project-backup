package com.chinadrtv.erp.user.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.FilterPermission;
import com.chinadrtv.erp.user.dao.FilterPermissionDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("filterPermissionDao")
public class FilterPermissionDaoImpl extends GenericDaoHibernate<FilterPermission,Long> implements FilterPermissionDao{
	
    public FilterPermissionDaoImpl() {
        super(FilterPermission.class);
    }

}
