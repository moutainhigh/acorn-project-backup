/*
 * @(#)QueueSizeCounter.java 1.0 2013-6-4上午11:29:34
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.util;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-6-4 上午11:29:34
 * 
 */

public class AmqJmxUtil {

	private MBeanServerConnection mBeanServerConnection;

	private Logger logger = Logger.getLogger(AmqJmxUtil.class);

	public void  removeQueue(String queueName) {
		String brokerNameQuery = "org.apache.activemq:BrokerName=localhost,Type=Broker";
        String removeTopicOperationName = "removeQueue";
        Object[] params = { queueName };
        String[] sig = { "java.lang.String" };
        
        try {
        	ObjectName brokerObjName = new ObjectName(brokerNameQuery); 
			mBeanServerConnection.invoke(brokerObjName, removeTopicOperationName , params, sig);
		} catch (Exception e) {
			logger.info("removeQueue Error:"+e.getMessage());
			e.printStackTrace();
		}
	}

	public void setmBeanServerConnection(MBeanServerConnection mBeanServerConnection) {
		this.mBeanServerConnection = mBeanServerConnection;
	}

}