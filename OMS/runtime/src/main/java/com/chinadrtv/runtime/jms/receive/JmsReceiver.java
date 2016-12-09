package com.chinadrtv.runtime.jms.receive;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class JmsReceiver extends DefaultMessageListenerContainer implements InitializingBean {

    private static final String TOPIC_PREFIX  = "t";
    private static final String QUEUE_PREFIX  = "q";
   // private static final String CONFIG_CENTER = "t_configcenter";

    /**
     * 根据destinationName 如以't'开始为topic,如以q开始为queue
     * @see org.springframework.jms.listener.AbstractMessageListenerContainer#setDestinationName(java.lang.String)
     */
    public void setDestinationName(String destinationName) {
        super.setDestinationName(destinationName);

        if (destinationName.toLowerCase().startsWith(TOPIC_PREFIX)) {
            super.setPubSubDomain(true);
          /*  if (destinationName.toLowerCase().startsWith(CONFIG_CENTER)) {
                super.setSubscriptionDurable(true);
            }*/
        } else if (destinationName.toLowerCase().startsWith(QUEUE_PREFIX)) {
            super.setPubSubDomain(false);
        }
    }

    /**
     * 默认设置为事务策略
     * @see org.springframework.jms.listener.AbstractJmsListeningContainer#afterPropertiesSet()
     */
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        super.setSessionTransacted(true);
    }

}
