package com.chinadrtv.erp.marketing.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.JMSException;



public class MessageProducer {
	private JmsTemplate template;

    private Destination destination;

    public void send(final String message) {
        template.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                Message m = session.createTextMessage(message);
                return m;
            }
        });
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }
	   
}
