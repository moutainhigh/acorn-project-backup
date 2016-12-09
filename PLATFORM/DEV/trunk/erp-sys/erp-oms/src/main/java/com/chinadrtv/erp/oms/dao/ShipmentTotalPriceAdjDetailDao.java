package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentTotalPriceAdjDetail;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-20
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentTotalPriceAdjDetailDao extends GenericDao<ShipmentTotalPriceAdjDetail, Long> {

    //数据保存
    void saveShipmentTotalPriceAdjDetail(ShipmentTotalPriceAdjDetail shipmentTotalPriceAdjDetail);
}
