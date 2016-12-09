package com.chinadrtv.scheduler.runtime.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chinadrtv.runtime.jms.send.JmsQueueSender;
import com.chinadrtv.runtime.jms.send.JmsTopicSender;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath*:applicationContext-mq-send.xml" })
public class JmsSendeTest {

	/*@Autowired
	public JmsTopicSender jmsTopicSender;

	@Autowired
	public JmsQueueSender jmsQueueSender;

	@Test
	public void testSendTopicMessage() {
		for (int i = 1; i <= 5; i++) {
			jmsTopicSender.convertAndSend("scheduler.topic", "hello world! topic!!!");
			jmsTopicSender.execute(new SessionCallback<TextMessage>() {

				@Override
				public TextMessage doInJms(Session session) throws JMSException {
					System.out.println("hashCode::::::::::::::"+session.hashCode()+"");
					return null;
				}
			});
		}
	}

	@Test
	public void testSendQueueMessage() {
		for (int i = 0; i < 10; i++) {
			jmsQueueSender.convertAndSend("scheduler.queue", "hello world! queue!!!");
			
			jmsQueueSender.execute(new SessionCallback<TextMessage>() {

				@Override
				public TextMessage doInJms(Session session) throws JMSException {
					System.out.println("QUQUQUhashCode::::::::::::::"+session.hashCode()+"");
					return null;
				}
			});
		}
	}*/

}
