/*
 * @(#)LoginDto.java 1.0 2014-5-5下午5:36:20
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.dto;

import java.io.Serializable;

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
 * @since 2014-5-5 下午5:36:20 
 * 
 */
public class LoginDto implements Serializable{

	private static final long serialVersionUID = -1754470933003323531L;
	
	private String message;
	private String result;
	private UserDto user;
	
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */ 
	public LoginDto() {
		super();
	}

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param message
	 * @param result
	 * @param user
	 */ 
	public LoginDto(String message, String result, UserDto user) {
		super();
		this.message = message;
		this.result = result;
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
}
