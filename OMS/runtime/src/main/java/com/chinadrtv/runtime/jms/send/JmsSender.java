package com.chinadrtv.runtime.jms.send;

import java.io.Serializable;
import java.util.Map;

import com.chinadrtv.common.exception.PaffJmsException;

public interface JmsSender {
    /**
     * 消息转换发送
     * 
     * @param destinationName
     * @param message
     * @throws PaffJmsException
     */
    public void sendConvertMessage(String destinationName, Serializable message)
                                                                                throws PaffJmsException;

    /**
     * 消息附加属性发送
     * 
     * @param destinationName
     * @param message
     * @param msgProperties
     * @throws PaffJmsException
     */
    public void sendConvertMessage(String destinationName, Serializable message,
                                   Map<String, Object> msgProperties) throws PaffJmsException;
}
