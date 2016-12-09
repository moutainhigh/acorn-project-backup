package com.chinadrtv.postprice.dal.wms.dao;

import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;
import com.chinadrtv.postprice.dal.wms.model.WmsShipmentWeight;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface WmsShipmentWeightDao {
    List<WmsShipmentWeight> findShipmentWeight(List<PostPriceItem> postPriceItemList);
}
