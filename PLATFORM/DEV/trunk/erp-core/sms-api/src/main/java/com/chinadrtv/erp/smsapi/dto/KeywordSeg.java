/*
 * @(#)KeywordSeg.java 1.0 2013-5-31下午10:43:14
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-31 下午10:43:14
 * 
 */
@XStreamAlias("keywordSeg")
public class KeywordSeg {
	@XStreamAlias("keyword")
	private String keyword;
	@XStreamAlias("KwTypeName")
	private String KwTypeName;
	@XStreamAlias("type")
	private String type;
	@XStreamAlias("replacevalue")
	private String replacevalue;

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the replacevalue
	 */
	public String getReplacevalue() {
		return replacevalue;
	}

	/**
	 * @param replacevalue
	 *            the replacevalue to set
	 */
	public void setReplacevalue(String replacevalue) {
		this.replacevalue = replacevalue;
	}

	/**
	 * @return the kwTypeName
	 */
	public String getKwTypeName() {
		return KwTypeName;
	}

	/**
	 * @param kwTypeName the kwTypeName to set
	 */
	public void setKwTypeName(String kwTypeName) {
		KwTypeName = kwTypeName;
	}

}
