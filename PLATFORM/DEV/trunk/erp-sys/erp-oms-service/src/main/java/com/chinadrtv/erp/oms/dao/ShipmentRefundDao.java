package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentRefund;

import java.util.List;


public interface ShipmentRefundDao extends GenericDao<ShipmentRefund, Long> {

    List<ShipmentRefund> getShipmentRefundFromOrderId(String orderId);
}
