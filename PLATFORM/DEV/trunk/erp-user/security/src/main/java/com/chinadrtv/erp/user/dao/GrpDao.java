/*
 * @(#)GrpDao.java 1.0 2013-6-20下午2:07:25
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Grp;

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
 * @author andrew
 * @version 1.0
 * @since 2013-6-20 下午2:07:25 
 * 
 */
public interface GrpDao extends GenericDao<Grp, String> {
      List<Grp> getGrpList(List<String> grpIdList);
      
      public List<Grp> getGrpList(String departmentNum);
      
      public boolean checkGrpMangerRole(String groupId);
}
