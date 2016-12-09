package com.chinadrtv.erp.oms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernate;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.agent.AreaAll;
import com.chinadrtv.erp.oms.dao.AcornAreaDao;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-6-12
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class AcornAreaDaoImpl extends GenericDaoHibernateBase<AreaAll, Integer> implements AcornAreaDao {
    public AcornAreaDaoImpl()
    {
        super(AreaAll.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactoryBAK")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    @Override
    public List<AreaAll> getPageAreaByCountryId(Integer countryId, Integer startPos,Integer pageSize) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AreaAll.class);
        if(countryId==null)
            return new ArrayList<AreaAll>();
        criteria.add(Restrictions.eq("countyid", countryId));

        return this.hibernateTemplate.findByCriteria(criteria,startPos,pageSize);
    }
}
