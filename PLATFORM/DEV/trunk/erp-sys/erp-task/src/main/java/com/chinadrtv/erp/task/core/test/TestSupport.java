package com.chinadrtv.erp.task.core.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestSupport extends AbstractJUnit4SpringContextTests {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
}
