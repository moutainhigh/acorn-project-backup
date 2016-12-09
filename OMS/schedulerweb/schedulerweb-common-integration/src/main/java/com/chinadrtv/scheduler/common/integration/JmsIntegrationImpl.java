package com.chinadrtv.scheduler.common.integration;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.exception.PaffJmsException;
import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.runtime.jms.send.JmsTopicSender;

/**
 * 
 * @author xieen
 * @version $Id: JmsIntegrationImpl.java, v 0.1 2013-7-25 上午8:42:08 xieen Exp $
 */
public class JmsIntegrationImpl implements JmsIntegration {
    Logger                 logger = LoggerFactory.getLogger(LOG_TYPE.ACORN_SERVICE.val);
    private JmsTopicSender jmsTopicSender;

    private String         destinationJmsTopic;

    public void setJmsTopicSender(JmsTopicSender jmsTopicSender) {
        this.jmsTopicSender = jmsTopicSender;
    }

    public void setDestinationJmsTopic(String destinationJmsTopic) {
        this.destinationJmsTopic = destinationJmsTopic;
    }

    public void sendJmsMessage(HashMap<String, Object> jsmMessage) {
        try {
            logger.info("send jms message", jsmMessage);
            jmsTopicSender.sendConvertMessage(destinationJmsTopic, jsmMessage);
        } catch (PaffJmsException e) {
            logger.warn("Topic send message error", e);
        }
    }

}
