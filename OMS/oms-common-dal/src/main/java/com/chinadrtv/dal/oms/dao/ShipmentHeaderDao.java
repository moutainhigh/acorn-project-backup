package com.chinadrtv.dal.oms.dao;

import com.chinadrtv.model.oms.ShipmentHeader;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-10-24
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
public interface ShipmentHeaderDao {

    //获取entityId
    public String findByMailId(String mailId);

    List<ShipmentHeader> findShipments(List<String> shipmentIdList);
}
