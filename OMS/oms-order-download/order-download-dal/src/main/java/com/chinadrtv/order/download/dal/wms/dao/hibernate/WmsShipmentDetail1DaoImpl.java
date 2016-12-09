package com.chinadrtv.order.download.dal.wms.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.trade.WmsShipmentDetail1;
import com.chinadrtv.order.download.dal.wms.dao.WmsShipmentDetail1Dao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;


/**
 * @author andrew
 * @version 1.0
 * @since 2013-2-18 下午3:12:20
 */
@Repository
public class WmsShipmentDetail1DaoImpl extends
        GenericDaoHibernateBase<WmsShipmentDetail1, Long> implements WmsShipmentDetail1Dao {


    public WmsShipmentDetail1DaoImpl() {
        super(WmsShipmentDetail1.class);
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
