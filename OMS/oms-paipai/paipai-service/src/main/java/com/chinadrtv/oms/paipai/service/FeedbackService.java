package com.chinadrtv.oms.paipai.service;

import java.util.List;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.oms.paipai.dto.OrderConfig;

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
 * @since 2014-11-13 下午2:30:15 
 *
 */
public interface FeedbackService {
    
    
    /**
     * <p>返回反馈结果修改状态</p>
     * @param preTrade
     * @return Boolean
     */
    Boolean updateOrderFeedbackStatus(PreTrade preTrade);

	/**
	 * <p></p>
	 * @param configList
	 */
	void feedback(List<OrderConfig> configList);
}
