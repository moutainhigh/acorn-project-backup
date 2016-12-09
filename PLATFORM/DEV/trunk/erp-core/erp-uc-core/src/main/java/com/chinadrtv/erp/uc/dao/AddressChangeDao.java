/*
 * @(#)AddressChangeDao.java 1.0 2013-5-20下午5:37:42
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.AddressChange;

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
 * @since 2013-5-20 下午5:37:42 
 * 
 */
public interface AddressChangeDao extends GenericDao<AddressChange, Long> {

	/**
	 * <p></p>
	 * @param addressId
	 * @param taskId
	 * @return
	 */
	AddressChange queryByInstId(String addressId, String instId);

    /**
     * 根据bpmInstID查找
     * @param bpmInstID
     * @return
     */
    AddressChange queryByBpmInstID(String bpmInstID);
}
