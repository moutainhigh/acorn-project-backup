package com.chinadrtv.erp.sales.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.OrderChange;
import com.chinadrtv.erp.sales.core.dao.OrderChangeDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-13
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class OrderChangeDaoImpl extends GenericDaoHibernateBase<OrderChange, Long> implements OrderChangeDao {
    public OrderChangeDaoImpl()
    {
        super(OrderChange.class);
    }
    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public OrderChange getOrderChangeFromProcInstId(String procInstId)
    {
        return this.find("from OrderChange where procInstId=:procInstId", new ParameterString("procInstId",procInstId));
    }

    @Override
    @Deprecated
    /**
     * Only for test transaction
     */
    public List<OrderChange> getOrderChangeList() {
        return this.findList("from OrderChange o where o.laststatus=:laststatus",new ParameterString("laststatus","abc"));
    }
}
