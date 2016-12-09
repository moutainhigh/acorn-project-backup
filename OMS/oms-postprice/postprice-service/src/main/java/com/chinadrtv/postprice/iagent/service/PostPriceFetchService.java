package com.chinadrtv.postprice.iagent.service;

import com.chinadrtv.postprice.dal.iagent.model.PostPriceItem;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-22
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface PostPriceFetchService {
    List<PostPriceItem> fetchOrders();
    void saveOrderPostPrice(List<PostPriceItem> postPriceItemList);
}
