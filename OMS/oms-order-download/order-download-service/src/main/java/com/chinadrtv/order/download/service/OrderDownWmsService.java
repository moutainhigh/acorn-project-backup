package com.chinadrtv.order.download.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.trade.ShipmentDownControl;

public interface OrderDownWmsService extends GenericService<ShipmentDownControl,Long> {

    //void orderDownToWms();

    /**
     * 正常订单下传
     */
    void shipmentDownToWms(ShipmentDownControl shipment);

    /**
     * 取消订单下传
     */
    void cancelShipmentDownToWms(ShipmentDownControl shipment);
}
