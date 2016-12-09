package com.chinadrtv.erp.smsapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 字符串操作
 * 
 * @author haoleitao
 * @date 2013-1-6 上午9:26:00
 * 
 */
public class StringUtil {
	/**
	 * @param str
	 * @return
	 * @author haoleitao
	 * @date 2013-1-8 上午11:48:10
	 */
	public static String nullToBlank(String str) {
		return str == null ? "" : str;
	}

	public static boolean isNullOrBank(String str) {
		return (str == null || str.trim().equals("")) ? true : false;

	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static void main(String[] args) {
		System.out.println(StringUtil.isNullOrBank(null));
	}
}
