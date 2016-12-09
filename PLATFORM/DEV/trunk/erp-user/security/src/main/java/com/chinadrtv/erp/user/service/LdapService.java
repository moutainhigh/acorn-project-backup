/*
 * @(#)UserService.java 1.0 2013-7-1下午1:22:05
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.ldap.AcornGroup;
import com.chinadrtv.erp.user.ldap.AcornUser;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

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
 * @since 2013-7-1 下午1:22:05 
 * 
 */
@Deprecated
public interface LdapService {

	/**
	 * 
	* @Description: 搜索ldap某对象属性
	* @param base 搜索根
	* @param filter 过滤
	* @param param 参数
	* @param attribute 属性名
	* @return
	* @throws ServiceException
	* @return String
	* @throws
	 */
	public String searchAttribute(String base,String filter,String param,String attribute) throws ServiceException;
	
	public String searchAttributeNoCache(String base,String filter,String param,String attribute) throws ServiceException;
	
	
	/**
	 * 
	* @Description: 搜索ldap某对象属性
	* @param base 搜索根
	* @param filter 过滤
	* @param param 参数
	* @param attribute 属性名
	* @return
	* @throws ServiceException
	* @return String
	* @throws
	 */
	public String searchGroupAttribute(String param,String attribute) throws ServiceException;
	
	/**
	 * 
	* @Description: 搜索用户属性
	* @param param
	* @param attribute
	* @return
	* @throws ServiceException
	* @return String
	* @throws
	 */
	public String searchUserAttribute(String param, String attribute) throws ServiceException;
	
	public String searchUserAttributeNoCache(String param, String attribute) throws ServiceException;
	/**
	 * 
	* @Description: 修改密码
	* @param userId
	* @param oldPassword
	* @param newPassWord
	* @throws ServiceException
	* @return void
	* @throws
	 */
	public void changePassword(String userDn,String newPassWord,String oldPassWord) throws ServiceException;
	
	public void changePasswordByRebind(String dn, String newPassWord,String oldPassWord)throws ServiceException ;
	/**
	 * 
	* @Description: 校验密码
	* @param uId
	* @param password
	* @throws ServiceException
	* @return void
	* @throws
	 */
	public boolean checkPassword(String uId, String password)throws ServiceException;
	
	public String getUserDn(String uid);
	/**
	 * 
	* @Description: 获取ou的Dn
	* @param groupName
	* @return
	* @return String
	* @throws
	 */
	public String getGroupDn(String groupName);
	
	public void createUser(AcornUser user) throws ServiceException ;
	public void updateUser(AcornUser user) throws ServiceException ;
	public void deleteUser(AcornUser user) throws ServiceException ;
	
	public void createGroup(AcornGroup group) throws ServiceException ;
	public void updateGroup(AcornGroup group) throws ServiceException ;
	public void deleteGroup(AcornGroup group) throws ServiceException ;
	
	public void assignUser(String username, String groupName)
	        throws ServiceException;
	
	
	public void creatAllGroup(String departmentNum);
	public void creatAllUser(String departmentNum);
	
	/**
	 * 
	* @Description: 修改用户锁定状态
	* @param userId
	* @param status
	* @throws ServiceException
	* @return void
	* @throws
	 */
	public void updateUserAttribute(String userId,String attr,String value) throws ServiceException;
	
	/**
	 * 
	* @Description: 添加用户属性
	* @param userId
	* @param attr
	* @param value
	* @throws ServiceException
	* @return void
	* @throws
	 */
	public void addUserAttribute(String userId, String attr, String value)
			throws ServiceException;
	
	void removeUserAttribute(String userId, String attr);

    public LdapUserDetails findUserByUid(String userId) throws ServiceException;

}
