/*
 * @(#)SmsApiServiceTest.java 1.0 2013-5-16上午10:26:35
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.service;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.JunitTestBase;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.marketing.core.service.SmsApiService;
import com.chinadrtv.erp.smsapi.constant.DataGridModel;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-16 上午10:26:35
 * 
 */
public class SmsApiServiceTest extends JunitTestBase {
	@Autowired
	private SmsApiService smsApiService;

	/**
	 * 
	 * @Description: 单条短信发送
	 * @return void
	 * @throws
	 */
	// @Test
	// public void testSmsSend() {
	// SmsSendDto smsDto = new SmsSendDto();
	// smsDto.setMobile("13918389281");
	// smsDto.setCustomerId("12345678");
	// smsDto.setDepartment("1000");
	// smsDto.setSmsContent("你好");
	// smsDto.setSource("test");
	// smsDto.setCreator("cm");
	// String uuid;
	// try {
	// uuid = smsApiService.singleSmsSend(smsDto);
	// System.out.println(uuid);
	// } catch (ServiceException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	/**
	 * 
	 * @Description: 根据uui查询短信
	 * @return void
	 * @throws
	 */
	@Test
	public void querySmsByUUID() {
//		String uuid = "2013042624134129000000030";
//		SmsSendDto smsSend;
//		try {
//			smsSend = smsApiService.getSmsByUuid(uuid);
//			System.out.println(smsSend.getSmsContent() + "==="
//					+ smsSend.getSmsStatus());
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * 
	 * @Description: 短信模板查询
	 * @return void
	 * @throws
	 */

//	@Test
//	public void querySmsTemplate() {
//		String department = "8888";
//		String theme = "懒得动营销";
//		DataGridModel dataGridModel = new DataGridModel();
//		dataGridModel.setStartRow(1);
//		Map<String, Object> map;
//		try {
//			map = smsApiService.findSmsTemplatePageList(department, theme,
//					dataGridModel);
//			System.out.println(map.get("total") + "==" + map.get("rows"));
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
