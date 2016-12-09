package com.chinadrtv.scheduler.runtime.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyJmsReceiverListener implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		try {
			System.out.println(((TextMessage) arg0).getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
