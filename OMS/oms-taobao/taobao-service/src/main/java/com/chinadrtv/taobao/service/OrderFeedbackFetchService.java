package com.chinadrtv.taobao.service;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.taobao.common.dal.model.TradeFeedback;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderFeedbackFetchService {
    List<TradeFeedback> searchOrderByType(String orderType);
    boolean updateOrderFeedbackStatus(PreTrade preTrade);
}
