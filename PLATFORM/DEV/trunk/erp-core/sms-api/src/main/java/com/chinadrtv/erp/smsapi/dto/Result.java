/*
 * @(#)Result.java 1.0 2013-5-31下午10:42:39
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.dto;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-31 下午10:42:39
 * 
 */
@XStreamAlias("result")
public class Result {

	private List<KeywordSeg> keywordSegs;

	/**
	 * @return the keywordSeg
	 */
	public List<KeywordSeg> getKeywordSeg() {
		return keywordSegs;
	}

	/**
	 * @param keywordSeg
	 *            the keywordSeg to set
	 */
	public void setKeywordSeg(List<KeywordSeg> keywordSegs) {
		this.keywordSegs = keywordSegs;
	}

}
