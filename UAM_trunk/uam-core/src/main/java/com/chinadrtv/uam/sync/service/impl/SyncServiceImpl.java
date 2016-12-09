package com.chinadrtv.uam.sync.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.uam.model.auth.Department;
import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.model.auth.User;
import com.chinadrtv.uam.model.auth.UserGroup;
import com.chinadrtv.uam.model.auth.UserMapping;
import com.chinadrtv.uam.model.cas.Site;
import com.chinadrtv.uam.service.RoleService;
import com.chinadrtv.uam.service.SiteService;
import com.chinadrtv.uam.service.UserService;
import com.chinadrtv.uam.sync.model.AcornRole;
import com.chinadrtv.uam.sync.model.AgentUser;
import com.chinadrtv.uam.sync.model.DepartmentInfo;
import com.chinadrtv.uam.sync.model.GroupInfo;
import com.chinadrtv.uam.sync.service.LdapService;
import com.chinadrtv.uam.sync.service.SyncService;

/**
 * 同步数据
 */
@Service
public class SyncServiceImpl implements SyncService {
    @Autowired
    private LdapService ldapService;
    @Autowired
    private UserService userService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private RoleService roleService;

    private static String LDAP = "LDAP";
    private static String SITE_NAME = "sales";

    @Override
    public void syncLdapInfo() {
        List<DepartmentInfo> departments = ldapService.getDepartmentList();

        if (departments == null) {
            return;
        }
        for (DepartmentInfo departmentInfo : departments) {
            Department department = userService.getDepartment(LDAP, departmentInfo.getId());
            if (department == null) {
                department = new Department();
                department.setCreateDate(new Date());
            } else {
                department.setUpdateDate(new Date());
            }

            department.setSource(LDAP);
            department.setSourceId(departmentInfo.getId());
            department.setCode(departmentInfo.getCode());
            department.setAcornAreaCode(departmentInfo.getAcornAreaCode());
            department.setAcornName(departmentInfo.getAcornName());
            department.setName(departmentInfo.getName());

            userService.saveOrUpdate(department);

            saveUserGroup(departmentInfo, department);
        }

    }

    private void saveUserGroup(DepartmentInfo departmentInfo, Department department) {
        List<GroupInfo> groupInfoList = ldapService.getGroupList(departmentInfo);

        if (groupInfoList == null) {
            return;
        }
        for (GroupInfo groupInfo : groupInfoList) {
            UserGroup userGroup = userService.getUserGroup(LDAP, groupInfo.getId());
            if (userGroup == null) {
                userGroup = new UserGroup();
                userGroup.setCreateDate(new Date());
            } else {
                userGroup.setUpdateDate(new Date());
            }

            userGroup.setName(groupInfo.getName());
            userGroup.setDepartment(department);
            userGroup.setSourceId(groupInfo.getId());
            userGroup.setSource(LDAP);
            userGroup.setAcornAreaCode(groupInfo.getAreaCode());
            userGroup.setAcornGroupType(groupInfo.getType());

            userService.saveOrUpdate(userGroup);

            saveUser(groupInfo, userGroup);
        }

    }

    private void saveUser(GroupInfo groupInfo, UserGroup userGroup) {
        List<AgentUser> userList = ldapService.getUserList(groupInfo.getId());

        if (userList == null) {
            return;
        }
        for (AgentUser agentUser : userList) {
            UserMapping userMapping = userService.getUserMapping(LDAP, agentUser.getUserId());
            User user = null;
            if (userMapping == null) {
                user = new User();
                user.setCreateDate(new Date());
                user.setValid(true);
            } else {
                user = userService.get(User.class, userMapping.getUserId());
                user.setUpdateDate(new Date());
                System.out.println("冲突帐号："+userMapping.getSourceId()+"Grp:"+user.getDefGrp()+"NGrp:"+groupInfo.getId());

            }

            user.setName(agentUser.getDisplayName());
            user.setUserGroup(userGroup);
            user.setDescription(agentUser.getDescription());
            user.setEmployeeType(agentUser.getEmployeeType());
            user.setAcornLastTime(agentUser.getLastTime());
            user.setAcornMaxFailure(agentUser.getMaxFailure());
            String grp = StringUtils.isNotBlank(agentUser.getDefGrp())?agentUser.getDefGrp(): userGroup.getSourceId();
            user.setDefGrp(grp);
            //FIXME 为了兼容sales。workgrp跟defgrp使用相同值
            user.setWorkGroup(grp);
            userService.saveOrUpdate(user);

            if (userMapping == null) {
                userMapping = new UserMapping();
                userMapping.setUserId(user.getId());
                userMapping.setSourceId(agentUser.getUserId());
                userMapping.setSource(LDAP);
                userMapping.setSourcePassword(agentUser.getPassword());
                userService.save(userMapping);
            }else {
                userMapping.setSourcePassword(agentUser.getPassword());
                userService.update(userMapping);
            }
        }
    }

    @Override
    public void syncLdapRoles() {

        Site site = this.getSite(SITE_NAME);

        List<AcornRole> acornRoleList = ldapService.getAcornRoleList();

        for (AcornRole acornRole : acornRoleList) {
            Role role = roleService.getRoleByName(acornRole.getName());
            if(role ==null){
                role = new Role();
                role.setName(acornRole.getName());
                role.setDescription(acornRole.getDescription());
                role.setSite(site);
                role.setAcornPriority(acornRole.getAcornPriority());

                roleService.save(role);
            }

            List<String> userIdList = ldapService.getUserIdList(acornRole.getName());

            for (String ldapUserId : userIdList) {

                UserMapping userMapping = userService.getUserMapping(LDAP, ldapUserId);
                if (userMapping != null) {
                    User user = userService.get(User.class, userMapping.getUserId());
                    user.getRoles().add(role);
                    userService.update(user);
                }

            }

        }

    }

    @Override
    public void syncUser(String userId,String groupCode) {
        AgentUser agentUser = ldapService.findUserByUid(userId);

        UserMapping userMapping = userService.getUserMapping(LDAP, agentUser.getUserId());
        User user = null;
        if (userMapping == null) {
            user = new User();
            user.setCreateDate(new Date());
            user.setValid(true);
        } else {
            user = userService.get(User.class, userMapping.getUserId());
            user.setUpdateDate(new Date());
        }

        user.setName(agentUser.getDisplayName());
        UserGroup userGroup = userService.getUserGroup(LDAP, groupCode);
        user.setUserGroup(userGroup);
        user.setDescription(agentUser.getDescription());
        user.setEmployeeType(agentUser.getEmployeeType());
        user.setAcornLastTime(agentUser.getLastTime());
        user.setAcornMaxFailure(agentUser.getMaxFailure());
        String grp = StringUtils.isNotBlank(agentUser.getDefGrp())?agentUser.getDefGrp(): groupCode;
        user.setDefGrp(grp);
        //FIXME 为了兼容sales。workgrp跟defgrp使用相同值
        user.setWorkGroup(grp);
        userService.saveOrUpdate(user);

        if (userMapping == null) {
            userMapping = new UserMapping();
            userMapping.setUserId(user.getId());
            userMapping.setSourceId(agentUser.getUserId());
            userMapping.setSource(LDAP);
            userMapping.setSourcePassword(agentUser.getPassword());
            userService.save(userMapping);
        }else {
            userMapping.setSourcePassword(agentUser.getPassword());
            userService.update(userMapping);
        }
    }

    private Site getSite(String siteName) {
        Site site = siteService.getSiteByName(siteName);
        if (site == null) {
            site = new Site();
            site.setName(siteName);
            site.setDescription("sales系统");
            site.setEvaluationOrder(99);
            siteService.save(site);
        }
        return site;
    }
}
