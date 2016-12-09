package com.chinadrtv.erp.report.entity;

import com.chinadrtv.erp.report.core.orm.entity.IdEntity;

//@Entity
//@Table(name="REPORT_ROLE")
public class Role extends IdEntity<Role> {

	private static final long serialVersionUID = 778907072645279504L;
	
	private Integer enable;
	private String code;
	private String name;

	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}