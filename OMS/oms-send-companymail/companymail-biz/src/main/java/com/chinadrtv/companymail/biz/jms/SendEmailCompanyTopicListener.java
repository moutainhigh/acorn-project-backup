package com.chinadrtv.companymail.biz.jms;

import com.chinadrtv.companymail.service.ShippingLoadService;
import com.chinadrtv.runtime.jms.receive.JmsListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: liukuan
 * Date: 13-11-7
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 * 发包清单邮件自动传送
 */
public class SendEmailCompanyTopicListener extends JmsListener {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SendEmailCompanyTopicListener.class);
    public SendEmailCompanyTopicListener()
    {
        logger.info("SendEmailCompanyTopicListener is created.");
    }
    private AtomicBoolean isRun=new AtomicBoolean(false);

    @Autowired
    private ShippingLoadService shippingLoadService;

    @Override
    public void messageHandler(Object msg) throws Exception {
        //如果有在处理，那么直接忽略此消息
        if(isRun.compareAndSet(false,true))
        {
            //定时消息处理
            try
            {
                shippingLoadService.sendEmailCompany();

            }catch (Exception exp)
            {
                logger.error("SendEmailCompanyTopicListener error:", exp);
            }
            finally {
                isRun.set(false);
                logger.info("SendEmailCompanyTopicListener end");
            }
        }
        else
        {
            logger.error("SendEmailCompanyTopicListener is running!!!");
        }
    }

}
