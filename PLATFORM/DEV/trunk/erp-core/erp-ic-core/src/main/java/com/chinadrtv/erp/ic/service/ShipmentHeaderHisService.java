package com.chinadrtv.erp.ic.service;

/**
 * User: liukuan
 * Date: 13-2-16
 * Time: 上午11:49
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentHeaderHisService {
    @Deprecated
    void createShipmentHeaderHis();
    void processShipments(String batchUser);
}
