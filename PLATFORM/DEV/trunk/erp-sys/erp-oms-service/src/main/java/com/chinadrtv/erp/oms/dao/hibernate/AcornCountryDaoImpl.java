package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.model.agent.CountyAll;
import com.chinadrtv.erp.oms.dao.AcornCountryDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class AcornCountryDaoImpl extends GenericDaoHibernateBase<CountyAll,Integer> implements AcornCountryDao{
    public AcornCountryDaoImpl()
    {
        super(CountyAll.class);
    }

    @Override
    public List<CountyAll> findCountysByCity(Integer cityId) {
        return this.findList("from CountyAll where cityid=:cityid",new ParameterInteger("cityid",cityId));
    }

    @Autowired
    @Required
    @Qualifier("sessionFactoryBAK")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}
