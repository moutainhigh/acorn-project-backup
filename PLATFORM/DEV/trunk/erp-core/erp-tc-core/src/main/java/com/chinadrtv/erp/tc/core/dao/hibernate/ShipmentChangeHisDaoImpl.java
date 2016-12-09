package com.chinadrtv.erp.tc.core.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.core.dao.hibernate.GenericDaoHibernateBase;
import com.chinadrtv.erp.model.trade.ShipmentChangeHis;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.dao.ShipmentChangeHisDao;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */
@Repository
public class ShipmentChangeHisDaoImpl extends GenericDaoHibernateBase<ShipmentChangeHis,Long> implements ShipmentChangeHisDao {

    public ShipmentChangeHisDaoImpl()
    {
        super(ShipmentChangeHis.class);
    }

    @Autowired
    @Required
    @Qualifier("sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.doSetSessionFactory(sessionFactory);
    }

    public void addShipmentChangeHis(ShipmentChangeHis shipmentChangeHis)
    {
        this.save(shipmentChangeHis);
    }

    public void addShipmentChangeHisFromUpdate(ShipmentHeader newShipmentHeader,ShipmentHeader oldShipmentHeader)
    {
        //根据更新前和更新后值，来记录日志
        ShipmentChangeHis shipmentChangeHis=new ShipmentChangeHis();

        shipmentChangeHis.setAfterAccountStatusId(newShipmentHeader.getAccountStatusId());
        shipmentChangeHis.setAfterAccountStatusRemark(newShipmentHeader.getAccountStatusRemark());
        shipmentChangeHis.setAfterEntityId(newShipmentHeader.getEntityId());
        shipmentChangeHis.setAfterLogisticsStatusId(newShipmentHeader.getLogisticsStatusId());
        shipmentChangeHis.setAfterLogisticsStatusRemark(newShipmentHeader.getLogisticsStatusRemark());
        shipmentChangeHis.setAfterMailId(newShipmentHeader.getMailId());
        shipmentChangeHis.setAfterWarehouseId(newShipmentHeader.getWarehouseId());

        shipmentChangeHis.setBeforeAccountStatusId(oldShipmentHeader.getAccountStatusId());
        shipmentChangeHis.setBeforeAccountStatusRemark(oldShipmentHeader.getAccountStatusRemark());
        shipmentChangeHis.setBeforeEntityId(oldShipmentHeader.getEntityId());
        shipmentChangeHis.setBeforeLogisticsStatusId(oldShipmentHeader.getLogisticsStatusId());
        shipmentChangeHis.setBeforeLogisticsStatusRemark(oldShipmentHeader.getLogisticsStatusRemark());
        shipmentChangeHis.setBeforeMailId(oldShipmentHeader.getMailId());
        shipmentChangeHis.setBeforeWarehouseId(oldShipmentHeader.getWarehouseId());

        shipmentChangeHis.setShipmentRefId(newShipmentHeader.getId());
        shipmentChangeHis.setDateTimeStamp(new Date());
        //TODO:处理程序名称
        shipmentChangeHis.setProcessStamp("");
        shipmentChangeHis.setShipmentId(newShipmentHeader.getShipmentId());
        shipmentChangeHis.setUserStamp(newShipmentHeader.getMdusr());

        this.save(shipmentChangeHis);
    }

    public ShipmentChangeHis getLatestShipmentHis(Long shipmentId)
    {
        Query query =this.getSession().createQuery("from ShipmentChangeHis where shipmentRefId=:shipmentId order by dateTimeStamp desc");
        query.setParameter("shipmentId",shipmentId);
        query.setMaxResults(1);
        List<ShipmentChangeHis> shipmentChangeHisList= query.list();
        if(shipmentChangeHisList!=null&&shipmentChangeHisList.size()==1)
        {
            return shipmentChangeHisList.get(0);
        }
        return null;
    }

    public ShipmentChangeHis getLatestShipmentHisFromOrderId(String orderId)
    {
        Query query =this.getSession().createQuery("from ShipmentChangeHis where shipmentId=:shipmentId order by dateTimeStamp desc");
        query.setParameter("shipmentId",orderId);
        query.setMaxResults(1);
        List<ShipmentChangeHis> shipmentChangeHisList= query.list();
        if(shipmentChangeHisList!=null&&shipmentChangeHisList.size()==1)
        {
            return shipmentChangeHisList.get(0);
        }
        return null;
    }
}
