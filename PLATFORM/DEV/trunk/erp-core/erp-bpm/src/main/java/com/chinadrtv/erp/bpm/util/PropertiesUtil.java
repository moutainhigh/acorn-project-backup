/*
 * @(#)PropertiesUtil.java 1.0 2013-2-20上午11:12:42
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.bpm.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-20 上午11:12:42
 * 
 */
public class PropertiesUtil {
	public static Properties props = new Properties();

	public static String getFtpPort() {

		try {

			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("system.properties");
			props.load(in);
			String value = props.getProperty("ftp.port");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpName() {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("system.properties");
			props.load(in);
			String value = props.getProperty("ftp.name");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpUrl() {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("system.properties");
			props.load(in);
			String value = props.getProperty("ftp.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpPass() {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("system.properties");
			props.load(in);
			String value = props.getProperty("ftp.pass");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpFile() {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("system.properties");
			props.load(in);
			String value = props.getProperty("ftp.file");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getSmsCsvPath() {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream("system.properties");
			props.load(in);
			String value = props.getProperty("sms.csvPath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
