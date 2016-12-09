/*
 * @(#)EncryptTest.java 1.0 2014-5-22上午11:35:54
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.chinadrtv.util.MD5Util;

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
 * @since 2014-5-22 上午11:35:54 
 * 
 */
public class EncryptTest extends TestCase{

	@Test
	public void testDecode() {
		String ciphertext = "4572726F723ACEA5B7B4B7A2CBCDD0ADD2E9B5DAB6FECCF5A1A3";
		String plaintext = MD5Util.decodeHex(ciphertext);
		
		System.out.println(plaintext);
	}
	
	@Test
	public void testEncode() {
		String xml = "<root><UpdateInfo><LogisticProviderID>95</LogisticProviderID><BillNo>ATV91041559</BillNo><OperaType>OP01</OperaType><OperaDate>2012-03-01T09:00:00</OperaDate><Station>青岛总站</Station><Operator>刘伟</Operator><RefuseReason></RefuseReason><ProblemReason></ProblemReason><Remark></Remark><Update_time>2012-03-01T09:00:00</Update_time></UpdateInfo></root>";
		String userId = "1040";
		String password = "XTqWZ58Q0c";
		String ciphertext = MD5Util.getMD5Hex(userId + xml + password);
		System.out.println(ciphertext);
	}
}
