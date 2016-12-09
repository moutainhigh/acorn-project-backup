package com.chinadrtv.erp.report.dao;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.report.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.report.entity.User;

@Repository
public interface UserDao extends BaseRepository<User, String> {
	
	/**
	 * 查找
	 * @param id
	 * @param password
	 * @return
	 */
	public User findByIdAndPassword(String id, String password);
	
}
