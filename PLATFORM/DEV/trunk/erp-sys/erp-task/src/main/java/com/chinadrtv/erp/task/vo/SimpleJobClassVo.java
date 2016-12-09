package com.chinadrtv.erp.task.vo;

import com.chinadrtv.erp.task.core.scheduler.SimpleJob;

public class SimpleJobClassVo {

	private String id;
	private Class<? extends SimpleJob> clazz;
	private String description;
	
	public SimpleJobClassVo(String id, Class<? extends SimpleJob> clazz, String description){
		this.id = id;
		this.clazz = clazz;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Class<? extends SimpleJob> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends SimpleJob> clazz) {
		this.clazz = clazz;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
