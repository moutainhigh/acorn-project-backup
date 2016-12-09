package com.chinadrtv.erp.marketing.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	public static boolean isMobileNO(String mobiles) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(14[0-9]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();

	}

	public static List<String> getVar(String str) {

		List<String> result = new ArrayList<String>();
		// Pattern p = Pattern.compile("\\{([^}]*)\\}");
		Pattern p = Pattern.compile("\\{[^}]*\\}");

		Matcher m = p.matcher(str);
		String var = "";
		while (m.find()) {
			var = m.group();
			// if(var!=null && var.indexOf(".")<0){
			result.add(var);
			// }
		}

		return result;

	}

}
