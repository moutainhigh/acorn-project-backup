package com.chinadrtv.service.oms;

import com.chinadrtv.model.oms.ShipmentChangeHis;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ShipmentChangeHisService {
    List<ShipmentChangeHis> findHisFromShipments(List<String> shipmentIdList);
    void saveShipmentChangeHisList(List<ShipmentChangeHis> shipmentChangeHisList);
}
