package com.chinadrtv.uam.dao;

import com.chinadrtv.uam.model.auth.Role;

import java.util.List;

public interface RoleDao {
	
	Role getRoleByName(String name);

    List<Role> getRoleListByUserId(Long userId);

	List<Role> getRoleListByUserId(Long userId, Long siteId);
}
