package com.chinadrtv.erp.jms.message;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

import com.chinadrtv.erp.message.AssignMessage;

/**
 * Date: 2008-8-28
 * Time: 17:14:23
 */
public class QueueMessageProducer {

    private JmsTemplate template;

    private Queue destination;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void setDestination(Queue destination) {
        this.destination = destination;
    }
    
    
    private static int ackMode;
    private static String clientQueueName;

    private boolean transacted = false;
    private MessageProducer producer;

    static {
        clientQueueName = "JMS-QUEUE-MAIN";//"client.messages";
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }

    public void send(AssignMessage message) {
//    	template.getConnectionFactory().createConnection().createSession(false, 1).createQueue(arg0)
//    	template.execute(new SessionCallback<Object>() {
//    	    public Object doInJms(Session ss) throws JMSException {
//    	        Queue q = ss.createQueue("someQueue");
//    	        MessageProducer prd = ss.createProducer(q);
//    	        Message msg = ss.createTextMessage("..content..");
//    	        prd.send(msg);
//    	        return null;
//    	    }
//    	});
    	
//    	Destination queue = null;
//    	 //取得JNDI上下文和连接
//    	try {
//			queue = template.getConnectionFactory().createConnection().createSession(false, Session.AUTO_ACKNOWLEDGE).createQueue("JMS-QUEUE-MAIN");
//			
//			System.out.println("test-------");
//		} catch (JMSException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//    	
//
//        //template.receive(queue);
//    	
//        template.convertAndSend(queue, message);
    	
    	
    	
    	
    	 ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
         Connection connection;
         try {
             connection = connectionFactory.createConnection();
             connection.start();
             Session session = connection.createSession(transacted, ackMode);
             Destination adminQueue = session.createQueue("JMS-QUEUE-MAIN");

             //Setup a message producer to send message to the queue the server is consuming from
             this.producer = session.createProducer(adminQueue);
             //this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

             //Create a temporary queue that this client will listen for responses on then create a consumer
             //that consumes message from this temporary queue...for a real application a client should reuse
             //the same temp queue for each message to the server...one temp queue per client
//             Destination tempDest = session.createTemporaryQueue();
//             MessageConsumer responseConsumer = session.createConsumer(tempDest);
 //
//             //This class will handle the messages to the temp queue as well
//             responseConsumer.setMessageListener(this);

             //Now create the actual message you want to send
             TextMessage txtMessage = session.createTextMessage();
             txtMessage.setText("MyProtocolMessage");

             //Set the reply to field to the temp queue you created above, this is the queue the server
             //will respond to
//             txtMessage.setJMSReplyTo(tempDest);

             //Set a correlation ID so when you get a response you know which sent message the response is for
             //If there is never more than one outstanding message to the server then the
             //same correlation ID can be used for all the messages...if there is more than one outstanding
             //message to the server you would presumably want to associate the correlation ID with this
             //message somehow...a Map works good
//             String correlationId = this.createRandomString();
//             txtMessage.setJMSCorrelationID(correlationId);
             this.producer.send(txtMessage);
             
         } catch (JMSException e) {
             //Handle the exception appropriately
         	e.printStackTrace();
         }
    }
    
    public static void main(String arg[]){
    	new QueueMessageProducer().send(new AssignMessage());
    }

}
