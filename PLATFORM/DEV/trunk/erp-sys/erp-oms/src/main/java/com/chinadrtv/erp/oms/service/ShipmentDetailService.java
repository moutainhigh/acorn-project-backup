package com.chinadrtv.erp.oms.service;

import com.chinadrtv.erp.model.trade.ShipmentHeader;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-4-26
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentDetailService {
    /**
     * 免运费登记表头
     * @param sh
     * @param afterFreight
     */
    public void copyShipmentHeader(ShipmentHeader sh,String afterFreight);

    /**
     * 免运费登记明细
     * @param sh
     * @param afterFreight
     */
    public void copyShipmentDetail(ShipmentHeader sh,String afterFreight);
}
