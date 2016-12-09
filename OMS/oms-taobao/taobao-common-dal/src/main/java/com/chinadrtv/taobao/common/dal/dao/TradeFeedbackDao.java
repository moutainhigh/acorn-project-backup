package com.chinadrtv.taobao.common.dal.dao;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.taobao.common.dal.model.TradeFeedback;

import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface TradeFeedbackDao extends BaseDao<TradeFeedback> {
	
    List<TradeFeedback> findFeedbacks(String orderType);
    
    int updateOrderFeedbackStatus(PreTrade preTrade);
	
    /**
	 * <p></p>
	 * @param valueOf
	 * @return List<TradeFeedback>
	 */
	List<TradeFeedback> queryChildList(String parentTradeId);
	
	/**
	 * <p></p>
	 * @param orderType
	 * @return List<TradeFeedback>
	 */
	List<TradeFeedback> queryRawTradeFeedback(String orderType);
	
}
