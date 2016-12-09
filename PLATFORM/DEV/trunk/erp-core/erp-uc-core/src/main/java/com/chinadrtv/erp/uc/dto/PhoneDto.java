/*
 * @(#)PhoneDto.java 1.0 2013-7-10上午10:05:47
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dto;

import com.chinadrtv.erp.model.Phone;

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
 * @since 2013-7-10 上午10:05:47 
 * 
 */
public class PhoneDto extends Phone {

	private static final long serialVersionUID = 3822309860815628426L;

	private String phoneNumMask;

	public String getPhoneNumMask() {
		return phoneNumMask;
	}

	public void setPhoneNumMask(String phoneNumMask) {
		this.phoneNumMask = phoneNumMask;
	}
}
