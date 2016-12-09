package com.chinadrtv.runtime.jms.send;

import java.io.Serializable;
import java.util.Map;

public class JmsSendTool {

    private static JmsTopicSender jmsTopicSender;
    private static JmsQueueSender jmsQueueSender;

    private static void sendMessage(String dest, Serializable msg, Map<String, Object> properties,
                                    boolean isTopic) {
        if (isTopic) {
            jmsTopicSender.sendConvertMessage(dest, msg, properties);
        } else {
            jmsQueueSender.sendConvertMessage(dest, msg, properties);
        }
    }

    /**
     * 发送队列消息,附加消息属性
     *
     * @param dest
     * @param msg
     * @param properties
     */
    public static void sendQueueMessageWithProperties(String dest, Serializable msg,
                                                      Map<String, Object> properties) {
        sendMessage(dest, msg, properties, false);
    }

    /**
     * 发送主题消息,附加消息属性
     *
     * @param dest
     * @param msg
     * @param properties
     */
    public static void sendTopicMessageWithProperties(String dest, Serializable msg,
                                                      Map<String, Object> properties) {
        sendMessage(dest, msg, properties, true);
    }

    /**
     * 发送队列消息
     *
     * @param dest
     * @param msg
     */
    public static void sendQueueMessage(String dest, Serializable msg) {
        sendMessage(dest, msg, null, false);
    }

    /**
     * 发送主题消息
     *
     * @param dest
     * @param msg
     */
    public static void sendTopicMessage(String dest, Serializable msg) {
        sendMessage(dest, msg, null, true);
    }

    public void setJmsTopicSender(JmsTopicSender jmsTopicSender) {
        JmsSendTool.jmsTopicSender = jmsTopicSender;
    }

    public void setJmsQueueSender(JmsQueueSender jmsQueueSender) {
        JmsSendTool.jmsQueueSender = jmsQueueSender;
    }

}
