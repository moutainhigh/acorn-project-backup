/*
 * @(#)UserService.java 1.0 2013-7-1下午1:22:05
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.service;

import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.model.GroupInfo;

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
public interface UserService {
	
	/**
	 * @Description: 搜索客户
	 * @param userName
	 * @return AgentUser
	 */
	public String getUserGroup(String userName) throws ServiceException;
	
    
	/**
	 * 获取部门
	 * @param group
	 * @return String
	 * @throws ServiceException
	 */
	String getDepartmentByGroup(String group) throws ServiceException;

	/**
	 * @Description: 获取客户部门
	 * @param userName
	 * @throws ServiceException
	 * @return String
	 */
	public String getDepartment(String userName) throws ServiceException, NamingException;
	
	/**
	 * @param userName
	 * @return List<String>
	 */
	@Deprecated
	List<String> getUserDepartments(String userName);
	
	/**
	 * @Description: 根据用户获取areaCode
	 * @param userName
	 * @throws ServiceException
	 * @return String
	 */
	public String getGroupAreaCode(String userName)  throws ServiceException;
	
	/**
	 * @Description: 根据用户获取groupType
	 * @param userName
	 * @throws ServiceException
	 * @return String
	 */
	public String getGroupType(String userName)  throws ServiceException;
	
	/**
	 * @Description:根据组名获取areaCode
	 * @param groupName
	 * @throws ServiceException
	 * @return String
	 */
	public String getAreaCode(String groupName) throws ServiceException;
	
	/**
	 * @Description: 修改密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassWord
	 * @throws ServiceException
	 */
	public void changePassword(String userId,String newPassWord,String oldPassword) throws ServiceException;
	
	/**
	 * @Description: 校验密码
	 * @param uId
	 * @param password
	 * @throws ServiceException
	 * @return boolean
	 */
	@Deprecated
	public boolean checkPassword(String uId, String password)throws ServiceException;
	
	/**
	 * 根据组获取人员列表
	 * @param groupName
	 * @throws NamingException
	 * @return List
	 */
	public List<AgentUser> getUserList(String groupCode) throws ServiceException;
	
	/**
	 * 获取部门信息
	 * @param departmentNum
	 * @throws ServiceException
	 * @return DepartmentInfo
	 */
	public DepartmentInfo getDepartmentInfo(String departmentNum) throws ServiceException;
	
	/**
	 * @Description: 获取部门下的组织信息
	 * @param departmentNum
	 * @throws ServiceException
	 * @return DepartmentInfo
	 */
	public List<GroupInfo> getGroupList(String departmentNum) throws ServiceException;
	
	/**
	 * @Description: 获取组信息
	 * @param groupName
	 * @throws ServiceException
	 * @return GroupInfo
	 */
	public GroupInfo getGroupInfo(String groupName) throws ServiceException;
	
	/**
	 * 获取组名
	 * @param groupName
	 * @return String
	 * @throws ServiceException
	 */
	public String getGroupDisplayName(String groupName) throws ServiceException;

	/**
	 * @Description: 获取用户的角色列表
	 * @param userName
	 * @throws ServiceException
	 * @return List<AcornRole>
	 */
	String getDepartmentDisplayName(String deptName) throws ServiceException;
	
	/**
	 * 获取用户角色
	 * @param userName
	 * @return List<AcornRole>
	 * @throws ServiceException
	 */
	public List<AcornRole> getRoleList(String userName) throws ServiceException;
	
	/**
	 * @Description: 获取部门下的管理组列表
	 * @param departmentNum
	 * @throws ServiceException
	 * @return List<String>
	 */
	public List<String> getManageGroupList(String departmentNum) throws ServiceException;
	
	/**
	 * @Description: 获取部门列表
	 * @throws ServiceException
	 * @return List<DepartmentInfo>
	 */
	public List<DepartmentInfo> getDepartments() throws ServiceException;
	
	/**
	 * @Description: 获取部门下的所有管理组的用户
	 * @param departmentNum
	 * @throws ServiceException
	 * @return List<AgentUser>
	 */
	public List<AgentUser> getManageUserList(String departmentNum) throws ServiceException;
	
	/**
	 * 登陆成功后处理
	 * @param userId
	 * @return String
	 * @throws ServiceException
	 */
	public String loginSuccess(String userId) throws ServiceException;
	
	/**
	 * 登陆失败后处理
	 * @param userId
	 * @return String
	 * @throws ServiceException
	 */
	@Deprecated
	public String loginFailure(String userId) throws ServiceException;
	
	/**
	 * 获取用户属性
	 * @param uid
	 * @param attribute
	 * @return String
	 * @throws ServiceException
	 */
	@Deprecated
	public String getUserAttribute(String uid, String attribute) throws ServiceException;

	/**
	 * 根据用户ID查找
	 * @param uid
	 * @return List<AgentUser>
	 * @throws ServiceException
	 */
    public List<AgentUser> findUserByUid(String uid) throws ServiceException;
    
    /**
     * 获取所有用户组
     * @return List<GroupInfo>
     * @throws ServiceException
     */
    public List<GroupInfo> getAllGroup() throws ServiceException;

    /**
     * 根据用户ID模糊查询
     * @param uid
     * @return List<AgentUser>
     * @throws ServiceException
     */
    public List<AgentUser> findUserLikeUid(String uid) throws ServiceException;
    
    @Deprecated
    boolean releaseLock(String userId) throws ServiceException;

    @Deprecated
    public String getUserDN(String userId);

    /**
     * <p>根据用户ID获取用户对象</p>
     * @param userId
     * @return AgentUser
     * @throws ServiceException
     */
    public AgentUser getUserById(String userId) throws ServiceException;
    
	/**
	 * <p>根据角色名获取角色对象</p>
	 * @param roleName
	 * @return AcornRole
	 */
	public AcornRole getRoleByName(String roleName) throws ServiceException;


	/**
	 * <p>登陆</p>
	 * @param userId
	 * @param password
	 * @return AgentUser
	 */
	public AgentUser login(String userId, String password) throws ServiceException;
	
	/**
	 * <p>重置用户密码</p>
	 * @param userId
	 * @param password
	 * @return Map<String, Object>
	 * @throws ServiceException
	 */
	public Map<String, Object> resetUserPassword(String userId, String password) throws ServiceException;
	
	/**
	 * <p>权限部门获取用户ID列表</p>
	 * @param deptCode
	 * @return List<String>
	 * @throws Exception
	 */
	List<String> getUserIdListByDept(String deptNum) throws Exception;
}
