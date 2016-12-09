/*
 * @(#)IcbcOrderInputServiceTest.java 1.0 2014-5-19下午4:07:39
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.gonghang.test;

import java.util.Date;
import java.util.Random;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinadrtv.gonghang.service.OrderImportService;

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
 * @since 2014-5-19 下午4:07:39 
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml", "classpath:spring/applicationContext*.xml"})
@TransactionConfiguration
@Transactional
public class IcbcOrderInputServiceTest extends TestCase {

	@Autowired
	private OrderImportService icbcOrderInputService;
	
	@Test
	public void test1() throws Exception {
		icbcOrderInputService.input(null, new Date(), new Date());
	}
	
	@Test
	public void test2() {
		Random rnd = new Random(10000000);
		Integer r = rnd.nextInt();
		System.out.println(r);
	}
}
