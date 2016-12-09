/*
 * @(#)AppTestResource.java 1.0 2013-3-13下午3:39:49
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.util;

import java.util.List;

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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-3-13 下午3:39:49 
 * 
 */
public class AppValidationResource {
	
	public static String validationQuery;
	
	//需要校验的datasource
	public static List<String> dbs;
	
	//需要校验的filePath
	public static List<String> files;
	
	//需要检验的http url
	public static List<String> https;

	/**
	 * @return the dbs
	 */
	public  List<String> getDbs() {
		return dbs;
	}

	/**
	 * @param dbs the dbs to set
	 */
	public  void setDbs(List<String> dbs) {
		AppValidationResource.dbs = dbs;
	}

	/**
	 * @return the files
	 */
	public  List<String> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public  void setFiles(List<String> files) {
		AppValidationResource.files = files;
	}

	/**
	 * @return the https
	 */
	public  List<String> getHttps() {
		return https;
	}

	/**
	 * @param https the https to set
	 */
	public  void setHttps(List<String> https) {
		AppValidationResource.https = https;
	}

	/**
	 * @return the validationQuery
	 */
	public  String getValidationQuery() {
		return validationQuery;
	}

	/**
	 * @param validationQuery the validationQuery to set
	 */
	public  void setValidationQuery(String validationQuery) {
		AppValidationResource.validationQuery = validationQuery;
	}
	
	

}
