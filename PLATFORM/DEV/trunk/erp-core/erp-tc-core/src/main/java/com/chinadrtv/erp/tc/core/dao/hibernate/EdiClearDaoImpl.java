package com.chinadrtv.erp.tc.core.dao.hibernate;

import com.chinadrtv.erp.model.EdiClear;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.tc.core.dao.EdiClearDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-4-26
 * Time: 下午2:41
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class EdiClearDaoImpl extends GenericDaoHibernateBase<EdiClear, String> implements EdiClearDao {

    public EdiClearDaoImpl()
    {
        super(EdiClear.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public EdiClear getEdiClear(String ediClearId){
        String hql ="from EdiClear p where p.clearid = :ediClearId";
        Parameter parameter = new ParameterString("ediClearId",ediClearId);
        EdiClear ediClear = find(hql,parameter);
        return ediClear;
    }
}
