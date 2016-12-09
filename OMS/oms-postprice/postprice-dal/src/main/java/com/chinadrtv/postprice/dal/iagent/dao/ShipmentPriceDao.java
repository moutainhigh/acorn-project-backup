package com.chinadrtv.postprice.dal.iagent.dao;


import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-21
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface ShipmentPriceDao {
    List<PostPriceItem> findPostPriceShipments(int batchSize);
    void updatePostPrice(PostPriceItem postPriceItem);
}
