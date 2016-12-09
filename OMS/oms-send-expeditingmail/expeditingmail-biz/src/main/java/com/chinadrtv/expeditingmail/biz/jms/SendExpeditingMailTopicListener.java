package com.chinadrtv.expeditingmail.biz.jms;

import com.chinadrtv.expeditingmail.service.SendExpeditingMailService;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-7
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 * 超时效催送货邮件
 */
public class SendExpeditingMailTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SendExpeditingMailTopicListener.class);
    public SendExpeditingMailTopicListener()
    {
        logger.info("SendExpeditingMailTopicListener is created.");
    }
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private SendExpeditingMailService sendExpeditionMailService;

    @Override
    public void messageHandler(Object msg) throws Exception {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            //定时消息处理
            try
            {
                sendExpeditionMailService.timingSendMail();

            }catch (Exception exp)
            {
                logger.error("SendExpeditingMailTopicListener error:", exp);
            }
            finally {
                isRun.set(false);
                logger.info("SendExpeditingMailTopicListener end");
            }
        }
        else
        {
            logger.error("SendExpeditingMailTopicListener is running!!!");
        }
    }

}
