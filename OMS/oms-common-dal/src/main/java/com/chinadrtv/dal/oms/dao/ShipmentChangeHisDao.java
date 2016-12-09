package com.chinadrtv.dal.oms.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.oms.ShipmentChangeHis;
import com.chinadrtv.model.oms.dto.SequenceDto;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-20
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ShipmentChangeHisDao extends BaseDao<ShipmentChangeHis> {
    List<ShipmentChangeHis> findHisFromShipments(List<String> shipmentIdList);
    List<SequenceDto> fecthSequence(int count);
}
