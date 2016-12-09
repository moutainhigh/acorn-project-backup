package com.chinadrtv.erp.report.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.report.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.report.core.service.BaseServiceImpl;
import com.chinadrtv.erp.report.dao.UserDao;
import com.chinadrtv.erp.report.entity.User;
import com.chinadrtv.erp.report.service.UserService;
import com.chinadrtv.erp.report.util.DefaultUserMap;

/**
 * @author zhangguosheng
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService{

	@Autowired
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.core.service.BaseServiceImpl#getDao()
	 */
	@Override
	public BaseRepository<User, String> getDao() {
		return userDao;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.UserService#findByUsername(java.lang.String)
	 */
	@Override
	public User findByUsername(String id) {
		User user = DefaultUserMap.findOne(id);
		if(user==null){
			user = userDao.findOne(id);
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see com.chinadrtv.erp.report.service.UserService#findByUsernameAndPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public User findByUsernameAndPassword(String id, String password) {
		User user = DefaultUserMap.findByIdAndPassword(id, password);
		if(user==null){
			user = userDao.findByIdAndPassword(id, password);
		}
		return user;
	}
	
	@Override
	public Page<User> findAll() {
		PageRequest pageable = new PageRequest(0, 10) ;
		return userDao.findAll(pageable);
	}
	
}
