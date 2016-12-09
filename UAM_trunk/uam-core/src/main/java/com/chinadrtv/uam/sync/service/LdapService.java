package com.chinadrtv.uam.sync.service;

import java.util.List;

import com.chinadrtv.uam.sync.model.AcornRole;
import com.chinadrtv.uam.sync.model.AgentUser;
import com.chinadrtv.uam.sync.model.DepartmentInfo;
import com.chinadrtv.uam.sync.model.GroupInfo;

/**
 * LADP获取接口
 */
public interface LdapService {
    /**
     * 获取部门
     *
     * @return
     */
    public List<DepartmentInfo> getDepartmentList();

    /**
     * 通过部门号获取用户组
     *
     * @param departmentInfo
     * @return
     */
    public List<GroupInfo> getGroupList(DepartmentInfo departmentInfo);

    /**
     * 获取用户
     *
     * @param groupCode
     * @return
     */
    public List<AgentUser> getUserList(String groupCode);

    /**
     * 获取角色
     *
     * @return
     */
    public List<AcornRole> getAcornRoleList();

    /**
     * 通过角色获取用户
     *
     * @param acornRole
     * @return
     */
    List<String> getUserIdList(String acornRole);

    public List<String> getManageGroupList(String departmentNum);

    public AgentUser findUserByUid(String userId);
}
