package com.chinadrtv.erp.task.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.chinadrtv.erp.task.core.test.TestSupport;
import com.chinadrtv.erp.task.entity.User;

public class UserServiceTest extends TestSupport {
	
	@Autowired
	private UserService service;

	@Test
	public void testFindAll() {
		PageRequest pageable = new PageRequest(0, 13) ;
		Page<User> page = service.queryUser(pageable);
		for(User user : page.getContent()){
			logger.info(user.getName());
		}
	}

}
