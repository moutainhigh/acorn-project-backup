package com.chinadrtv.runtime.test;

import com.chinadrtv.runtime.jms.send.JmsQueueSender;
import com.chinadrtv.runtime.jms.send.JmsTopicSender;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;


/**
 * MQ发送测试
 * 功能描述：
 * User: leo
 * Date: 13-10-20
 * Time: 下午3:36
 */
public class MqSendTest {

    private JmsTopicSender jmsTopicSender;

    private JmsQueueSender jmsQueueSender;

    //@Before
    public void init() {
        String[] contextFileArr = {"classpath*:/mq/mq-*.xml"};
        try {
            ApplicationContext appCont = new ClassPathXmlApplicationContext(contextFileArr);
            jmsTopicSender = (JmsTopicSender) appCont.getBean("jmsTopicSender");
            jmsQueueSender = (JmsQueueSender) appCont.getBean("jmsQueueSender");
            System.out.println("init is ok");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //@Test
    public void testSendTopicMessage() {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("systemId", 2);
            String msg = "发送主题消息topic1!";
            jmsTopicSender.sendConvertMessage("t_configcenter", msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //@Test
    public void testSendQueueMessage() {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("aaa", 2);
            String msg = "发送队列消息topic1!";
            jmsQueueSender.sendConvertMessage("q_schedule_xacenter", msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
