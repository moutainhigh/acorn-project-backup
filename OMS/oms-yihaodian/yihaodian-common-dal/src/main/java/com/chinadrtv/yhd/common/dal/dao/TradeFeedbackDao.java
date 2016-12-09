package com.chinadrtv.yhd.common.dal.dao;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.yhd.common.dal.model.TradeFeedback;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 14-3-20
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public interface TradeFeedbackDao {
    List<TradeFeedback> findFeedBacks(String tradeType);
    int updateOrderFeedbackStatus(PreTrade preTrade);
}
