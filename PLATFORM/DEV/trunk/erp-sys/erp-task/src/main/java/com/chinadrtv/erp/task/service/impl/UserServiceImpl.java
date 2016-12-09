package com.chinadrtv.erp.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.core.service.BaseServiceImpl;
import com.chinadrtv.erp.task.dao.oms.UserDao;
import com.chinadrtv.erp.task.entity.User;
import com.chinadrtv.erp.task.service.UserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public BaseRepository<User, String> getDao() {
		return userDao;
	}

	@Override
	public Page<User> queryUser(Pageable pageable) {
		return userDao.findAll(pageable);
	}

}
