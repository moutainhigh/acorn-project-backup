package com.chinadrtv.uam.dao;

import com.chinadrtv.uam.model.uam.SysUser;

public interface SysUserDao {

	/**
	 * 加载用户，以及对应角色
	 * 
	 * @param username
	 * @return
	 */
	SysUser loadByName(String username);

}
