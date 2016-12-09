package com.chinadrtv.erp.oms.service.impl;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-11-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    public String getUserGroup(String userName) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDepartmentByGroup(String s) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getDepartment(String userName) throws ServiceException, NamingException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getUserDepartments(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getGroupAreaCode(String userName) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getGroupType(String userName) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getAreaCode(String groupName) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void changePassword(String userId, String newPassWord, String oldPassword) throws ServiceException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean checkPassword(String uId, String password) throws ServiceException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> getUserList(String groupCode) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public DepartmentInfo getDepartmentInfo(String departmentNum) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<GroupInfo> getGroupList(String departmentNum) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public GroupInfo getGroupInfo(String groupName) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getGroupDisplayName(@ParameterValueKeyProvider String s) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDepartmentDisplayName(@ParameterValueKeyProvider String s) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AcornRole> getRoleList(String userName) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<String> getManageGroupList(String departmentNum) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<DepartmentInfo> getDepartments() throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> getManageUserList(String departmentNum) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String loginSuccess(String userId) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String loginFailure(String userId) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getUserAttribute(String uid, String attribute) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> findUserByUid(String uid) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<GroupInfo> getAllGroup() throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<AgentUser> findUserLikeUid(String uid) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean releaseLock(String userId) throws ServiceException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getUserDN(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
