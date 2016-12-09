/*
 * @(#)QueueSizeCounter.java 1.0 2013-6-4上午11:29:34
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.jms.message;

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

public class QueueSizeCounter {

	private MBeanServerConnection mBeanServerConnection;

	private Logger logger = Logger.getLogger(QueueSizeCounter.class);

	public Long getQueueSize(String queueName) {
		Long queueSize = null;
		try {
			ObjectName objectNameRequest = new ObjectName(
					"org.apache.activemq:BrokerName=localhost,Type=Queue,Destination=" + queueName);

			queueSize = (Long) mBeanServerConnection.getAttribute(objectNameRequest, "QueueSize");

			return queueSize;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return queueSize;
	}

	public void setmBeanServerConnection(MBeanServerConnection mBeanServerConnection) {
		this.mBeanServerConnection = mBeanServerConnection;
	}

}