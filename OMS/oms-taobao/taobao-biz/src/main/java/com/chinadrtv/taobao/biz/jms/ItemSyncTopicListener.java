/*
 * @(#)StockSyncTopicListener.java 1.0 2014-7-14上午10:19:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.taobao.biz.jms;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.runtime.jms.receive.JmsListener;
import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.service.StockSyncService;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-7-14 上午10:19:28 
 * 
 */
public class ItemSyncTopicListener extends JmsListener<Object>{
	
	private static transient final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ItemSyncTopicListener.class);

	private List<TaobaoOrderConfig> configList;
	
	@Autowired
	private StockSyncService stockSyncService;
	
	private AtomicBoolean isRunning = new AtomicBoolean();
	
	/** 
	 * <p>Title: messageHandler</p>
	 * <p>Description: </p>
	 * @param msg
	 * @throws Exception
	 * @see com.chinadrtv.runtime.jms.receive.JmsListener#messageHandler(java.lang.Object)
	 */ 
	@Override
	public void messageHandler(Object msg) throws Exception {
		if(isRunning.get()) {
			return;
		}
		
		try {
			isRunning.set(true);
			
			stockSyncService.syncItemList(configList);
			
		} catch (Exception e) {
			logger.error("item synchronlize error ", e);
		} finally {
			isRunning.set(false);
		}
	}

	
	public void setConfigList(List<TaobaoOrderConfig> configList) {
		this.configList = configList;
	}
}
