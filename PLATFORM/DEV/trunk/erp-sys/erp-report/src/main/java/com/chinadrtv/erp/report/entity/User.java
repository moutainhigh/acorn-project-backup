package com.chinadrtv.erp.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.chinadrtv.erp.report.core.orm.entity.BaseEntity;

@Entity
@Table(name="USR", schema="IAGENT")
public class User extends BaseEntity<User> {

	private static final long serialVersionUID = 6785992777452283149L;
	
    @Id
    @Column(name="USRID")
	private String id;
	private String name;
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}