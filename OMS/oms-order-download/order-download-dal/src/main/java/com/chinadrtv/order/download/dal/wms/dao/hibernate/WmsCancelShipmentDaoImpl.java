package com.chinadrtv.order.download.dal.wms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.trade.WmsCancelShipment;
import com.chinadrtv.order.download.dal.wms.dao.WmsCancelShipmentDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-4-3
 * Time: 上午9:46
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class WmsCancelShipmentDaoImpl extends GenericDaoHibernateBase<WmsCancelShipment, Long> implements WmsCancelShipmentDao
{
    public WmsCancelShipmentDaoImpl() {
        super(WmsCancelShipment.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactoryWms")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

}
