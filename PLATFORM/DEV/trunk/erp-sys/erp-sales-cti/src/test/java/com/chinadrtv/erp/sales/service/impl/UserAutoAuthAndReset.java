/**
 * Copyright (c) 2014, Acorn and/or its affiliates. All rights reserved.
 * ACORN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.chinadrtv.erp.sales.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.test.SpringTest;
import com.chinadrtv.erp.user.service.LdapService;

/**
 * 2014-2-25 下午4:19:29
 * @version 1.0.0
 * @author yangfei
 *
 */
public class UserAutoAuthAndReset extends SpringTest {
	
	@Autowired
	private LdapService ldapService;
	
	public static void main(String[] args) throws Exception {
		UserAutoAuthAndReset userAutoAuth = new UserAutoAuthAndReset();
		userAutoAuth.importUserList("C:\\Users\\yangfei\\Desktop\\ldap\\20140225上线座席\\userlist.txt");
	}

	@Test
	public void testUserAutoAuth() throws Exception {
		String filePath = "C:\\Users\\yangfei\\Desktop\\ldap\\20140225上线座席\\userlist.txt";
		List<String> userList = importUserList(filePath);
		boolean isSuc = true;
		for(String userId : userList) {
			isSuc = ldapService.checkPassword(userId, "123456");
			if(!isSuc) {
				System.out.println(userId+" failed.");
			}
		}
	}
	
	private List<String> importUserList(String filePath) throws Exception {
		List<String> userList = new ArrayList<String>();
		File userFile = new File(filePath);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(userFile));
		Scanner scanner = new Scanner(bis).useDelimiter("\r\n");
		while(scanner.hasNext()) {
			userList.add(scanner.next());
		}
		return userList;
	}
	
	
}
