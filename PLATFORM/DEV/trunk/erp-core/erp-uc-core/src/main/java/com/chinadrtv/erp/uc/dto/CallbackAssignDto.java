/*
 * @(#)CallbackAssignDto.java 1.0 2013年8月13日下午1:33:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年8月13日 下午1:33:08 
 * 
 */
public class CallbackAssignDto {
	private String userId;
	private String userGroup;
	private Integer assignCount;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public Integer getAssignCount() {
		return assignCount;
	}
	public void setAssignCount(Integer assignCount) {
		this.assignCount = assignCount;
	}
}
