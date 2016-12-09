/*
 * @(#)TestConnect.java 1.0 2014-5-19下午3:52:34
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.gonghang.test;

import java.io.UnsupportedEncodingException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import junit.framework.TestCase;

import org.junit.Test;

import sun.misc.BASE64Encoder;

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
 * @since 2014-5-19 下午3:52:34 
 * 
 */
public class TestConnect extends TestCase {

	@Test
	public void test1() throws UnsupportedEncodingException, Exception {
		
		// Hmac加密
		String data = "app_key=ZeQFkiJI&auth_code=6TooHpr5SaH9uikMbPcIpfrcIq78LKwBfmqaE38PpTUcNrHT0VMRBaxEnerCRt3n&req_data=<?xml version=\"1.0\" encoding=\"UTF-8\"?><body><create_start_time>2012-12-12 12:00:00</create_start_time><create_end_time>2012-12-12 13:00:00</create_end_time><refund_status>1</refund_status></body>";
		//String key = "12345678901234567890123456789012";// 32位密钥，从app信息中获取
		String key = "APPSOIUxQg4byGKmArvrXt1JJBa7SW86";

		byte[] bytes = encryptHMAC(data.getBytes("UTF-8"), key);
		BASE64Encoder be = new BASE64Encoder();
		System.out.println(be.encode(bytes));// 作为sign的密文内容
	}
	
	public static final String KEY_MAC = "HmacSHA256";

	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey sk = new SecretKeySpec(key.getBytes("UTF-8"), KEY_MAC);
		Mac mac = Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
		return mac.doFinal(data);
	}

}
