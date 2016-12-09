/**
 * Copyright (c) 2013, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.model.marketing.task;

import java.io.Serializable;

/**
 * 2013-5-6 下午6:07:41
 * @version 1.0.0
 * @author yangfei
 *
 */
public class TaskFormItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String value;
	private String type;
	private boolean isChecked = false;
	private String camName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public String getCamName() {
		return camName;
	}
	public void setCamName(String camName) {
		this.camName = camName;
	}
	
	@Override
	public String toString() {
		return "TaskFormItem [name=" + name + ", value=" + value + ", type="
				+ type + "]";
	}
	
}
