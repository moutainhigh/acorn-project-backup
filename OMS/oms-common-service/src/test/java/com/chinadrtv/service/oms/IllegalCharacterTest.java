/*
 * @(#)IllegalCharacterTest.java 1.0 2014-8-27上午10:17:31
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.junit.Test;

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
 * @since 2014-8-27 上午10:17:31 
 * 
 */
public class IllegalCharacterTest extends TestCase {

	
	@Test
	public void test2() {
		String receiverAddress = "\"上!@#$%^&*()_+-={}[]\\|\"`~,.<>/?海!@#$%^&*()_+-={}[]\\|`~,.<>/?市!@#$%^&*()_+-={}[]\\|`~,.<>/?田林路487#18F";
		
		String regExp = "[\"`~!@$%^&*\\]\\[\\-_\\\\+=|\"{}':;',//[//].<>/?~！@￥%……&*——+|{}【】‘；：”“’。，、？]";
		
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(receiverAddress);
		
		receiverAddress = matcher.replaceAll(" ");
		
		System.out.println(receiverAddress);
	}
	
	@Test
	public void test3() {
		String userId = String.format("%04d", 143443);
		if(userId.length() > 4) {
			userId = userId.substring(userId.length() -4, userId.length());	
		}
		
		System.out.println("userId: " + userId);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmssS");
		String dt = sdf.format(new Date());
		
		System.out.println(dt);
	}
}
