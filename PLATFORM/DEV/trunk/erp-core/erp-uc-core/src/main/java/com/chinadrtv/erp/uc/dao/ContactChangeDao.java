/*
 * @(#)ContactChangeDao.java 1.0 2013-5-20下午6:09:50
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.ContactChange;

import java.util.Date;
import java.util.List;

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
 * @since 2013-5-20 下午6:09:50 
 * 
 */
public interface ContactChangeDao extends GenericDao<ContactChange, Long> {

	/**
	 * <p>根据流程编号查询</p>
	 * @param contactId
	 * @param taskId
	 * @return ContactChange
	 */
	ContactChange queryByTaskId(String contactId, String taskId);


    List<String> findProcInstFromContactId(String contactId,Date beginDate);
    /**
     * 根据bpmInstID查找
     * @param bpmInstID
     * @return
     */
    ContactChange queryByBpmInstID(String bpmInstID);
}
