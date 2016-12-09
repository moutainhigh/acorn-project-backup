/*
 * @(#)ConsumerHelper.java 1.0 2013-6-4上午10:48:05
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-6-4 上午10:48:05 
 * 
 */
public class ConsumerHelper {
	
	private final static Map<String, MessageConsumer> consumerMap = new HashMap<String, MessageConsumer>();

	
	public static synchronized MessageConsumer getInstance(String jobId,JmsTemplate template) throws JMSException{
		
		MessageConsumer consumer = null;
		if(consumerMap.containsKey(jobId)){
			consumer = consumerMap.get(jobId);
		}else{
			Connection connection = template.getConnectionFactory().createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination jobQueue = session.createQueue(jobId);
            //Setup a message producer to send message to the queue the server is consuming from
            consumer = session.createConsumer(jobQueue);
            consumerMap.put(jobId, consumer);
		}
		
		return consumer;
	}
}
