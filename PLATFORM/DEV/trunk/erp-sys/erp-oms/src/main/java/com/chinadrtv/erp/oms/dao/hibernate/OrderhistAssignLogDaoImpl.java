package com.chinadrtv.erp.oms.dao.hibernate;


import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.model.OrderhistAssignLog;
import com.chinadrtv.erp.oms.dao.OrderhistAssignLogDao;

/**
 * OrderhistAssignLogDaoImpl
 * 
 * 手工挑单分配日志
 * @author zhaizhanyi
 *
 */
@Repository
public class OrderhistAssignLogDaoImpl extends GenericDaoHibernate<OrderhistAssignLog, java.lang.String> implements OrderhistAssignLogDao{

	public OrderhistAssignLogDaoImpl() {
	    super(OrderhistAssignLog.class);
	}
	
	
	
}
