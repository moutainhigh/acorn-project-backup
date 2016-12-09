package com.chinadrtv.uam.security;

import org.springframework.security.core.context.SecurityContextHolder;

public final class UserBeanHelper {

	/**
	 * 得到当前登录的用户
	 * 
	 * @return
	 */
	public static UserBean currentUser() {
		if (SecurityContextHolder.getContext() == null
				|| SecurityContextHolder.getContext().getAuthentication() == null) {
			return null;
		}

		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		return principal instanceof UserBean ? (UserBean) principal : null;
	}
	
	/**
	 * 当前登录用户的ID
	 * @return
	 */
	public static Long getUserId() {
		UserBean user = currentUser();
		if (user != null) {
			return user.getSysUser().getId();
		}
		return null;
	}
	
	/**
	 * 当前登录用户的名称
	 * @return
	 */
	public static String getUsername() {
		UserBean user = currentUser();
		if (user != null) {
			return user.getUsername();
		}
		return null;
	}
}
