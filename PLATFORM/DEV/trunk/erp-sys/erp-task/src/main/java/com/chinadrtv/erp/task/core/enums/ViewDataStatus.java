package com.chinadrtv.erp.task.core.enums;

public enum ViewDataStatus {
	
	SUCCESS("成功"),

	ERROR("出错");
	
	private String description;

	private ViewDataStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
