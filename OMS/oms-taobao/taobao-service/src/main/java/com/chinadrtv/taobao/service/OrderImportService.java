package com.chinadrtv.taobao.service;

import com.chinadrtv.taobao.model.TaobaoOrderConfig;

import java.util.Date;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-29
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderImportService {
    void importOrders(List<TaobaoOrderConfig> taobaoOrderConfigList,Date startDate, Date endDate);
    //Date getServerTime(TaobaoOrderConfig taobaoOrderConfig);
    //List<String> getOrders(TaobaoOrderConfig taobaoOrderConfig,Date startDate, Date endDate);
}
