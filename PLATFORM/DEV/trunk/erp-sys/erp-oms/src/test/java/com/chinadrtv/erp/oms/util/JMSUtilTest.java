/**
 * 
 */
package com.chinadrtv.erp.oms.util;

import static org.junit.Assert.assertTrue;

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

import org.junit.Test;

import java.util.Properties;

/*
 * SMSUtil 测试
 * @author haoleitao
 * @date 2012-12-28 下午5:45:08
 *
 */
public class JMSUtilTest {
	
	private QueueConnection connection;
	private QueueSession session;
	private Queue queue;
	public void setupConnection() throws Exception
	{
		System.out.println("Connection Starting...");
		
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
		
		System.out.println("Connection Started");
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
	
	@Test
	public void test(){
	  assertTrue(true);
	}


}
