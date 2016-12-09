package com.chinadrtv.uam.dao.wrapper;

import org.hibernate.FetchMode;

/**
 * 
 * @author Qianyong,Deng
 * @since Oct 8, 2012
 *
 */
public class FetchModeWrapper {

	private String property;
	
	private FetchMode fetchMode;

	public FetchModeWrapper(String property, FetchMode fetchMode) {
		this.property = property;
		this.fetchMode = fetchMode;
	}

	public String getProperty() {
		return property;
	}

	public FetchMode getFetchMode() {
		return fetchMode;
	}
}
