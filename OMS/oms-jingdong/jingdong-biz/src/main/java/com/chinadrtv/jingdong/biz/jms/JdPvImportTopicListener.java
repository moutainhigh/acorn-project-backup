/*
 * @(#)JdPvImportTopicListener.java 1.0 2014-5-20下午3:10:06
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.jingdong.biz.jms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.DwsjosShopSalesService;
import com.chinadrtv.runtime.jms.receive.JmsListener;

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
 * @since 2014-5-20 下午3:10:06 
 * 
 */
public class JdPvImportTopicListener extends JmsListener<Object>{
	
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JdPvImportTopicListener.class);

	@Autowired
	private DwsjosShopSalesService dwsjosShopSalesService;
	
	private AtomicBoolean isRun=new AtomicBoolean(false);
	
    private List<JingdongOrderConfig> pvList;
	
	/** 
	 * <p>Title: messageHandler</p>
	 * <p>Description: </p>
	 * @param msg
	 * @throws Exception
	 * @see com.chinadrtv.runtime.jms.receive.JmsListener#messageHandler(java.lang.Object)
	 */ 
	@Override
	public void messageHandler(Object msg) throws Exception {
		 
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date yestorday = calendar.getTime();
				
		if (isRun.compareAndSet(false, true)) {
			
			try {
				dwsjosShopSalesService.importPv(pvList, yestorday);
			} catch (Exception exp) {
				logger.error("importing error:", exp);
			} finally {
				isRun.set(false);
			}
		} else {
			logger.error("importing is running!");
		}
	}

	
	public List<JingdongOrderConfig> getPvList() {
		return pvList;
	}
	public void setPvList(List<JingdongOrderConfig> pvList) {
		this.pvList = pvList;
	}
}
