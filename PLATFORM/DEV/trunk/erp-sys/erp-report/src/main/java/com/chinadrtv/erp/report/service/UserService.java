package com.chinadrtv.erp.report.service;

import org.springframework.data.domain.Page;

import com.chinadrtv.erp.report.core.service.BaseService;
import com.chinadrtv.erp.report.entity.User;


public interface UserService extends BaseService<User, String>{
	
	public Page<User> findAll();
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User findByUsernameAndPassword(String username, String password);
	
}
