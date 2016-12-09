package com.chinadrtv.runtime.jms.send;

import java.io.Serializable;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import com.chinadrtv.common.exception.PaffJmsException;
import com.chinadrtv.common.log.LOG_TYPE;

public class JmsTopicSender implements JmsSender {

    private Logger logger = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");

    private JmsTemplate jmsTemplateTopic;

    public void sendConvertMessage(String destinationName, Serializable message)
            throws PaffJmsException {

        try {
            this.jmsTemplateTopic.convertAndSend(destinationName, message);
        } catch (JmsException jmsException) {
            logger.error(jmsException.getMessage(), jmsException);
            throw new PaffJmsException(jmsException);
        }

    }

    public void sendConvertMessage(String destinationName, Serializable message,
                                   final Map<String, Object> msgProperties) throws PaffJmsException {

        try {
            this.jmsTemplateTopic.convertAndSend(destinationName, message,
                    new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message msg) throws JMSException {
                            if (msgProperties != null) {
                                for (Map.Entry<String, Object> entry : msgProperties.entrySet()) {
                                    msg.setObjectProperty(entry.getKey(), entry.getValue());
                                }
                            }
                            return msg;

                        }
                    });
        } catch (JmsException jmsException) {
            logger.error(jmsException.getMessage(), jmsException);
            throw new PaffJmsException(jmsException);
        }
    }

    /* public void sendMessage(String destinationName, MessageCreator messageCreator) {
         this.jmsTemplateTopic.send(destinationName, messageCreator);
     }*/

    public void setJmsTemplateTopic(JmsTemplate jmsTemplateTopic) {
        this.jmsTemplateTopic = jmsTemplateTopic;
    }

}
