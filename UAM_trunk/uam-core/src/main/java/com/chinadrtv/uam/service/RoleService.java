package com.chinadrtv.uam.service;

import com.chinadrtv.uam.model.auth.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-18
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
public interface RoleService extends ServiceSupport {
    /**
     * 通过名称查询角色
     * @param name
     * @return
     */
    Role getRoleByName(String name);

    /**
     * 通过用户id查询对应角色列表
     * @param userId
     * @return
     */
	List<Role> getRoleListByUserId(Long userId);

    /**
     * 根据用户id查询对应site中的角色列表
     * 
     * @param userId
     * @param siteId
     * @return
     */
	List<Role> getRoleListByUserId(Long userId, Long siteId);
    
}
