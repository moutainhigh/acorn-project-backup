package com.chinadrtv.erp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 文件MD5工具类
 * 
 * @author dengqianyong
 *
 */
public final class FileMd5Utils {
	
	private static final String EMPTY_STRING = "";
	private static final String MD5_STRING = "MD5";
	
	/**
	 * 得到文件的MD5值
	 * 
	 * @param file
	 * @return
	 */
	public static String md5(File file) {
		if (!file.isFile()) {
			return EMPTY_STRING;
		}
		FileInputStream in = null;
		try {
			int len;
			byte buffer[] = new byte[1024];
			MessageDigest digest = MessageDigest.getInstance(MD5_STRING);
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			return new BigInteger(1, digest.digest()).toString(16);
		} catch (Exception e) {
			return EMPTY_STRING;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
