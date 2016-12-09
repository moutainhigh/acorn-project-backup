package com.chinadrtv.erp.tc.core.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentChangeHis;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */
public interface ShipmentChangeHisDao extends GenericDao<ShipmentChangeHis,Long> {
    void addShipmentChangeHis(ShipmentChangeHis shipmentChangeHis);
    void addShipmentChangeHisFromUpdate(ShipmentHeader newShipmentHeader,ShipmentHeader oldShipmentHeader);
    ShipmentChangeHis getLatestShipmentHis(Long shipmentId);
    ShipmentChangeHis getLatestShipmentHisFromOrderId(String orderId);
}
