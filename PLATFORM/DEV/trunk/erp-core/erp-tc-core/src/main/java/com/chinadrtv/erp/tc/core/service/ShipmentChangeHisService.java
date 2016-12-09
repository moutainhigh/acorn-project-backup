package com.chinadrtv.erp.tc.core.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.trade.ShipmentChangeHis;
import com.chinadrtv.erp.model.trade.ShipmentHeader;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 13-2-5
 */
@Deprecated
public interface ShipmentChangeHisService  extends GenericService<ShipmentChangeHis,Long> {
    void addShipmentChangeHisFromUpdate(ShipmentHeader newShipmentHeader,ShipmentHeader oldShipmentHeader);
    void addShipmentChangeHis(ShipmentChangeHis shipmentChangeHis);
    ShipmentChangeHis getLatestShipmentHis(Long shipmentId);
    ShipmentChangeHis getLatestShipmentHisFromOrderId(String orderId);
}
