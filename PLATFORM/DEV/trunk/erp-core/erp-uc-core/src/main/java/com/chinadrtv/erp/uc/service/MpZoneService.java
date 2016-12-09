/*
 * @(#)MpZoneService.java 1.0 2013-6-5上午9:50:15
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.core.service.GenericService;
import com.chinadrtv.erp.model.agent.MpZone;

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
 * @since 2013-6-5 上午9:50:15 
 * 
 */
public interface MpZoneService extends GenericService<MpZone, String> {

	/**
	 * <p>API-UC-40.查询手机归属地</p>
	 * @param phoneNo	手机号码前7位
	 * @return MpZone
	 */
	MpZone queryByPhoneNo(String phoneNo);
	
}
