package com.chinadrtv.erp.marketing.jms;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.jms.core.JmsTemplate;
public class MessageConsumer  implements MessageListener {
	

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message arg0) {
		// TODO Auto-generated method stub
	    System.out.println("****************************************");
        System.out.println(arg0);
	}

}
