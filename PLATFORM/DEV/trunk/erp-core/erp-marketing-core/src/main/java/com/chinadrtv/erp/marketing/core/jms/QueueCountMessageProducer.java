package com.chinadrtv.erp.marketing.core.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

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
public class QueueCountMessageProducer{
	
	private static final Logger logger = LoggerFactory.getLogger(QueueCountMessageProducer.class);
	
    private JmsTemplate template;

    private Queue destination;

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }

    public void setDestination(Queue destination) {
        this.destination = destination;
    }
    
    

    public void send(final AssignMessage assignMessage) throws JMSException {
    	long start = System.currentTimeMillis();
    	template.send(destination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(assignMessage);
			}
		});
    	
    	long end = System.currentTimeMillis();
    	
    	logger.info("取到一个数后,发送消息queue="+assignMessage.getJobId()+";用时="+(end-start));
    }
    

}
