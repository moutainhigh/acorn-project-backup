package com.chinadrtv.taobao.biz.jms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.taobao.biz.OrderBizHandler;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-5
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderFeedbackTopicListener extends JmsListener<Object> {
	
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderFeedbackTopicListener.class);

    private List<TaobaoOrderConfig> taobaoOrderConfigList;

    @Autowired
    private OrderBizHandler orderBizHandler;

	public OrderFeedbackTopicListener() {
		logger.info("OrderFeedbackTopicListener is created");
	}

	@Override
	public void messageHandler(Object msg) throws Exception {
		logger.info("timing feedback begin");
		try {
			orderBizHandler.feedback(taobaoOrderConfigList);
		} catch (Exception exp) {
			logger.info("timing feedback end with error:", exp);
			throw exp;
		}
		logger.info("timing feedback end succ");
	}
    
    public List<TaobaoOrderConfig> getTaobaoOrderConfigList() {
        return taobaoOrderConfigList;
    }
    public void setTaobaoOrderConfigList(List<TaobaoOrderConfig> taobaoOrderConfigList) {
        this.taobaoOrderConfigList = taobaoOrderConfigList;
    }
}
