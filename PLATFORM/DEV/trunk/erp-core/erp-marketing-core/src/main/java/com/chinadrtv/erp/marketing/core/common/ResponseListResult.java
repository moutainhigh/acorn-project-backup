/*
 * @(#)ResponseResult.java 1.0 2013-5-8下午2:30:08
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.core.common;

import java.util.List;

/**
 * <dl>
 *    <dt><b>Title:接口响应结果</b></dt>
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
 * @since 2013-5-8 下午2:30:08 
 * 
 */
public class ResponseListResult extends ResponseResult {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	
	private List result;

	/**
	 * @return the result
	 */
	public List getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List result) {
		this.result = result;
	}
	
	
}
