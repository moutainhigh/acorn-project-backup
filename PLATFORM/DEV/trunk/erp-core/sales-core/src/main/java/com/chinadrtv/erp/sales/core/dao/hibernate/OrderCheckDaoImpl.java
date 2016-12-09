package com.chinadrtv.erp.sales.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.OrderCheck;
import com.chinadrtv.erp.sales.core.dao.OrderCheckDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class OrderCheckDaoImpl extends GenericDaoHibernateBase<OrderCheck,Long> implements OrderCheckDao {
    public OrderCheckDaoImpl()
    {
        super(OrderCheck.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<OrderCheck> getOrderChecks(String orderId)
    {
        return this.findList("from OrderCheck where orderId=:orderId",new ParameterString("orderId",orderId));
    }

    public void saveOrderChecks(List<OrderCheck> orderCheckList)
    {
        for(OrderCheck orderCheck:orderCheckList)
        {
            this.save(orderCheck);
        }
    }

}
