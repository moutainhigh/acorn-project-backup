/**
 * 
 */
package com.chinadrtv.erp.user.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.user.model.UserGroup;

/**
 *  
 * @author haoleitao
 * @date 2013-3-19 上午10:10:51
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public interface UserGroupDao extends GenericDao<UserGroup,String> {
	public List<UserGroup> getUserGroupsByUserId(String userId);

}
