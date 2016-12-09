/**
 * 
 */
package com.chinadrtv.erp.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *  
 * @author haoleitao
 * @date 2013-3-18 下午6:36:51
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Entity
@Table(name = "USER_GROUP", schema = "IAGENT")
public class UserGroup implements Serializable {
	
	private static final long serialVersionUID = -8063050269397978680L;
	
	private String usrId; //用户id
	private String groupId;//组id 
	private String remarks;//备注
	
	@Id
	@Column(name = "USRID")
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	@Id
	@Column(name = "GROUPID")
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
