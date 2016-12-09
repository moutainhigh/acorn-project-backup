package com.chinadrtv.yuantong.biz.jms;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.yuantong.service.CreateOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-11
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
public class YuantongTopicListener extends JmsListener<Object> {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(YuantongTopicListener.class);
    public YuantongTopicListener()
    {
        logger.info("YuantongTopicListener is created.");
    }
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private CreateOrderService createOrderService;
    @Autowired
    private Date lastHandleTime;

	@Override
	public void messageHandler(Object msg) throws Exception {
		
		if (isRun.compareAndSet(false, true)) {
		
			try {
				logger.info("YuantongTopicListener begin...");
				createOrderService.createOrder();
			} catch (Exception exp) {
				logger.error("YuantongTopicListener post error:", exp);
			} finally {
				isRun.set(false);
				lastHandleTime = new Date();
				logger.info("end post lastHandleTime" + lastHandleTime);
			}
		} else {
			logger.error("feed back is running!!!");
		}
	}
}
