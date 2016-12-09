package com.chinadrtv.erp.user.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.Filter;
import com.chinadrtv.erp.user.dao.FilterDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("filterDao")
public class FilterDaoImpl extends GenericDaoHibernate<Filter,Long> implements FilterDao{
	
    public FilterDaoImpl() {
        super(Filter.class);
    }

}
