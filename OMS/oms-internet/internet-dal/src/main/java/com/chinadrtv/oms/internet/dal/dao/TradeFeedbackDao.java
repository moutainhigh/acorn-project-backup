package com.chinadrtv.oms.internet.dal.dao;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.oms.internet.dal.model.TradeFeedback;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-6
 * Time: 下午1:08
 * To change this template use File | Settings | File Templates.
 */
public interface TradeFeedbackDao {
    /**
     * 获取反馈信息
     * @param orderType
     * @return
     */
    List<TradeFeedback> findByOpsId(List<String> orderType);

    /**
     * 更改状态
     * @param preTrade
     * @return
     */
    int updateOrderFeedbackStatus(PreTrade preTrade);
}
