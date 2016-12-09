/*
 * @(#)WmsShipmentHeaderDaoImpl.java 1.0 2013-2-18下午3:15:45
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.order.download.dal.wms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.trade.WmsShipmentHeader;
import com.chinadrtv.order.download.dal.wms.dao.WmsShipmentHeaderDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none
 * </dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>none
 * </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013-2-18 下午3:15:45
 */
@Repository
public class WmsShipmentHeaderDaoImpl extends
        GenericDaoHibernateBase<WmsShipmentHeader, Long> implements WmsShipmentHeaderDao {

    public WmsShipmentHeaderDaoImpl() {
        super(WmsShipmentHeader.class);
    }

    /* (非 Javadoc)
    * <p>Title: setSessionFactory</p>
    * <p>Description: </p>
    * @param sessionFactory
    * @see com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase#setSessionFactory(org.hibernate.SessionFactory)
    */
    @Autowired
    @Required
    @Qualifier("sessionFactoryWms")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

}
