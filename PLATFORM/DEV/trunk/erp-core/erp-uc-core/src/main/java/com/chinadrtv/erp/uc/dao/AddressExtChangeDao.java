/*
 * @(#)AddressExtChangeDao.java 1.0 2013-5-20下午5:40:50
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.AddressExtChange;

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
 * @since 2013-5-20 下午5:40:50 
 * 
 */
public interface AddressExtChangeDao extends GenericDao<AddressExtChange, Long> {

	/**
	 * <p></p>
	 * @param addressId
	 * @param taskId
	 * @return
	 */
	AddressExtChange queryByInstId(String addressId, String instId);


    List<String> findProcInstFromAddressId(String addressId,Date beginDate);

    /**
     * 根据bpmInstID查找
     * @param bpmInstID
     * @return
     */
    AddressExtChange queryByBpmInstID(String bpmInstID);
}
