package com.chinadrtv.erp.task.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.chinadrtv.erp.task.core.service.BaseService;
import com.chinadrtv.erp.task.entity.User;


public interface UserService extends BaseService<User, String>{
	
	public Page<User> queryUser(Pageable pageable);
	
}
