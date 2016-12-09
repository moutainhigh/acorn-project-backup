/*
 * @(#)ParameterLong.java 1.0 2013-3-25下午1:22:42
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.dao.query;

import org.hibernate.Query;

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
 * @since 2013-3-25 下午1:22:42 
 * 
 */
public class ParameterLong extends AbstractParameter<Long> {

	public ParameterLong(String name, Long value) {
		super(name, value);
	}

	public void setParameterValue(Query q) {
		q.setLong(this.getName(), this.getValue());
	}

}
