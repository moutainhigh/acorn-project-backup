package com.chinadrtv.uam.test.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.chinadrtv.uam.dao.HibernateDao;
import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.model.auth.res.ResAction;
import com.chinadrtv.uam.model.uam.SysUser;
import com.chinadrtv.uam.test.BaseTest;

public class UserDetailsDaoTest extends BaseTest {
	
	@Resource
	private HibernateDao hibernateDao;

	@Test
//	@Rollback(false)
	public void testLoadResActions() {
		
		SysUser user = new SysUser();
		user.setSignName("dengqianyong");
		user.setSignPass("password");
		user.setEnabled(true);
		user.setUserName("邓钱咏");
		hibernateDao.save(user);
		
		Role role1 = new Role();
		role1.setName("role1");
		role1.setDescription("role1");
		hibernateDao.save(role1);
		
		Role role2 = new Role();
		role2.setName("role2");
		role2.setDescription("role2");
		hibernateDao.save(role2);
		
		ResAction a1 = new ResAction();
		a1.setActionUrl("www.a1.com");
		a1.setName("action1");
		a1.setDescription("action1");
		hibernateDao.save(a1);
		
		ResAction a2 = new ResAction();
		a2.setActionUrl("www.a2.com");
		a2.setName("action2");
		a2.setDescription("action2");
		hibernateDao.save(a2);
		
		ResAction a3 = new ResAction();
		a3.setActionUrl("www.a3.com");
		a3.setName("action3");
		a3.setDescription("action3");
		hibernateDao.save(a3);
		
		role1.getResources().add(a1);
		role1.getResources().add(a2);
		hibernateDao.update(role1);
		
		role2.getResources().add(a2);
		role2.getResources().add(a3);
		hibernateDao.update(role2);
		
		user.getRoles().add(role1);
		user.getRoles().add(role2);
		hibernateDao.update(user);
		
	}
	
}
