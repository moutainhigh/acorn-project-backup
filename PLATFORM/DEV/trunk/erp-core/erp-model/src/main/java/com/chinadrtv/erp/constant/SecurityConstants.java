/*
 * @(#)SecurityConstants.java 1.0 2013-6-8下午3:56:32
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.constant;

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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-6-8 下午3:56:32 
 * 
 */
public class SecurityConstants {

	
	public static final String 	USER_TRACK  = "USER_TRACK";
	
	public static final String 	LDAP_ROOT_DN  = "dc=chinadrtv,dc=com";//outbound部门主管角色
	
	public static final String 	ROLE_APPROVE_MANAGER  = "APPROVE_MANAGER";//审批权限
	public static final String 	ROLE_OUTBOUND_MANAGER  = "OUTBOUND_MANAGER";//outbound部门主管角色
	public static final String 	ROLE_OUTBOUND_GROUP_MANAGER  = "OUTBOUND_GROUP_MANAGER";//outbound组主管角色
	public static final String 	ROLE_OUTBOUND_AGENT  = "OUTBOUND_AGENT";//outbound坐席
	public static final String 	ROLE_INBOUND_GROUP_MANAGER  = "INBOUND_GROUP_MANAGER";//inbound组主管角色
	public static final String 	ROLE_INBOUND_MANAGER  = "INBOUND_MANAGER";//inbound主管角色
	public static final String 	ROLE_INBOUND_AGENT  = "INBOUND_AGENT";//inbound主管坐席
	public static final String 	ROLE_OTHER  = "OTHER";//inbound主管坐席
	public static final String 	ROLE_ASSIGN_TO_AGENT  = "ASSIGN_TO_AGENT";//分配到坐席的权限
    public static final String 	ROLE_ASSIGN_TO_DEPARTMENT  = "ASSIGN_TO_DEPARTMENT";//分配到部门的权限
	public static final String 	ROLE_CTI_SESSION_MANAGER  = "CTI_SESSION_MANAGER";//cti会话管理
	public static final String 	ROLE_CALLBACK_AGENT  = "CALLBACK_AGENT";//callback拨打权限
    public static final String    ROLE_QUALITY_INSPECTOR ="QUALITY_INSPECTOR";//质检部门，可以查询所有订单
    public static final String    ROLE_CREATE_TASK ="CREATE_TASK";//创建任务的权限，不受客户被其他坐席绑定的限制
    public static final String    ROLE_COMPLAIN_MANAGER="COMPLAIN_MANAGER";//客服主管
    public static final String    ROLE_COMPLAIN_AGENT="COMPLAIN_AGENT"; //客服坐席
    public static final String 	ROLE_INSURE_PROMPT  = "INSUREPROMPT";//赠险权限
	
	public static final String 	SEARCH_GROUP_ROOT  = "ou=department";//搜索组的根group
	public static final String 	SEARCH_ROLE_ROOT  = "ou=roles";//搜索组的根角色
	public static final String 	SEARCH_GROUP_FILTER  = "(ou={0})";//搜索组过滤
	public static final String 	SEARCH_USER_FILTER  = "(uid={0})";//搜索组过滤
	public static final String 	SEARCH_ROLE_FILTER  = "(cn={0})";//搜索角色过滤
	public static final String 	LDAP_GROUP_ATTRIBUTE_AREACODE  = "acornAreaCode";//搜索组属性区域代码
	public static final String 	LDAP_GROUP_ATTRIBUTE_GROUPTYPE  = "acornGroupType";//搜索组属性out外呼或in进线
	public static final String 	LDAP_ROLE_ATTRIBUTE_ACORNPRIORITY  = "acornPriority";//搜索角色属性 角色优先级
	public static final String 	LDAP_ROLE_ATTRIBUTE_DESC  = "description";//搜索角色属性 角色优先级
	public static final String 	LDAP_USER_ATTRIBUTE_EMPLOYEETYPE  = "employeeType";//搜索用户类型属性
	public static final String 	LDAP_USER_ATTRIBUTE_LASTTIME  = "acornLastTime";//最后登录时间属性
	public static final String 	LDAP_USER_ATTRIBUTE_MAXFAILURE  = "acornMaxFailure";//最大登陆错误次数属性
	public static final String 	LDAP_USER_ATTRIBUTE_LOCK  = "acornLock";//锁定状态
	public static final String 	LDAP_USER_ATTRIBUTE_DISPLAYNAME  = "displayName";//用户中文显示名称
    public static final String  SEARCH_SEAT_ROOT  = "ou=P3,ou=department";//搜索坐席的根是P3
	
}

