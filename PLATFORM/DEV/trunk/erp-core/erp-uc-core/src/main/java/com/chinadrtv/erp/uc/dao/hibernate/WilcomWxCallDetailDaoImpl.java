package com.chinadrtv.erp.uc.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-10
 * Time: 上午11:07
 * To change this template use File | Settings | File Templates.
 */
@Repository("WilcomWxCallDetailDao")
public class WilcomWxCallDetailDaoImpl extends WilcomCallDetailDaoImpl {

    @Autowired
    @Required
    @Qualifier("wilcomWxSessionFactory")
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
