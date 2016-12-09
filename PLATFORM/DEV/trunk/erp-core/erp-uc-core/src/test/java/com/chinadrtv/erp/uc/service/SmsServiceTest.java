/*
 * @(#)SmsServiceTest.java 1.0 2014年1月9日上午11:19:26
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
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
 * @since 2014年1月9日 上午11:19:26 
 * 
 */
public class SmsServiceTest extends AppTest{

	@Autowired
	private SmsService smsService;
	
	//@Test
	public void testSendSms() throws ServiceException {
		String orderId = "";
		
		SmsSendDto smsSendDto = new SmsSendDto();
		smsSendDto.setCustomerId("910393294");
		smsSendDto.setMobile("15073688638");
		smsSendDto.setSmsContent("上门自提地址");
		smsSendDto.setSmsName("上门自提地址");
		smsSendDto.setDepartment("2");
		smsSendDto.setCreator("27430");
		
		smsService.sendSms(smsSendDto, orderId);
	}
}
