package com.chinadrtv.erp.marketing.core.jms;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.chinadrtv.erp.message.AssignMessage;

/**
 * 
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
 * @since 2013-6-2 下午3:17:04 
 *
 */
public class QueueMessageProducer  implements MessageListener{
	
	private static final Logger logger = LoggerFactory.getLogger(QueueMessageProducer.class);
	
	private static Map<String, MessageProducer> producerMap = new HashMap<String, MessageProducer>();

    private JmsTemplate template;

    private Queue destination;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void setDestination(Queue destination) {
        this.destination = destination;
    }
    
    

    public AssignMessage send(AssignMessage assignMessage) throws JMSException {
    	

    	Connection connection = null;
    	MessageProducer producer = null;
    	
    	AssignMessage result= null;
            connection = template.getConnectionFactory().createConnection();
            connection.start();
            logger.info("create mq session："+assignMessage.getJobId());
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //Setup a message producer to send message to the queue the server is consuming from
            logger.info("createProducer："+assignMessage.getJobId());
            producer = session.createProducer(this.destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //Create a temporary queue that this client will listen for responses on then create a consumer
            //that consumes message from this temporary queue...for a real application a client should reuse
            //the same temp queue for each message to the server...one temp queue per client
            Destination tempDest = session.createTemporaryQueue();
            logger.info("createConsumer："+assignMessage.getJobId());
            MessageConsumer responseConsumer = session.createConsumer(tempDest);

            //This class will handle the messages to the temp queue as well
//            responseConsumer.setMessageListener(this);

            //Now create the actual message you want to send
             ObjectMessage requestMessage = session.createObjectMessage(assignMessage);
             logger.info("createObjectMessage："+assignMessage.getJobId());
            //Set the reply to field to the temp queue you created above, this is the queue the server
            //will respond to
            requestMessage.setJMSReplyTo(tempDest);

            //Set a correlation ID so when you get a response you know which sent message the response is for
            //If there is never more than one outstanding message to the server then the
            //same correlation ID can be used for all the messages...if there is more than one outstanding
            //message to the server you would presumably want to associate the correlation ID with this
            //message somehow...a Map works good
            String correlationId = this.createRandomString();
            requestMessage.setJMSCorrelationID(correlationId);
            
            logger.info("取数为0发送jms消息,告知服务器端创建队列或者补充数："+assignMessage.getJobId());
            producer.send(requestMessage);
            
            Message msg = responseConsumer.receive(60000);
            
            if(msg instanceof ObjectMessage ){
    			ObjectMessage response = (ObjectMessage)msg;
    			
    			result = (AssignMessage)response.getObject();
    		}
            
    	
    	return result;
    }
    

	/* (非 Javadoc)
	* <p>Title: onMessage</p>
	* <p>Description: </p>
	* @param arg0
	* @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	*/ 
	@Override
	public void onMessage(Message msg) {
        logger.debug("接收到服务器响应=====================");
		
		if(msg instanceof ObjectMessage ){
			ObjectMessage response = (ObjectMessage)msg;
			try {
				logger.debug("接收到服务器响应："+((AssignMessage)response.getObject()).getMessge());
			} catch (JMSException e) {
                logger.error(e.getMessage(),e); //e.printStackTrace();
			}
		}
		
	}

	 private String createRandomString() {
	        Random random = new Random(System.currentTimeMillis());
	        long randomLong = random.nextLong();
	        return Long.toHexString(randomLong);
	    }
}
