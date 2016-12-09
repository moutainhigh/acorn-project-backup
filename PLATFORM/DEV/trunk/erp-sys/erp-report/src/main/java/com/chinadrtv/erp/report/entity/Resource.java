package com.chinadrtv.erp.report.entity;

import com.chinadrtv.erp.report.core.orm.entity.IdEntity;

//@Entity
//@Table(name="REPORT_RESOURCE")
public class Resource extends IdEntity<Resource> {

	private static final long serialVersionUID = -4130339243076992897L;

	private String url;
	private Integer priority;
	private Integer type;
	private String name;
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}