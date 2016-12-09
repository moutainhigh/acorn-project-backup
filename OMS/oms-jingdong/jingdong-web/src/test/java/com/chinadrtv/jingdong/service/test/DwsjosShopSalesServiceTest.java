/*
 * @(#)DwsjosShopSalesServiceTest.java 1.0 2014-5-20下午3:47:41
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.jingdong.service.test;

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

import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.chinadrtv.jingdong.service.DwsjosShopSalesService;

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
 * @since 2014-5-20 下午3:47:41 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/applicationContext*.xml", "classpath*:spring/applicationContext*.xml"})
@TransactionConfiguration
@Transactional
public class DwsjosShopSalesServiceTest extends TestCase{

	@Autowired
	private DwsjosShopSalesService dwsjosShopSalesService;
	//@Autowired
	private List<JingdongOrderConfig> cfgList;
	@Autowired
	private ApplicationContext applicationContext;
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback(false)
	public void testLoadYearlyData() throws Exception {
		
		cfgList = (List<JingdongOrderConfig>) applicationContext.getBean("cfgList");
		
		Calendar calendar = Calendar.getInstance();
		
		int daysOfYear = calendar.get(Calendar.DAY_OF_YEAR);
		for(int i=daysOfYear; i > 0; i--) {
			calendar.add(Calendar.DATE, -1);
			Date date = calendar.getTime();
			
			try {
				dwsjosShopSalesService.importPv(cfgList, date);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}	
		}
		
	}
}
