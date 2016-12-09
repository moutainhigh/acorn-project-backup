package com.chinadrtv.erp.tc.core.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.ModifyOrderhistExt;
import com.chinadrtv.erp.tc.core.dao.ModifyOrderhistExtDao;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-2-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class ModifyOrderhistExtDaoImpl extends GenericDaoHibernateBase<ModifyOrderhistExt,ModifyOrderhistExt.ModifyOderhistExtKey> implements ModifyOrderhistExtDao {
    public ModifyOrderhistExtDaoImpl()
    {
        super(ModifyOrderhistExt.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}
