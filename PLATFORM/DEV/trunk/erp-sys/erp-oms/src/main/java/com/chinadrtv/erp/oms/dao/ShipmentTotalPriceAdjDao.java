package com.chinadrtv.erp.oms.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.trade.ShipmentTotalPriceAdj;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-12-20
 * Time: 下午1:59
 * To change this template use File | Settings | File Templates.
 * 客服商品折扣
 */
public interface ShipmentTotalPriceAdjDao extends GenericDao<ShipmentTotalPriceAdj, Long> {
    //数据保存
    void saveShipmentTotalPriceAdj(ShipmentTotalPriceAdj shipmentTotalPriceAdj);
}
