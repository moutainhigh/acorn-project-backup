package com.chinadrtv.taobao.service;

import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.common.dal.model.TradeFeedback;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderFeedbackService {
    String updateTradeStatus(TaobaoOrderConfig taobaoOrderConfig, TradeFeedback tradefeedback);
}
