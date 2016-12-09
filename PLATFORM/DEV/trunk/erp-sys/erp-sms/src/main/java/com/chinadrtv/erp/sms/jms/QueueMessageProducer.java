package com.chinadrtv.erp.sms.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.chinadrtv.sms.model.AssignXml;

/**
 * Date: 2008-8-28 Time: 17:14:23
 */
public class QueueMessageProducer {

	private static final Logger logger = LoggerFactory
			.getLogger(QueueMessageProducer.class);

	private JmsTemplate template;

	private Queue destination;

	/**
	 * 发送消息
	 * 
	 * @Description: TODO
	 * @param assignMessage
	 * @throws JMSException
	 * @return void
	 * @throws
	 */
	public void send(final AssignXml assignXml) throws JMSException {
		long start = System.currentTimeMillis();
		template.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(assignXml);
			}
		});
		long end = System.currentTimeMillis();
		// logger.info("获得请求的xml,发送消息queue=" + assignXml.getXml() + ";用时="
		// + (end - start));
	}

	/**
	 * @return the template
	 */
	public JmsTemplate getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	/**
	 * @return the destination
	 */
	public Queue getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(Queue destination) {
		this.destination = destination;
	}

}
