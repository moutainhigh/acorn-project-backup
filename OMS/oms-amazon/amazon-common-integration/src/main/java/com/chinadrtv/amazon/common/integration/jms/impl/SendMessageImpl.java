package com.chinadrtv.amazon.common.integration.jms.impl;

import java.util.Map;

import com.chinadrtv.amazon.common.integration.jms.SendMessage;
import com.chinadrtv.common.exception.PaffJmsException;
import com.chinadrtv.runtime.jms.send.JmsTopicSender;


public class SendMessageImpl implements SendMessage {

    private JmsTopicSender      jmsTopicSender;

    //private static final Logger logger = LoggerFactory.getLogger(LOG_TYPE.PAFF_BIZ.val);

    @Override
    public void sendTopic(Map<String, String> param, String topicName) {
        try {
            //jmsTopicSender.sendConvertMessage("q_paycore_circular_transferAccount",param);
        } catch (PaffJmsException e) {
            //logger.error(e.getMessage(), e);
        }
    }
}
