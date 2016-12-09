package com.chinadrtv.erp.sales.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.Urgentorderhist;
import com.chinadrtv.erp.sales.core.dao.UrgentorderhistDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class UrgentorderhistDaoImpl extends GenericDaoHibernateBase<Urgentorderhist,String> implements UrgentorderhistDao {
    public UrgentorderhistDaoImpl()
    {
        super(Urgentorderhist.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public void deleteUrgentOrder(String orderId)
    {
        this.execUpdate("delete from Urgentorderhist where orderid=:orderId",new ParameterString("orderId",orderId));
    }
}
