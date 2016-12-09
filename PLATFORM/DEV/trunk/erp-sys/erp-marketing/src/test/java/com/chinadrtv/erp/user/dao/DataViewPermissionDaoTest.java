/*
 * @(#)DataViewPermissionDaoTest.java 1.0 2013-5-7下午3:28:04
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.dao;

import org.junit.Test;

import com.chinadrtv.erp.test.SpringTest;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-5-7 下午3:28:04 
 * 
 */
public class DataViewPermissionDaoTest extends SpringTest {

//	@Autowired
//	private DataViewPermissionDao dataViewPermissionDao;
	
	
	@Test
	public void test() {
		
		String condition = "111";
				//PermissionHelper.getFilterCondition("/customer/list");
//		DataViewPermission dataViewPermission = dataViewPermissionDao.query("/customer/list", "GROUPADMIN");
//		
//		System.out.println("---------------"+dataViewPermission.getFilterPermission().getFilter().getWhere_clause());
//		//SecurityHelper.getLoginUser();
//		AgentUser user = new AgentUser();
//		user.setDepartment("20000");
//		user.setName("test");
//		String whereClause = dataViewPermission.getFilterPermission().getFilter().getWhere_clause();
//		Map<String, Object> context = new HashMap<String, Object>();
//		context.put("user", user);
//		String namespace = ExpressionUtils.evalString(whereClause,
//				context);
		System.out.println(condition);
//		assertNotNull(condition);
	}

}
