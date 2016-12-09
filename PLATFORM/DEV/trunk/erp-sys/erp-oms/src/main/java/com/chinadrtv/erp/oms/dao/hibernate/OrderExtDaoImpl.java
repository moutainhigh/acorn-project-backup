package com.chinadrtv.erp.oms.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.OrderExt;
import com.chinadrtv.erp.oms.dao.OrderExtDao;

@Repository
public class OrderExtDaoImpl extends GenericDaoHibernate<OrderExt, java.lang.String> implements OrderExtDao{

	public OrderExtDaoImpl() {
	    super(OrderExt.class);
	}

	

}
