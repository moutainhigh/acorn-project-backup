package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.agent.Parameters;
import com.chinadrtv.erp.tc.core.constant.cache.CacheNames;
import com.chinadrtv.erp.tc.core.dao.ParametersDao;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-9-16
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class ParametersDaoImpl extends GenericDaoHibernateBase<Parameters, String> implements ParametersDao {
    public ParametersDaoImpl() {
        super(Parameters.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    @ReadThroughSingleCache(namespace = CacheNames.CACHE_PARAMETER, expiration = 3600)
    public Parameters getSystemParm(@ParameterValueKeyProvider String name)
    {
        return this.get(name);
    }
}
