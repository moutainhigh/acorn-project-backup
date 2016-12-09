/*
 * @(#)AcornRole.java 1.0 2013-8-6上午11:08:41
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.uam.sync.model;

import java.io.Serializable;

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
 * @since 2013-8-6 上午11:08:41
 * 
 */
@SuppressWarnings("serial")
public class AcornRole implements Serializable {

	private String name;
	private String acornPriority;
	private String description;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the acornPriority
	 */
	public String getAcornPriority() {
		return acornPriority;
	}

	/**
	 * @param acornPriority
	 *            the acornPriority to set
	 */
	public void setAcornPriority(String acornPriority) {
		this.acornPriority = acornPriority;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
