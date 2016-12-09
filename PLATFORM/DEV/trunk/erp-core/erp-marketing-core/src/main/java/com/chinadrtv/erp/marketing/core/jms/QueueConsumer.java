package com.chinadrtv.erp.marketing.core.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.chinadrtv.erp.message.AssignMessage;

/**
 * Date: 2008-8-28
 * Time: 17:10:34
 */
public class QueueConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(QueueConsumer.class);
	
	private JmsTemplate template;
	
	@Autowired
	private QueueCountMessageProducer producerCount;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public AssignMessage receive(String jobId) throws JMSException {
    	logger.info("********************准备取数******************* Queue : "+jobId);
		
//		MessageConsumer consumer = ConsumerHelper.getInstance(jobId,template);
		
		Message message = template.receive(jobId) ;//consumer.receive(100);//consumer.receiveNoWait();
		
		if(message!=null  && message instanceof ObjectMessage){
			ObjectMessage objMessage = (ObjectMessage)message;
			AssignMessage assignMessage = (AssignMessage)objMessage.getObject();
			assignMessage.setStatus(1);
			
//			producerCount.send(assignMessage);
			return assignMessage;
		}
		
		return null;
	}
    
}
