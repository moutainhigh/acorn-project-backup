package com.chinadrtv.topicmessage.biz.jms;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.topicmessage.service.DetectSalesMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-27
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 *
 */
public class DetectSalesMessageTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DetectSalesMessageTopicListener.class);
    public DetectSalesMessageTopicListener()
    {
        logger.info("DetectSalesMessageTopicListener is created.");
    }
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private DetectSalesMessageService detectSalesMessageService;
    @Autowired
    private Date lastHandleTime;  //程序执行最后时间

    @Override
    public void messageHandler(Object msg) throws Exception {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            //定时消息处理
            try
            {
                logger.info("DetectSalesMessageTopicListener begin....");
                detectSalesMessageService.detectSalesTopicMessage();

            }catch (Exception e)
            {
                logger.error("DetectSalesMessageTopicListener Exception:", e);
            }
            finally {
                isRun.set(false);
                logger.info("DetectSalesMessageTopicListener end......");
                lastHandleTime = new Date();
            }
        }
        else
        {
            logger.error("DetectSalesMessageTopicListener is running!!!");
        }
    }


}
