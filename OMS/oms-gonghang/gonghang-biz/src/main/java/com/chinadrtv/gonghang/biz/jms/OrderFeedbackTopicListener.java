package com.chinadrtv.gonghang.biz.jms;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.gonghang.dal.model.OrderConfig;
import com.chinadrtv.gonghang.service.OrderFeedbackService;
import com.chinadrtv.runtime.jms.receive.JmsListener;

/**
 * Created with (TC). User: 徐志凯 Date: 13-11-5 橡果国际-系统集成部 Copyright (c) 2012-2013
 * ACORN, Inc. All rights reserved.
 */
public class OrderFeedbackTopicListener extends JmsListener<Object> {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderFeedbackTopicListener.class);

	private AtomicBoolean isRun = new AtomicBoolean(false);

	@Autowired
	private OrderFeedbackService orderFeedbackService;
	
	private List<OrderConfig> configList;
	
	public OrderFeedbackTopicListener() {
		logger.info("OrderFeedbackTopicListener is created");
	}
	
	@Override
	public void messageHandler(Object msg) throws Exception {

		if (isRun.compareAndSet(false, true)) {
			
			logger.info("begin feedback");
			
			try {
				orderFeedbackService.feedback(configList);
			} catch (Exception exp) {
				logger.error("Feedback error:", exp);
				throw new RuntimeException(exp.getMessage());
			} finally {
				isRun.set(false);
				logger.info("Feedback finished.");
			}

		}
	}

	public void setConfigList(List<OrderConfig> configList) {
		this.configList = configList;
	}

}
