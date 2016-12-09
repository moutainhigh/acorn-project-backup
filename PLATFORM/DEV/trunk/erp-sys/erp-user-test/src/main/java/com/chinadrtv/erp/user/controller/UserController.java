package com.chinadrtv.erp.user.controller;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.ldap.AcornRole;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.model.DepartmentInfo;
import com.chinadrtv.erp.user.model.GroupInfo;
import com.chinadrtv.erp.user.service.UserService;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.converter.ConvertingComparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import java.util.List;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 14-3-25
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Controller
@RequestMapping(value = "user")
public class UserController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    public UserController()
    {
        //ConvertingComparator
        logger.debug("controller is created!!!");
    }

    @RequestMapping(value = "/getUserGroup/{userName}")
    @ResponseBody
    public String getUserGroup(@PathVariable String userName) throws ServiceException {
        String str= userService.getUserGroup(userName);
        return str;
    }

    @RequestMapping(value = "/getDepartment/{userName}")
    @ResponseBody
    public String getDepartment(@PathVariable String userName) throws ServiceException, NamingException {
        return userService.getDepartment(userName);
    }

    @RequestMapping(value = "/getGroupAreaCode/{userName}")
    @ResponseBody
    public String getGroupAreaCode(@PathVariable String userName) throws ServiceException {
        return userService.getGroupAreaCode(userName);
    }

    @RequestMapping(value = "/getGroupType/{userName}")
    @ResponseBody
    public String getGroupType(@PathVariable String userName) throws ServiceException {
        return userService.getGroupType(userName);
    }

    @RequestMapping(value = "/getAreaCode/{groupName}")
    @ResponseBody
    public String getAreaCode(@PathVariable String groupName) throws ServiceException {
        return userService.getAreaCode(groupName);
    }

    @RequestMapping(value = "/changePassword")
    @ResponseBody
    public void changePassword(@RequestParam(required = false)String userId, @RequestParam(required = false)String newPassWord, @RequestParam(required = false)String oldPassword) throws ServiceException {
        if(StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(newPassWord)&&StringUtils.isNotBlank(oldPassword))
            userService.changePassword(userId,newPassWord,oldPassword);
    }

    @RequestMapping(value = "/checkPassword/{uId}/{password}")
    @ResponseBody
    public boolean checkPassword(@PathVariable("uId") String uId, @PathVariable("password")String password) throws ServiceException {
        return userService.checkPassword(uId,password);
    }

    @RequestMapping(value = "/getUserList/{groupCode}")
    @ResponseBody
    public List<AgentUser> getUserList(@PathVariable String groupCode) throws ServiceException {
        return userService.getUserList(groupCode);
    }

    @RequestMapping(value = "/getDepartmentInfo/{departmentNum}")
    @ResponseBody
    public DepartmentInfo getDepartmentInfo(@PathVariable String departmentNum) throws ServiceException {
        return userService.getDepartmentInfo(departmentNum);
    }

    @RequestMapping(value = "/getGroupList/{departmentNum}")
    @ResponseBody
    public List<GroupInfo> getGroupList(@PathVariable String departmentNum) throws ServiceException {
        return userService.getGroupList(departmentNum);
    }

    @RequestMapping(value = "/getGroupInfo/{groupName}")
    @ResponseBody
    public GroupInfo getGroupInfo(@PathVariable String groupName) throws ServiceException {
        return userService.getGroupInfo(groupName);
    }


    public String getGroupDisplayName(@ParameterValueKeyProvider String groupName) throws ServiceException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public List<AcornRole> getRoleList(String userName) throws ServiceException {
        return userService.getRoleList(userName);
    }

    @RequestMapping(value = "/getManageGroupList/{departmentNum}")
    @ResponseBody
    public List<String> getManageGroupList(@PathVariable String departmentNum) throws ServiceException {
        return userService.getManageGroupList(departmentNum);
    }

    @RequestMapping(value = "/getDepartments")
    @ResponseBody
    public List<DepartmentInfo> getDepartments() throws ServiceException {
        return userService.getDepartments();
    }

    @RequestMapping(value = "/getManageUserList/{departmentNum}")
    @ResponseBody
    public List<AgentUser> getManageUserList(@PathVariable String departmentNum) throws ServiceException {
        return userService.getManageUserList(departmentNum);
    }

    @RequestMapping(value = "/loginSuccess/{userId}")
    @ResponseBody
    public String loginSuccess(@PathVariable String userId) throws ServiceException {
        return userService.loginSuccess(userId);
    }

    @RequestMapping(value = "/loginFailure/{userId}")
    @ResponseBody
    public String loginFailure(@PathVariable String userId) throws ServiceException {
        return userService.loginFailure(userId);
    }


    public String getUserAttribute(String uid, String attribute) throws ServiceException {
        return userService.getUserAttribute(uid,attribute);
    }

    @RequestMapping(value = "/findUserByUid/{uid}")
    @ResponseBody
    public List<AgentUser> findUserByUid(@PathVariable String uid) throws ServiceException {
        return userService.findUserByUid(uid);
    }

    @RequestMapping(value = "/getAllGroup")
    @ResponseBody
    public List<GroupInfo> getAllGroup() throws ServiceException {
        return userService.getAllGroup();
    }

    @RequestMapping(value = "/findUserLikeUid/{uid}")
    @ResponseBody
    public List<AgentUser> findUserLikeUid(@PathVariable String uid) throws ServiceException {
        return userService.findUserLikeUid(uid);
    }

    @RequestMapping(value = "/releaseLock/{userId}")
    @ResponseBody
    public boolean releaseLock(@PathVariable String userId) throws ServiceException {
        return userService.releaseLock(userId);
    }

    @ExceptionHandler
    @ResponseBody
    public void handleException(Exception ex)
    {
        logger.error("controller error:",ex);
    }
}
