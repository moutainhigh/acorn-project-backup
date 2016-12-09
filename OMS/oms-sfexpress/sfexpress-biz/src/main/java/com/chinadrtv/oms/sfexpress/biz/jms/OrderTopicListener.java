package com.chinadrtv.oms.sfexpress.biz.jms;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.oms.sfexpress.service.ShipmentService;
import com.chinadrtv.runtime.jms.receive.JmsListener;

/**
 * Created with IntelliJ IDEA. User: liukuan Date: 13-11-11 Time: 下午1:45 To
 * change this template use File | Settings | File Templates.
 */
public class OrderTopicListener extends JmsListener<Object> {

	public OrderTopicListener() {
		logger.info("OrderTopicListener is created.");
	}

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderTopicListener.class);

	private AtomicBoolean isRun = new AtomicBoolean(false);

	@Autowired
	private ShipmentService shipmentService;
	@Autowired
	private Date lastHandleTime;

	@Override
	public void messageHandler(Object msg) throws Exception {

		if (isRun.compareAndSet(false, true)) {
			logger.debug("OrderTopicListener begin...");
			try {
				shipmentService.createWaybill();
			} catch (Exception exp) {
				logger.error("OrderTopicListener post error:", exp);
			} finally {
				isRun.set(false);
				lastHandleTime = new Date();
				logger.debug("end post lastHandleTime" + lastHandleTime);
			}
		} else {
			logger.debug("OrderTopicListener is running!");
		}
	}
}
