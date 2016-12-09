package com.chinadrtv.runtime.test;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.chinadrtv.runtime.jms.receive.JmsReceiver;

import static java.lang.Thread.*;

/**
 * 类名称
 * 功能描述：
 * User: leo
 * Date: 13-10-21
 * Time: 下午6:18
 */
public class MqReceiveTest {

    //<bean id="jmsFactoryReceive" class="org.apache.activemq.pool.PooledConnectionFactory"

    private JmsReceiver jmsConfigCenterTopicReceive;

    //@Before
    public void init() {
        //classpath*:mq/**/mq-receive.xml
        String[] contextFileArr = {"classpath*:mq/**/mq-receive.xml"};
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            jmsConfigCenterTopicReceive = (JmsReceiver) appCont.getBean("jmsConfigCenterTopicReceive");
            //jmsQueueSender = (JmsQueueSender) appCont.getBean("jmsQueueSender");
            System.out.println("init is ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //@Test
    public void testReceiveMessage() {

        System.out.println("test");

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





    }

}
