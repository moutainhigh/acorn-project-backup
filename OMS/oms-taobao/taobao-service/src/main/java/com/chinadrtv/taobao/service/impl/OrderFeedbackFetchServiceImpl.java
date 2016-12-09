package com.chinadrtv.taobao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.model.oms.PreTrade;
import com.chinadrtv.taobao.common.dal.dao.TradeFeedbackDao;
import com.chinadrtv.taobao.common.dal.model.TradeFeedback;
import com.chinadrtv.taobao.service.OrderFeedbackFetchService;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service
public class OrderFeedbackFetchServiceImpl implements OrderFeedbackFetchService {

    @Autowired
    private TradeFeedbackDao tradeFeedbackDao;

    @Override
    public List<TradeFeedback> searchOrderByType(String orderType) {
    	
    	List<TradeFeedback> dataList = tradeFeedbackDao.findFeedbacks(orderType);
    	
    	//Raw food trade
    	List<TradeFeedback> tempList = tradeFeedbackDao.queryRawTradeFeedback(orderType);
    	
    	if(null == dataList) {
    		dataList = new ArrayList<TradeFeedback>();
    	}
    	
    	if(null != tempList && tempList.size() != 0) {
    		dataList.addAll(tempList);
    	}
    	
        return dataList;
    }

    @Override
    public boolean updateOrderFeedbackStatus(PreTrade preTrade) {
        int count=tradeFeedbackDao.updateOrderFeedbackStatus(preTrade);
        if(count>0)
            return true;
        else
            return false;
    }
}
