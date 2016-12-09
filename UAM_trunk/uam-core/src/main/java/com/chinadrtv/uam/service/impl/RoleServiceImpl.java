package com.chinadrtv.uam.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.uam.dao.RoleDao;
import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.service.RoleService;

/**
 * Created with IntelliJ IDEA.
 * User: zhoutaotao
 * Date: 14-3-18
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RoleServiceImpl extends ServiceSupportImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

    @Override
    public Role getRoleByName(String name) {
    	return roleDao.getRoleByName(name);
    }

    @Override
    public List<Role> getRoleListByUserId(Long userId) {
        return roleDao.getRoleListByUserId(userId);
    }
    
    @Override
    public List<Role> getRoleListByUserId(Long userId, Long siteId) {
    	return roleDao.getRoleListByUserId(userId, siteId);
    }
}
