package com.chinadrtv.erp.oms.jms;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.jms.core.JmsTemplate;

/**
 * 处理ＪＭＳ消息
 *  
 * @author haoleitao
 * @date 2013-4-27 上午11:30:28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
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
