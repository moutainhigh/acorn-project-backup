/*
 * @(#)FilterCondition.java 1.0 2013-5-22上午9:56:52
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.model;

import java.util.Map;
import java.util.Set;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.model.security.FilterValue;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-5-22 上午9:56:52
 * 
 */
public class FilterCondition {

	private String whereClause;
	private String replacedWhereClause;
	private String placeHolderWhere;
	private Set<FilterValue> filterValues;
	private Map<String,Parameter> parmater;
	/**
	 * @return the whereClause
	 */
	public String getWhereClause() {
		return whereClause;
	}

	/**
	 * @param whereClause
	 *            the whereClause to set
	 */
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	/**
	 * @return the replacedWhereClause
	 */
	public String getReplacedWhereClause() {
		return replacedWhereClause;
	}

	/**
	 * @param replacedWhereClause
	 *            the replacedWhereClause to set
	 */
	public void setReplacedWhereClause(String replacedWhereClause) {
		this.replacedWhereClause = replacedWhereClause;
	}

	/**
	 * @return the filterValues
	 */
	public Set<FilterValue> getFilterValues() {
		return filterValues;
	}

	/**
	 * @param filterValues
	 *            the filterValues to set
	 */
	public void setFilterValues(Set<FilterValue> filterValues) {
		this.filterValues = filterValues;
	}

	/**
	 * @return the placeHolderWhere
	 */
	public String getPlaceHolderWhere() {
		return placeHolderWhere;
	}

	/**
	 * @param placeHolderWhere the placeHolderWhere to set
	 */
	public void setPlaceHolderWhere(String placeHolderWhere) {
		this.placeHolderWhere = placeHolderWhere;
	}

	/**
	 * @return the parmater
	 */
	public Map<String, Parameter> getParmater() {
		return parmater;
	}

	/**
	 * @param parmater the parmater to set
	 */
	public void setParmater(Map<String, Parameter> parmater) {
		this.parmater = parmater;
	}

}
