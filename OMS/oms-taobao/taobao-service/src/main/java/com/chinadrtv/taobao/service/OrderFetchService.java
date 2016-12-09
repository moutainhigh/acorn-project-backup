package com.chinadrtv.taobao.service;

import java.util.Date;
import java.util.List;

import com.chinadrtv.taobao.model.TaobaoOrderConfig;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-10-30
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderFetchService {
    Date getServerTime(TaobaoOrderConfig taobaoOrderConfig);
    
    List<String> getOrders(TaobaoOrderConfig taobaoOrderConfig,Date startDate, Date endDate) throws Exception;
}
