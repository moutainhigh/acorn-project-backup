/**
 * Copyright (c) 2014, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.user.service;

import org.compass.core.util.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.test.SpringTest;

/**
 * 2014-4-4 下午1:56:28
 * @version 1.0.0
 * @author yangfei
 *
 */
public class UserServiceTest extends SpringTest{
	@Autowired
	private UserService userService;
	
	@Test
	public void testGetUserGroup() throws ServiceException {
		String uid = "15674";
		String group = userService.getUserGroup(uid);
		Assert.notNull(group);
	}
	
	@Test
	public void testGetDepartmentDisplayName() throws ServiceException {
		String deptId= "dbmarketing_bj";
		String deptName = userService.getDepartmentDisplayName(deptId);
		Assert.notNull(deptName);
	}
	
	@Test
	public void testGetGroupDisplayName() throws ServiceException {
		String groupId= "outdialsh13";
		String groupName = userService.getGroupDisplayName(groupId);
		Assert.notNull(groupName);
	}
	
	@Test
	public void getDepartMentDisplayNameByGroupId() throws ServiceException {
		String groupId = "outdialsh38";
		String deptId = userService.getDepartmentByGroup(groupId);
		String deptName = userService.getDepartmentDisplayName(deptId);
		Assert.notNull(deptName);
	}
}
