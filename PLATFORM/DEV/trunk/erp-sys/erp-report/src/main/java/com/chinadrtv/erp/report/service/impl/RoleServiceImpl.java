package com.chinadrtv.erp.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.report.dao.RoleDao;
import com.chinadrtv.erp.report.entity.Role;
import com.chinadrtv.erp.report.service.RoleService;

/**
 * @author zhangguosheng
 *
 */
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.RoleService#get(java.lang.Long)
	 */
	@Override
	public Role get(Long id) {
		return roleDao.get(Role.class, id);
	}
	
}
