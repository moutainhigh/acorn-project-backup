package com.chinadrtv.erp.sales.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.agent.OrderTrackRemark;
import com.chinadrtv.erp.sales.core.dao.OrderTrackRemarkDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-12-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class OrderTrackRemarkDaoImpl extends GenericDaoHibernateBase<OrderTrackRemark,Long> implements OrderTrackRemarkDao {
    public OrderTrackRemarkDaoImpl()
    {
        super(OrderTrackRemark.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}
