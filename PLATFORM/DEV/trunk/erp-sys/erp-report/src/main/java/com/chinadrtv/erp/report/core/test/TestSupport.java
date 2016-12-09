package com.chinadrtv.erp.report.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestSupport extends AbstractJUnit4SpringContextTests {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
}
