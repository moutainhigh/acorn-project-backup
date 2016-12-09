/*
 * @(#)OrderImportServiceTest.java 1.0 2014-5-26下午3:31:28
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.taobao.model.TaobaoOrderConfig;
import com.chinadrtv.taobao.service.OrderImportService;

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
 * @since 2014-5-26 下午3:31:28 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/test/resources/applicationContext*.xml", "classpath*:mq/mq-receive.xml" })
@TransactionConfiguration
@Transactional
public class OrderImportServiceTest extends TestCase {

	@Autowired
	private OrderImportService orderImportService;
	@Autowired
	private ApplicationContext appContext;
	
	private List<TaobaoOrderConfig> cfgList;
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback(false)
	public void test1() {
		
		cfgList = (List<TaobaoOrderConfig>) appContext.getBean("cfgList");
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -3);
		
		orderImportService.importOrders(cfgList, new Date(), calendar.getTime());
	}
}
