package com.chinadrtv.amazon.service;

import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.common.dal.model.TradeFeedback;
import com.chinadrtv.amazon.model.TradeResultList;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface OrderFeedbackService {
    boolean updateTradeStatus(AmazonOrderConfig amazonOrderConfig, List<TradeFeedback> tradefeedbackList, StringBuffer feedSubmissionId);
    TradeResultList getTradeResultInfo(AmazonOrderConfig amazonOrderConfig, String feedSubmissionId);
}
