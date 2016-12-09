package com.chinadrtv.erp.task.enums;

public enum TriggerState {

	ACQUIRED("运行中"),

	PAUSED("暂停中"),
	
	WAITING("等待中");
	
	private String description;

	private TriggerState(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

}
