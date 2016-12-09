package com.chinadrtv.gonghang.service;

import java.util.List;

import com.chinadrtv.gonghang.dal.model.OrderConfig;

/**
 * Created with (TC). User: 徐志凯 Date: 13-11-5 橡果国际-系统集成部 Copyright (c) 2012-2013
 * ACORN, Inc. All rights reserved.
 */
public interface OrderFeedbackService {

	/**
	 * <p>order feedback</p>
	 * @param configList
	 */
	void feedback(List<OrderConfig> configList) throws Exception;
	
}
