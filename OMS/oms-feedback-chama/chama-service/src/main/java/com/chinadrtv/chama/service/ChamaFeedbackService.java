package com.chinadrtv.chama.service;

import com.chinadrtv.chama.dal.model.TradeFeedback;
import com.chinadrtv.model.oms.PreTrade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-6
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public interface ChamaFeedbackService {
    //获取反馈信息
    List<TradeFeedback> searchOrderByType(List<String>  orderType);
    //返回反馈结果修改状态
    boolean updateOrderFeedbackStatus(PreTrade preTrade);
    //反馈发货信息到茶马
    boolean updateTradeStatus(String url, TradeFeedback tradefeedback);
}
