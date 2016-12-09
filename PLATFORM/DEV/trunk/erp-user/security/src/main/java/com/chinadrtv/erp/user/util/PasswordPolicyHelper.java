/*
 * @(#)PermissionHelper.java 1.0 2013-5-7上午9:39:11
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.ppolicy.PasswordPolicyControl;

import com.chinadrtv.erp.core.dao.query.Parameter;
import com.chinadrtv.erp.core.dao.query.ParameterInteger;
import com.chinadrtv.erp.core.dao.query.ParameterLong;
import com.chinadrtv.erp.core.dao.query.ParameterString;
import com.chinadrtv.erp.model.security.DataViewPermission;
import com.chinadrtv.erp.model.security.FieldPermission;
import com.chinadrtv.erp.model.security.FilterValue;
import com.chinadrtv.erp.user.dao.DataViewPermissionDao;
import com.chinadrtv.erp.user.dao.FieldPermissionDao;
import com.chinadrtv.erp.user.model.AgentRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.FilterCondition;
import com.chinadrtv.erp.util.ExpressionUtils;
import com.chinadrtv.erp.util.SpringUtil;

/**
 * <dl>
 * <dt><b>Title:密码策略工具帮助类</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:密码策略帮助类</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-5-7 上午9:39:11
 * 
 */
public class PasswordPolicyHelper {

	private static final Logger logger = LoggerFactory.getLogger(PasswordPolicyHelper.class);
	
	private static ThreadLocal<PasswordPolicyControl> passwordPolicyLocal = new ThreadLocal<PasswordPolicyControl>();

	/**
	 * 
	* @Description: TODO
	* @return void
	* @throws
	 */
	public static void removeThreadLocal(){
		passwordPolicyLocal.remove();
	}
	
	/**
	 * 
	* @Description: TODO
	* @param passwordPolicyControl
	* @return void
	* @throws
	 */
	public static PasswordPolicyControl getPasswordPolicyControl() {
			return passwordPolicyLocal.get();
	}
	
	/**
	 * 
	* @Description: TODO
	* @param passwordPolicyControl
	* @return void
	* @throws
	 */
	public static void setPasswordPolicyControl(PasswordPolicyControl passwordPolicyControl) {
			passwordPolicyLocal.set(passwordPolicyControl);
	}
}
