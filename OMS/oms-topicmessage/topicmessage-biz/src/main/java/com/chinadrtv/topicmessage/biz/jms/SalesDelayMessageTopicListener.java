package com.chinadrtv.topicmessage.biz.jms;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.topicmessage.service.DetectSalesMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: xuzk
 * Date: 14-11-14
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */
public class SalesDelayMessageTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SalesDelayMessageTopicListener.class);

    public SalesDelayMessageTopicListener()
    {
        logger.debug("SalesDelayMessageTopicListener is created!");
    }

    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private DetectSalesMessageService detectSalesMessageService;

    @Override
    public void messageHandler(Object o) throws Exception {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            //定时消息处理
            try
            {
                logger.info("SalesDelayMessageTopicListener begin....");
                detectSalesMessageService.delaySalesTasks();

            }catch (Exception e)
            {
                logger.error("SalesDelayMessageTopicListener error:", e);
            }
            finally {
                isRun.set(false);
                logger.info("SalesDelayMessageTopicListener end......");
            }
        }
        else
        {
            logger.error("SalesDelayMessageTopicListener is running!!!");
        }
    }
}
