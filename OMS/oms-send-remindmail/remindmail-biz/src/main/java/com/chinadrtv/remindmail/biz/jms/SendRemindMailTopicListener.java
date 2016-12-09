package com.chinadrtv.remindmail.biz.jms;

import com.chinadrtv.remindmail.service.MailDetailsService;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-27
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 * 催送货邮件定时调度
 */
public class SendRemindMailTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SendRemindMailTopicListener.class);
    public SendRemindMailTopicListener()
    {
        logger.info("SendRemindMailTopicListener is created.");
    }
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private MailDetailsService mailDetailsService;

    @Override
    public void messageHandler(Object msg) throws Exception {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            //定时消息处理
            try
            {
                mailDetailsService.sendMailDetails();

            }catch (Exception exp)
            {
                logger.error("SendRemindMailTopicListener error:", exp);
            }
            finally {
                isRun.set(false);
                logger.info("SendRemindMailTopicListener end");
            }
        }
        else
        {
            logger.error("SendRemindMailTopicListener is running!!!");
        }
    }


}
