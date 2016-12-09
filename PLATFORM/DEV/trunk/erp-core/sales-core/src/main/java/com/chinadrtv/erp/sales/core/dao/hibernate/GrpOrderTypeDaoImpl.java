package com.chinadrtv.erp.sales.core.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.GrpOrderType;
import com.chinadrtv.erp.model.agent.MediaDnis;
import com.chinadrtv.erp.sales.core.dao.GrpOrderTypeDao;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-6-18
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Repository
public class GrpOrderTypeDaoImpl extends GenericDaoHibernateBase<GrpOrderType,Long> implements GrpOrderTypeDao {
    public GrpOrderTypeDaoImpl()
    {
        super(GrpOrderType.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<MediaDnis> getOrderTypeByN400(String n400) {
        Query hqlQuery = getSession().createQuery("from  MediaDnis  m where m.startdt <:sysdate and  m.enddt >:sysdate and  m.n400 =:n400 ");
        hqlQuery.setParameter("n400",n400);
        hqlQuery.setParameter("sysdate",new Date());
        return  hqlQuery.list();
    }
}
