package com.chinadrtv.scheduler.web.constants;

public enum Constants {
	SEARCH_ACTION("search_action"),SEARCH_NAME("search_name"),SEARCH_PARAM_CACHE("cache"),SEARCH_PARAM_CLEAN("clean"),PAGE_SIZE("pageSize"),CURRENT_PAGE("currPage"),
	SERIAL_NUMBER("serial_number");
	public  String val;

	private Constants(String val) {
		this.val = val;
	}
}
