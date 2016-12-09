package com.chinadrtv.order.download.dal.iagent.dao.hibernate;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.trade.ShipmentDownControl;
import com.chinadrtv.order.download.dal.iagent.dao.OrderDownloadDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liyu
 * Date: 13-3-13
 * Time: 下午6:45
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class OrderDownloadDaoImpl extends GenericDaoHibernateBase<ShipmentDownControl, Long> implements OrderDownloadDao {

    public OrderDownloadDaoImpl() {
        super(ShipmentDownControl.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public List<ShipmentDownControl> getShipmentDownControl() {

        List<ShipmentDownControl> returnlist = new ArrayList<ShipmentDownControl>();

        StringBuilder sb = new StringBuilder();
        sb.append(" select t.shipment_id as shipmentId, t.order_ref_revision as revison, ");
        sb.append(" t.order_id as orderid, t.account_status_id as orderStatus, t.logistics_status_id as logistStatus ");
        sb.append(" from acoapp_oms.vw_wms_shipment_download t ");

        List<Object[]> list = getSession().createSQLQuery(sb.toString()).list();

        for (int i = 0; i < list.size(); i++) {
            Object obj[] = (Object[]) list.get(i);

            ShipmentDownControl shipmentDownInfo = new ShipmentDownControl();
            shipmentDownInfo.setShipmentId(obj[0].toString());
            shipmentDownInfo.setRevison(obj[1].toString());
            shipmentDownInfo.setOrderid(obj[2].toString());
            shipmentDownInfo.setOrderStatus(obj[3].toString());
            shipmentDownInfo.setLogisticsStatus(obj[4].toString());
            shipmentDownInfo.setImpdat(new Date());
            shipmentDownInfo.setIsrtn(0L);
            shipmentDownInfo.setAllowRepeat(0L);

            returnlist.add(shipmentDownInfo);
        }

        return returnlist;
    }
}
