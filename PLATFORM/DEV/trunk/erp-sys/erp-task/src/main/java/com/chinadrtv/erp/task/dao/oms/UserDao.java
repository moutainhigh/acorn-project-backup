package com.chinadrtv.erp.task.dao.oms;

import org.springframework.stereotype.Repository;

import com.chinadrtv.erp.task.core.repository.jpa.BaseRepository;
import com.chinadrtv.erp.task.entity.User;

@Repository
public interface UserDao extends BaseRepository<User, String> {
	
}
