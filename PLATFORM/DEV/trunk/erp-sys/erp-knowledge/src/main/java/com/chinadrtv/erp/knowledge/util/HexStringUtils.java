/**
 * 
 */
package com.chinadrtv.erp.knowledge.util;

import java.io.ByteArrayOutputStream;

/**
 * @author dengqianyong
 * 
 */
public final class HexStringUtils {

	private static final String HEX_STRING = "0123456789ABCDEF";

	/**
	 * 以16进制加密
	 * 
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(HEX_STRING.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(HEX_STRING.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 以16进制解密
	 * 
	 * @param bytes
	 * @return
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((HEX_STRING.indexOf(bytes.charAt(i)) << 4 | HEX_STRING
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
}
