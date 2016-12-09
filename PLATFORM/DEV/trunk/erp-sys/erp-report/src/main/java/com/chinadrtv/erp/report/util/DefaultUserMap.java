package com.chinadrtv.erp.report.util;

import java.util.ArrayList;
import java.util.List;

import com.chinadrtv.erp.report.entity.User;

public class DefaultUserMap {
	
	private static List<User> userList = new ArrayList<User>();
	
	static{
		
		User user1 = new User();
		user1.setId("user");
		user1.setPassword("123456");
		user1.setName("Default one");
		
		User user2 = new User();
		user2.setId("visitors");
		user2.setPassword("123456");
		user2.setName("Visitors one");
		
		userList.add(user1);
		userList.add(user2);
		
	}

	/**
	 * 查找一个用户
	 * @param id
	 * @return
	 */
	public static User findOne(String id) {
		for(User user : userList){
			if(user.getId().equals(id)){
				return user;
			}
		}
		return null;
	}

	/**
	 * 查找一个用户
	 * @param id
	 * @param password
	 * @return
	 */
	public static User findByIdAndPassword(String id, String password) {
		for(User user : userList){
			if(user.getId().equals(id) && user.getPassword().equals(password)){
				return user;
			}
		}
		return null;
	}

}
