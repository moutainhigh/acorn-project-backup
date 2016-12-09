package com.chinadrtv.amazon.service.impl;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.amazon.common.dal.dao.TradeFeedbackDao;
import com.chinadrtv.amazon.common.dal.model.TradeFeedback;
import com.chinadrtv.amazon.service.OrderFeedbackFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderFeedbackFetchServiceImpl implements OrderFeedbackFetchService {

    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;

    @Override
    public List<TradeFeedback> searchOrderByType(String orderType) {
        return tradeFeedbackDao.findFeedbacks(orderType);
    }

    @Override
    public List<String> seachResultByType(String orderType) {
        return tradeFeedbackDao.findFeedbackResults(orderType);
    }

    @Override
    public boolean updateOrderFeedbackStatus(PreTrade preTrade) {
        int count=tradeFeedbackDao.updateOrderFeedbackStatus(preTrade);
        if(count>0)
            return true;
        return false;
    }

    @Override
    public boolean updateOrderFeedbackResultStatus(PreTrade preTrade) {
        int count= tradeFeedbackDao.updateOrderFeedbackResultStatus(preTrade);
        if(count>0)
            return true;
        return false;
    }

    @Override
    public boolean updateOrderFeedbackResultSuccess(PreTrade preTrade) {
        int count=tradeFeedbackDao.updateOrderFeedbackResultSuccess(preTrade);
        if(count>0)
            return true;
        return false;
    }
}
