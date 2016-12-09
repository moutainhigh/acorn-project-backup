package com.chinadrtv.erp.user.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.Widget;
import com.chinadrtv.erp.user.dao.WidgetDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("widgetDao")
public class WidgetDaoImpl extends GenericDaoHibernate<Widget,Long> implements WidgetDao{
	
    public WidgetDaoImpl() {
        super(Widget.class);
    }

}
