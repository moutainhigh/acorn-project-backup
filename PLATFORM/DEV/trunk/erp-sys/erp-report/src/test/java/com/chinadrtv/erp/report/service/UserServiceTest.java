package com.chinadrtv.erp.report.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.chinadrtv.erp.report.core.test.TestSupport;
import com.chinadrtv.erp.report.entity.User;

public class UserServiceTest extends TestSupport {
	
	@Autowired
	private UserService service;
	
	@Test
	public void testFindAll() {
		Page<User> page = service.findAll();
		for(User user : page.getContent()){
			logger.info(user.getName());
		}
	}

	/**
	 * 
	 */
	@Test
	public void testFindByUsername() {
		User user = service.findByUsername("27427");
		if(user!=null){
			logger.info(user.getId());
		}
	}

	/**
	 * 
	 */
	@Test
	public void testsFindByUsernameAndPassword() {
//		fail("Not yet implemented");
	}

}
