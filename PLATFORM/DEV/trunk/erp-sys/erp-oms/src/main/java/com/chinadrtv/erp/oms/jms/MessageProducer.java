package com.chinadrtv.erp.oms.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.JMSException;


/**
 * 发送ＪＭＳ　消息
 *  
 * @author haoleitao
 * @date 2013-4-27 上午11:29:34
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
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
