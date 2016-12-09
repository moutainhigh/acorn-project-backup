package com.chinadrtv.erp.tc.core.service.impl;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.core.service.impl.GenericServiceImpl;
import com.chinadrtv.erp.model.trade.ShipmentChangeHis;
import com.chinadrtv.erp.model.trade.ShipmentHeader;
import com.chinadrtv.erp.tc.core.dao.ShipmentChangeHisDao;
import com.chinadrtv.erp.tc.core.service.ShipmentChangeHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */
@Deprecated
@Service
public class ShipmentChangeHisServiceImpl extends GenericServiceImpl<ShipmentChangeHis,Long> implements ShipmentChangeHisService {

    @Autowired
    private ShipmentChangeHisDao shipmentChangeHisDao;

    @Override
    protected GenericDao<ShipmentChangeHis,Long> getGenericDao(){
        return shipmentChangeHisDao;
    }

    public void addShipmentChangeHisFromUpdate(ShipmentHeader newShipmentHeader,ShipmentHeader oldShipmentHeader)
    {
        this.shipmentChangeHisDao.addShipmentChangeHisFromUpdate(newShipmentHeader,oldShipmentHeader);
    }

    public void addShipmentChangeHis(ShipmentChangeHis shipmentChangeHis)
    {
        this.shipmentChangeHisDao.save(shipmentChangeHis);
    }


    public ShipmentChangeHis getLatestShipmentHis(Long shipmentId)
    {
        return this.shipmentChangeHisDao.getLatestShipmentHis(shipmentId);
    }

    public ShipmentChangeHis getLatestShipmentHisFromOrderId(String orderId)
    {
        return this.shipmentChangeHisDao.getLatestShipmentHisFromOrderId(orderId);
    }

}
