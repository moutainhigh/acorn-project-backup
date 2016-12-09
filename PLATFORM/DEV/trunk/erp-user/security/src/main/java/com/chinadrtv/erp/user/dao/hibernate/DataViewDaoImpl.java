package com.chinadrtv.erp.user.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.security.DataView;
import com.chinadrtv.erp.user.dao.DataViewDao;

/**
 * User: zhaizhanyi
 * Date: 12-11-19
 */

@Repository("dataViewDao")
public class DataViewDaoImpl extends GenericDaoHibernate<DataView,Long> implements DataViewDao{
	
    public DataViewDaoImpl() {
        super(DataView.class);
    }

}
