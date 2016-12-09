package com.chinadrtv.amazon.common.integration.jms;

import java.util.Map;

public interface SendMessage {

   /**
    * 发送信息
    * 
    * @param param 发送消息
    * @param topicName 队列名称
    */
    public void sendTopic(Map<String, String> param,String topicName);
}
