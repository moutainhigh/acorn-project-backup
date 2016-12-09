/*
 * @(#)PhoneChangeDao.java 1.0 2013-6-3下午1:50:36
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.PhoneChange;

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
 * @since 2013-6-3 下午1:50:36 
 * 
 */
public interface PhoneChangeDao extends GenericDao<PhoneChange, Long> {

	/**
	 * <p>根据实例号和业务号查询</p>
	 * @param phoneId
	 * @param instId
	 * @return
	 */
	PhoneChange queryByTaskId(Long phoneId, String instId);

    /**
     * 根据bpmInstID查找
     * @param bpmInstID
     * @return
     */
    PhoneChange queryByBpmInstID(String bpmInstID);
}
