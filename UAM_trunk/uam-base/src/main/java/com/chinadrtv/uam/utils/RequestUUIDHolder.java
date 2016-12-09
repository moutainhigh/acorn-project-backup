package com.chinadrtv.uam.utils;

import java.util.UUID;

/**
 * 当前请求的唯一标识，用于区别请求
 * 
 * @author Qianyong, Deng
 * @created Sep 19, 2012
 *
 */
public final class RequestUUIDHolder {

	private static ThreadLocal<String> UUID_THREAD_LOCAL = new ThreadLocal<String>();
	
	/**
	 * 得到一个唯一标识
	 * 
	 * @return
	 */
	public static String get() {
		String uid = UUID_THREAD_LOCAL.get();
		if (uid != null && !"".equals(uid)) 
			return uid;
		uid = create();
		return uid;
	}

	/**
	 * 设置一个唯一标识
	 * 
	 * @param uid
	 */
	public static void set(String uid) {
		UUID_THREAD_LOCAL.set(uid);
	}
	
	/**
	 * 移除唯一标识
	 */
	public static void remove() {
		UUID_THREAD_LOCAL.remove();
	}
	
	/**
	 * 创建标识
	 * 
	 * @return
	 */
	private static String create() {
		String uid = UUID.randomUUID().toString();
		set(uid);
		return uid;
	}
	
}
