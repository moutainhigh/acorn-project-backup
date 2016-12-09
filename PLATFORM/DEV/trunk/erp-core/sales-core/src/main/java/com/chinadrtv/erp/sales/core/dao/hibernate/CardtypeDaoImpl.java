package com.chinadrtv.erp.sales.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.sales.core.dao.CardtypeDao;
import com.chinadrtv.erp.sales.core.model.CardAmortisationDto;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-5-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class CardtypeDaoImpl extends GenericDaoHibernateBase<Cardtype,String> implements CardtypeDao {
    public CardtypeDaoImpl()
    {
        super(Cardtype.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}
