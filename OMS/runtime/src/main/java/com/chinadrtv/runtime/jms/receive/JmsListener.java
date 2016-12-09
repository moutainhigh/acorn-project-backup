package com.chinadrtv.runtime.jms.receive;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.SimpleMessageConverter;

//import com.chinadrtv.common.log.LOG_TYPE;

public abstract class JmsListener<T> implements MessageListener {

    protected Logger               logger    = LoggerFactory.getLogger("LOG_TYPE.PAFF_COMMON.val");

    private Map<String, Object>    params;

    private SimpleMessageConverter converter = new SimpleMessageConverter();

    protected boolean pre(Map<String, Object> param) {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(Message msg) {
       //获取消息属性
        try {
            params =  new HashMap<String, Object>();
            Enumeration enu = msg.getPropertyNames();
            while (enu.hasMoreElements()) {
                String key = (String) enu.nextElement();
                params.put(key, msg.getObjectProperty(key));
            }
        } catch (JMSException e1) {
            logger.error("获取消息属性失败!" + e1.getMessage(), e1);
            throw new RuntimeException(e1);
        }

        logger.info("==>receive message:" + msg);
        //捕获获取消息流异常
        Object tempMsg = null;
        try {
            tempMsg = converter.fromMessage(msg);
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        //捕获类型转换异常
        T temp = null;
        try {
            temp = (T) tempMsg;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        //业务处理
        try {
            messageHandler(temp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        logger.info("==>messageHandler over");

    }

    public abstract void messageHandler(T msg) throws Exception;

    public Map<String, Object> getParams() {
        return params;
    }

}
