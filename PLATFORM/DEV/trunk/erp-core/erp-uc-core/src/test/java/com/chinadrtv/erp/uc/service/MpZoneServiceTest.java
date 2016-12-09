/*
 * @(#)MpZoneServiceTest.java 1.0 2013-6-5上午9:59:07
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.agent.MpZone;
import com.chinadrtv.erp.uc.test.AppTest;

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
 * @since 2013-6-5 上午9:59:07 
 * 
 */
public class MpZoneServiceTest extends AppTest {

	@Autowired
	private MpZoneService mpZoneService;
	
	@Test
	public void queryByPhoneNo(){
		String phoneNo = "18616756132";
		phoneNo = phoneNo.substring(0, 7);
		MpZone mpZone = mpZoneService.queryByPhoneNo(phoneNo);
		Assert.assertNotNull(mpZone);
		Assert.assertNotNull(mpZone.getProvince());
		Assert.assertNotNull(mpZone.getCity());
	}
	

	
	//@Test
	public void truncate(){
		Date now = new Date();
	    Date truncYear = DateUtils.truncate(now, Calendar.YEAR);
	    Date truncDate = DateUtils.truncate(now, Calendar.DATE);
	    Date truncMonth = DateUtils.truncate(now, Calendar.MONTH);
	    System.out.println("now: " + DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(now));
	    System.out.println("truncDate: " + DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(truncDate));
	    System.out.println("truncYear: " + DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(truncYear));
	    System.out.println("truncMonth: " + DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(truncMonth));
	}
}
