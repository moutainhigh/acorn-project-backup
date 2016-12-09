/*
 * @(#)PropertiesUtils.java 1.0 2013-11-8下午1:39:03
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-11-8 下午1:39:03
 * 
 */
public class PropertiesUtils {

	public static String getValue(String key) {
		Resource resource = new ClassPathResource("/system.properties");
		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
			return props.getProperty(key);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
