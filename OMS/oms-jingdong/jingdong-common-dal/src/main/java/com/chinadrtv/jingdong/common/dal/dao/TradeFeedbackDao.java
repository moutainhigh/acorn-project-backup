package com.chinadrtv.jingdong.common.dal.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.jingdong.common.dal.model.TradeFeedback;
import com.chinadrtv.model.oms.PreTrade;

import java.util.List;

/**
 * Created with (JDOrder).
 * User: 刘宽
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface TradeFeedbackDao extends BaseDao<TradeFeedback> {
    List<TradeFeedback> findFeedbacks(String orderType);
    int updateOrderFeedbackStatus(PreTrade preTrade);
}
