package com.chinadrtv.scheduler.common.integration.test;

//import com.chinadrtv.runtime.jms.send.JmsQueueSender;
//import com.chinadrtv.runtime.jms.send.JmsTopicSender;
import org.junit.Before;
import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 类名称
 * 功能描述：
 * User: leo
 * Date: 13-10-21
 * Time: 下午6:09
 */
public class SchedulerReceiverTest {

    //private JmsTopicReceive jmsTopicReceive;

    //@Before
    public void init() {

        //String[] contextFileArr = { "classpath*:/spring/applicationContext*.xml",
        //        "classpath*:/mybatis-config.xml" };

        String[] contextFileArr = {"classpath*:/spring/applicationContext*.xml",
                                   "classpath*:/mq/mq-*.xml"};
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            //jmsTopicSender = (JmsTopicSender) appCont.getBean("jmsTopicSender");
            //jmsQueueSender = (JmsQueueSender) appCont.getBean("jmsQueueSender");
            System.out.println("init is ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //@Test
    public  void Receive(){
        System.out.println("test");
    }

}
