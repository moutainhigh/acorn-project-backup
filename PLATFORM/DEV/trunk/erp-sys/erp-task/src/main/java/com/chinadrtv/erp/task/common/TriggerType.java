package com.chinadrtv.erp.task.common;

public enum TriggerType {

	CRON("CRON"), SIMPLE("SIMPLE");
	
	private String description;

	private TriggerType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
