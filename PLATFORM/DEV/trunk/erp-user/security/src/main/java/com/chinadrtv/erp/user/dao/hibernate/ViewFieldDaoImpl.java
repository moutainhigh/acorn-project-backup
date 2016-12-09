package com.chinadrtv.erp.user.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.ViewField;
import com.chinadrtv.erp.user.dao.ViewFieldDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("viewFieldDao")
public class ViewFieldDaoImpl extends GenericDaoHibernate<ViewField,Long> implements ViewFieldDao{
	
    public ViewFieldDaoImpl() {
        super(ViewField.class);
    }

}
