package com.chinadrtv.oms.suning.dal.dao;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.oms.suning.dal.model.TradeFeedback;

import java.util.List;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-11-13 下午2:08:44 
 *
 */
public interface TradeFeedbackDao {
    /**
     * 更改状态
     * @param preTrade
     * @return
     */
    int updateOrderFeedbackStatus(PreTrade preTrade);

	/**
	 * <p></p>
	 * @param tradeType
	 * @return
	 */
	List<TradeFeedback> findFeedbackList(String tradeType);
}
