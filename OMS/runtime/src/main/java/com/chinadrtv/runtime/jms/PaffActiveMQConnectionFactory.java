package com.chinadrtv.runtime.jms;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;

import com.chinadrtv.common.context.SystemContext;

public class PaffActiveMQConnectionFactory extends ActiveMQConnectionFactory {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PaffActiveMQConnectionFactory.class);
    private static final String BROKER_URL = "brokerURL";
    @SuppressWarnings("unused")
    private String key;

    public PaffActiveMQConnectionFactory()
    {
        logger.debug("PaffActiveMQConnectionFactory is created");
    }

    public void setKey(String key) {
        this.key = key;
        String url = SystemContext.get(key);
        if (StringUtils.isBlank(url)) {
            url = System.getProperty(BROKER_URL);
        }
        //url = "tcp://192.168.1.201:61616";//"tcp://localhost:61616";
        //url = "failover:(tcp://192.168.75.213:61616,tcp://192.168.75.213:61617)";
        logger.info("mq url:"+url);
        super.setBrokerURL(url.trim());
    }

}
