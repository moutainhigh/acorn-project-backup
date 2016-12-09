package com.chinadrtv.erp.user.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.FilterContext;
import com.chinadrtv.erp.user.dao.FilterContextDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("filterContextDao")
public class FilterContextDaoImpl extends GenericDaoHibernate<FilterContext,Long> implements FilterContextDao{
	
    public FilterContextDaoImpl() {
        super(FilterContext.class);
    }

}
