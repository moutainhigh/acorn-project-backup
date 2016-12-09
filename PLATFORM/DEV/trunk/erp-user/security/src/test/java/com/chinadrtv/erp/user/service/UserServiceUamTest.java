/*
 * @(#)UserServiceRestTest.java 1.0 2014年4月10日下午1:13:54
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.test.SpringTest;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.impl.UserServiceUamImpl;

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
 * @author andrew
 * @version 1.0
 * @since 2014年4月10日 下午1:13:54 
 * 
 */
public class UserServiceUamTest extends SpringTest {
	
	 private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceUamImpl.class);

	@Autowired
	private UserService userServiceUam;
	
	@Autowired
	private UserService userService;
	
	private String userId = "10004";
	private String groupCode = "outdialsh56";
	private String deptNum = "3";
	
	@Test
	public void testGetUserGroup() throws ServiceException {
		String userGroupName = userServiceUam.getUserGroup(userId);
		
		Assert.assertNotNull(userGroupName);
		logger.debug("userGroupName", userGroupName);
		
		String userGroupNameLdap = userService.getUserGroup(userId);
		Assert.assertEquals(userGroupName, userGroupNameLdap);
	}
	
	@Test
	public void testGetDepartment() throws ServiceException, NamingException {
		String departmentName = userServiceUam.getDepartment(userId);
		
		Assert.assertNotNull(departmentName);
		logger.debug("departmentName", departmentName);
		
		Assert.assertEquals(departmentName, userService.getDepartment(userId));
	}
	
	@Test
	public void testGetGroupAreaCode() throws ServiceException, NamingException {
		String areaCode = userServiceUam.getGroupAreaCode(userId);
		
		Assert.assertNotNull(areaCode);
		logger.debug("areaCode", areaCode);
		
		Assert.assertEquals(areaCode, userService.getGroupAreaCode(userId));
	}
	
	@Test
	public void testGetGroupType() throws ServiceException, NamingException {
		String groupType = userServiceUam.getGroupType(userId);
		
		Assert.assertNotNull(groupType);
		logger.debug("groupType", groupType);
		
		Assert.assertEquals(groupType, userService.getGroupType(userId));
	}
	
	@Test
	public void testGetUserList() throws ServiceException, NamingException {
		List<AgentUser> userList = userServiceUam.getUserList(groupCode);
		
		Assert.assertNotNull(userList);
		logger.debug("userList", userList);
		
		List<AgentUser> userListLdap = userService.getUserList(groupCode);
		
		Assert.assertTrue(userList.size() == userListLdap.size());
		//Assert.assertTrue(compareWithoutOrder(userList, userListLdap));
	}
	
	@Test
	public void testGetDepartmentInfo() throws ServiceException, NamingException {
		DepartmentInfo deptInfo = userServiceUam.getDepartmentInfo(deptNum);
		
		Assert.assertNotNull(deptInfo);
		logger.debug("deptInfo", deptInfo);
		
		DepartmentInfo deptInfoLdap = userService.getDepartmentInfo(deptNum);
		
		Assert.assertEquals(deptInfo, deptInfoLdap);
	}
	
	@Test
	public void testGetGroupList() throws ServiceException, NamingException {
		List<GroupInfo> groupList = userServiceUam.getGroupList(deptNum);
		
		Assert.assertNotNull(groupList);
		logger.debug("deptInfo", groupList);
		
		//List<GroupInfo> groupListLdap = userService.getGroupList(deptNum);
		
		//Assert.assertTrue(compareWithoutOrder(groupList, groupListLdap));
	}
	
	@Test
	public void testGetGroupInfo() throws ServiceException, NamingException {
		GroupInfo groupInfo = userServiceUam.getGroupInfo(groupCode);
		
		Assert.assertNotNull(groupInfo);
		logger.debug("groupInfo", groupInfo);
		
		Assert.assertEquals(groupInfo, userService.getGroupInfo(groupCode));
	}
	
	@Test
	public void testGetGroupDisplayName() throws ServiceException, NamingException {
		String groupName = userServiceUam.getGroupDisplayName(groupCode);
		
		Assert.assertNotNull(groupName);
		logger.debug("groupName", groupName);
		
		Assert.assertEquals(groupName, userService.getGroupDisplayName(groupCode));
	}
	
	@Test
	public void testGetRoleList() throws ServiceException, NamingException {
		List<AcornRole> roleList = userServiceUam.getRoleList(userId);
		
		Assert.assertNotNull(roleList);
		logger.debug("roleList", roleList);
		
		List<AcornRole> roleListLdap = userService.getRoleList(userId);
		
		Assert.assertTrue(roleList.size() == roleListLdap.size());
		//Assert.assertTrue(compareWithoutOrder(roleList, roleListLdap));
	}
	
	@Test
	public void testGetDepartments() throws ServiceException, NamingException {
		List<DepartmentInfo> deptList = userServiceUam.getDepartments();
		
		Assert.assertNotNull(deptList);
		logger.debug("deptList", deptList);
		
		//List<DepartmentInfo> deptListLdap = userService.getDepartments();
		
		//Assert.assertTrue(compareWithoutOrder(deptList, deptListLdap));
	}
	
	@Test
	public void testGetUserAttribute() throws ServiceException, NamingException {
		String userName = userServiceUam.getUserAttribute(userId, "displayName");
		
		Assert.assertNotNull(userName);
		logger.debug("userName", userName);
		
		String userNameLdap = userService.getUserAttribute(userId, "displayName");
		
		Assert.assertEquals(userName, userNameLdap);
	}
	
	@Test
	public void testFindUserByUid() throws ServiceException, NamingException {
		List<AgentUser> userList = userServiceUam.findUserByUid(userId);
		
		Assert.assertNotNull(userList);
		logger.debug("userList", userList);
		//List<AgentUser> userListLdap = userService.findUserByUid(userId);
		//Assert.assertTrue(compareWithoutOrder(userList, userListLdap));
	}
	
	@Test
	public void testGetAllGroup() throws ServiceException, NamingException {
		List<GroupInfo> groupList = userServiceUam.getAllGroup();
		
		Assert.assertNotNull(groupList);
		logger.debug("groupList", groupList);
		
		//List<GroupInfo> groupListLdap = userService.getAllGroup();
		
		//Assert.assertTrue(compareWithoutOrder(groupList, groupListLdap));
	}
	
	@Test
	public void testFindUserLikeUid() throws ServiceException, NamingException {
		List<AgentUser> userList = userServiceUam.findUserLikeUid("1000");
		
		Assert.assertNotNull(userList);
		logger.debug("userList", userList);
		
		//List<AgentUser> userListLdap = userService.findUserLikeUid("1000");
		
		//Assert.assertTrue(compareWithoutOrder(userList, userListLdap));
	}
	
	@Test
	public void testGetDepartmentByGroup() throws ServiceException, NamingException {
		String dept = userServiceUam.getDepartmentByGroup(groupCode);
		
		Assert.assertNotNull(dept);
		logger.debug("dept", dept);
		
		Assert.assertEquals(dept, userService.getDepartmentByGroup(groupCode));
	}
	
	@Test
	public void testGetDepartmentDisplayName() throws ServiceException, NamingException {
		String deptName = userServiceUam.getDepartmentDisplayName(deptNum);
		
		Assert.assertNotNull(deptName);
		logger.debug("deptName", deptName);
		
		//String deptNameLdap = userService.getDepartmentDisplayName(deptNum);
		
		//Assert.assertEquals(deptName, deptNameLdap);
	}
	
	@Test
	public void testGetManageGroupList() throws ServiceException, NamingException {
		List<String> groupList = userServiceUam.getManageGroupList(deptNum);
		
		Assert.assertNotNull(groupList);
		logger.debug("groupList", groupList);
		
		List<String> groupListLdap = userService.getManageGroupList(deptNum);
		
		Assert.assertTrue(compareWithoutOrder(groupList, groupListLdap));
	}
	
	@Test
	public void testGetManageUserList() throws ServiceException, NamingException {
		List<AgentUser> userList = userServiceUam.getManageUserList(deptNum);
		
		Assert.assertNotNull(userList);
		logger.debug("userList", userList);
		
		List<AgentUser> userListLdap = userService.getManageUserList(deptNum);
		
		Assert.assertTrue(userList.size() == userListLdap.size());
		//Assert.assertTrue(compareWithoutOrder(userList, userListLdap));
	}
	
	@Test
	public void testChangePassword() throws ServiceException {
		String userId = "18855";
		String oldPassword = "123456";
		String newPassword = "Abcd1234";
		
		userServiceUam.changePassword(userId, newPassword, oldPassword);
	}
	
	//@Test
	public void testCheckPassword() throws ServiceException {
		String userId = "18855";
		String password = "Abcd1234";
		
		boolean success = userServiceUam.checkPassword(userId, password);
		Assert.assertTrue(success);
	}
	
	@Test
	public void testGetUserDN() {
		String dn = userService.getUserDN(userId);
		Assert.assertNotNull(dn);
	}
	
	@Test
	public void testGetRoleByName() throws ServiceException {
		String roleName = "cti_session_manager";
		AcornRole role = userService.getRoleByName(roleName);
		Assert.assertNotNull(role);
		Assert.assertNotNull(role.getName());
	}

	@Test
	//@Rollback(false)
	public void testResetUserPassword() throws ServiceException {
		String password = "123456";
		Map<String, Object> rsMap = userService.resetUserPassword(userId, password);
		Assert.assertNotNull(rsMap);
		Assert.assertEquals(rsMap.get("result"), "success");
	}
	
	@Test
	public void testLogin() throws ServiceException {
		AgentUser user = userService.login("11397", "123456");
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getWorkGrp());
	}
	
	@Test
	public void testGetUserIdListByDept() throws Exception {
		List<String> userIdList = userService.getUserIdListByDept(deptNum);
		
		Assert.assertNotNull(userIdList);
		Assert.assertTrue(userIdList.size() > 1);
	}
	
	@Test
	public void testArraySample() {
		
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		for(int i=0; i<3; i++) {
			list1.add(i);
		}
		//list1.add(2);
		
		for(int i=2; i>=0; i-- ) {
			list2.add(i);
		}
		//list2.add(5);
		
		Assert.assertTrue(compareWithoutOrder(list1, list2));
	}


}
