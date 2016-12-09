package com.chinadrtv.erp.ic.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.inventory.WmsShipmentHeaderHis;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-2-26
 * Time: 上午9:35
 * To change this template use File | Settings | File Templates.
 */
public interface WmsShipmentHeaderHisDao extends GenericDao<WmsShipmentHeaderHis,Long> {
    List<WmsShipmentHeaderHis> getUnhandledShipments();
    Double getTotalQty(String warehouse, String shipmentId, String orderTyper, String productCode);
    void batchLog(WmsShipmentHeaderHis shipment);
}
