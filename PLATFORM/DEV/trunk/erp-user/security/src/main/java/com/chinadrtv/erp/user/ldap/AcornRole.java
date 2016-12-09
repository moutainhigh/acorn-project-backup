/*
 * @(#)AcornRole.java 1.0 2013-8-6上午11:08:41
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.ldap;

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
public class AcornRole implements Serializable {

	private static final long serialVersionUID = -8925801286521929503L;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acornPriority == null) ? 0 : acornPriority.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcornRole other = (AcornRole) obj;
		if (acornPriority == null) {
			if (other.acornPriority != null)
				return false;
		} else if (!acornPriority.equals(other.acornPriority))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
