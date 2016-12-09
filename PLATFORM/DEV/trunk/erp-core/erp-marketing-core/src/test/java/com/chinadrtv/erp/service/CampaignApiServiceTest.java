/*
 * @(#)CampaignApiServiceTest.java 1.0 2013-5-13上午10:20:10
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.service;

import com.chinadrtv.erp.model.marketing.Campaign;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.JunitTestBase;
import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.service.CampaignApiService;

import java.util.List;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-5-13 上午10:20:10
 * 
 */
public class CampaignApiServiceTest extends JunitTestBase {
	@Autowired
	private CampaignApiService campaignApiService;

	/**
	 * @throws ServiceException
	 * 
	 * @Description: 查询inbound 进线营销活动
	 * @return void
	 * @throws
	 */
	@Test
	public void queryInboundCampaignTest() throws ServiceException {
//		CampaignDto cd = campaignApiService.queryInboundCampaign("111",
//				"2013-5-10 16:03:59");
//		System.out.println("TollFreeNum:" + cd.getTollFreeNum() + "name:"
//				+ cd.getName());
	}

	/**
	 * @throws ServiceException
	 * 
	 * @Description: 查询老客户营销活动
	 * @return void
	 * @throws
	 */
	@Test
	public void queryOldCustomerCampaign() throws ServiceException {
//		List<CampaignDto> list = campaignApiService
//				.queryOldCustomerCampaign("2000");
//		for (CampaignDto cd : list) {
//			System.out.println("TollFreeNum:" + cd.getTollFreeNum() + "name:"
//					+ cd.getName());
//		}
	}

    @Test
    public void TestfindCampaignByType(){

        List<Campaign> list = campaignApiService.findCampaignByType(6L) ;

        System.out.println(list);
    }



}
