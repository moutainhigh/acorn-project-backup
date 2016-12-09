package com.chinadrtv.oms.internet.service;

import java.util.List;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.oms.internet.dal.model.TradeFeedback;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-6
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
public interface FeedbackService {
    
	/**
     * <p>获取反馈信息</p>
     * @param orderType
     * @return
     */
    List<TradeFeedback> searchOrderByType(List<String>  orderType);
    
    /**
     * <p>返回反馈结果修改状态</p>
     * @param preTrade
     * @return Boolean
     */
    Boolean updateOrderFeedbackStatus(PreTrade preTrade);
    
    /**
     * <p>反馈发货信息</p>
     * @param url
     * @param tradefeedback
     * @return Boolean
     */
    Boolean updateTradeStatus(String url, TradeFeedback tradefeedback) throws Exception;
}
