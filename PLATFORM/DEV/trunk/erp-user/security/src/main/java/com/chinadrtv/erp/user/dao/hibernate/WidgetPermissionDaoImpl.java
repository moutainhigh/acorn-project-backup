package com.chinadrtv.erp.user.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.WidgetPermission;
import com.chinadrtv.erp.user.dao.WidgetPermissionDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("widgetPermissionDao")
public class WidgetPermissionDaoImpl extends GenericDaoHibernate<WidgetPermission,Long> implements WidgetPermissionDao{
	
    public WidgetPermissionDaoImpl() {
        super(WidgetPermission.class);
    }

}
