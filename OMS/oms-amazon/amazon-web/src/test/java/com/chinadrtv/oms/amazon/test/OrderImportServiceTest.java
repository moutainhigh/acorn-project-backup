/*
 * @(#)orderImportServiceTest.java 1.0 2014-5-30下午1:40:23
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.amazon.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.chinadrtv.amazon.model.AmazonOrderConfig;
import com.chinadrtv.amazon.service.OrderImportService;
import com.chinadrtv.amazon.service.impl.OrderImportServiceImpl;

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
 * @since 2014-5-30 下午1:40:23 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:applicationContext*.xml", 
								"classpath*:spring/applicationContext*.xml",
								"classpath*:mq/mq-receive.xml"})
@TransactionConfiguration
@Transactional
public class OrderImportServiceTest extends TestCase {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportServiceTest.class);
	 
	@Autowired
	private OrderImportService orderImportService;
	
	//@Autowired
	private List<AmazonOrderConfig> cfgList;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback(false)
	public void testImport() throws Exception {
		
		cfgList = (List<AmazonOrderConfig>) applicationContext.getBean("cfgList");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date originalDate = sdf.parse("2014-10-26 00:00:00");
		Date today = sdf.parse("2014-10-27 10:00:00");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(originalDate);
		
		Date beginDate = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		Date endDate = calendar.getTime();
		
		try {
			while(endDate.before(today)) {
				
				logger.error("beginDate: " + sdf.format(beginDate) + "\t endDate: " + sdf.format(endDate));
				
				orderImportService.importOrders(cfgList, beginDate, endDate);
				beginDate = calendar.getTime();
				calendar.add(Calendar.DATE, 1);
				endDate = calendar.getTime();
				
				try {
					Thread.sleep(200 * 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.error("beginDate: " + sdf.format(beginDate) + "\t endDate: " + sdf.format(endDate));
			e.printStackTrace();
		}
		
		
	}
}
