/*
 * @(#)JunitTest.java 1.0 2013-5-13上午10:21:26
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-13 上午10:21:26
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext*.xml")
@TransactionConfiguration
@Transactional
public class JunitTestBase extends AbstractJUnit4SpringContextTests {

}
