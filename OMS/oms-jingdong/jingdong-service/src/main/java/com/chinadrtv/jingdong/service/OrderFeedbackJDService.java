package com.chinadrtv.jingdong.service;

import com.chinadrtv.jingdong.common.dal.model.TradeFeedback;
import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.model.oms.PreTrade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-11
 * Time: 下午1:28
 * To change this template use File | Settings | File Templates.
 */
public interface OrderFeedbackJDService {
    //获取指定订单类型的反馈订单列表
     List<TradeFeedback> searchOrderByType(String orderType);
     boolean updateOrderFeedbackStatus(PreTrade preTrade) ;

    //反馈发货信息到淘宝
     boolean updateTradeStatus(TradeFeedback tradefeedback,JingdongOrderConfig jdorderconfig) throws Exception;
}
