/**
 * 
 */
package com.chinadrtv.erp.oms.util;

import java.io.Serializable;
import java.util.Properties;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.chinadrtv.erp.model.PreTrade;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/*
 * JMS助手
 * @author haoleitao
 * @date 2012-12-28 下午5:45:08
 *
 */
public class JMSUtil {
	private static final Logger log = LoggerFactory.getLogger(JMSUtil.class);
	private QueueConnection connection;
	private QueueSession session;
	private Queue queue;
	public void setupConnection() throws Exception
	{
		log.info("Connection Starting...");
		
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");   
		properties.put(Context.PROVIDER_URL, "jnp://127.0.0.1:1099");
		InitialContext context = new InitialContext(properties);
		
		QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
		connection = factory.createQueueConnection();
		queue = (Queue) context.lookup("queue/quickstart_helloworld_Request_gw");
		session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		connection.start();
		
		log.info("Connection Started");
	}

	public void stop() throws Exception
	{
		if(connection != null) connection.stop();
		if(session != null) session.close();
		if(connection != null) connection.close();
	}

	public void sendMessage(Queue queue,String text) throws Exception
	{
		QueueSender sender = session.createSender(queue);
		Message message = session.createTextMessage(text);
		sender.send(message);
		sender.close();
	}
	
	public void sendObjectMessage(Object object) throws Exception
	{
		QueueSender sender = session.createSender(queue);
		Message message = session.createObjectMessage((Serializable) object);
		sender.send(message);
		sender.close();
	}
	
	
	public static void main(String[] args) throws Exception
	{
		JMSUtil main = new JMSUtil();
		main.setupConnection();
		PreTrade pt = new PreTrade();
		pt.setId(Long.valueOf(""));
		main.sendObjectMessage(pt);
		main.sendMessage(main.queue,"Llu, miss you, afa");
		main.stop();
	}

}
