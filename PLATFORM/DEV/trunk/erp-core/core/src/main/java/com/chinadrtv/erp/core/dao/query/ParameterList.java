/*
 * @(#)ParameterList.java 1.0 2013年8月28日下午1:36:01
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.core.dao.query;

import java.util.List;

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
 * @since 2013年8月28日 下午1:36:01 
 * 
 */
public class ParameterList extends AbstractParameter<List> {
	
	public ParameterList(String name, List param){
		super(name, param);
	}

	/** 
	 * <p>Title: setParameterValue</p>
	 * <p>Description: </p>
	 * @param q
	 * @see com.chinadrtv.erp.core.dao.query.Parameter#setParameterValue(org.hibernate.Query)
	 */ 
	@Override
	public void setParameterValue(Query q) {
		q.setParameterList(getName(), getValue());
	}

}
