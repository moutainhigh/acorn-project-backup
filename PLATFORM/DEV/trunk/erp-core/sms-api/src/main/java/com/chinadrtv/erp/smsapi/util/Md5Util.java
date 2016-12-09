/*
 * @(#)Md5Util.java 1.0 2013-2-21下午1:34:09
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-21 下午1:34:09
 * 
 */
public class Md5Util {

	public static void main(String[] args) {
		Md5Util md5Util = new Md5Util();
		String x = "2013030524171024000000001test00115901957475{t1}我很好你好啊{t2}acorn";
		try {
			String xxString = md5Util.MD5(new String(x.getBytes("iso8859-1"),
					"gb2312"));
			System.out.println(xxString);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
