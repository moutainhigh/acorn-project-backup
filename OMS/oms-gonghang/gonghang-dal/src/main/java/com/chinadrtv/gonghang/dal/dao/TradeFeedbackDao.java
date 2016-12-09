package com.chinadrtv.gonghang.dal.dao;

import java.util.List;

import com.chinadrtv.common.dal.BaseDao;
import com.chinadrtv.gonghang.dal.model.TradeFeedback;
import com.chinadrtv.model.oms.PreTrade;

/**
 * Created with (TC). User: liukuan Date: 13-11-5 橡果国际-系统集成部 Copyright (c)
 * 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface TradeFeedbackDao extends BaseDao<TradeFeedback> {
	
	/**
	 * <p>load feedback data</p>
	 * @param orderType
	 * @return List<TradeFeedback>
	 */
	List<TradeFeedback> findFeedbacks(String orderType);

	/**
	 * <p>feedback success then update sign</p>
	 * @param preTrade
	 * @return Integer
	 */
	int updateOrderFeedbackStatus(PreTrade preTrade);
	
}
