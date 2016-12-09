package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.agent.Province;
import com.chinadrtv.erp.oms.dao.AcornProvinceDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class AcornProvinceDaoImpl extends GenericDaoHibernateBase<Province,String> implements AcornProvinceDao {
    public AcornProvinceDaoImpl()
    {
        super(Province.class);
    }

    @Override
    public Province findProvinceByName(String name) {
        return this.find("from Province where chinese=:name",new ParameterString("name",name));
    }

    @Autowired
    @Required
    @Qualifier("sessionFactoryBAK")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }
}
