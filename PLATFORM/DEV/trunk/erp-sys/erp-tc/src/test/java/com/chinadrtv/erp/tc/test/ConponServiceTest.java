/*
 * @(#)ShipmentTest.java 1.0 2013-2-20上午9:27:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.tc.core.model.ConTicket;
import com.chinadrtv.erp.tc.core.service.CouponService;


/**
 * @author taoyawei
 * @version 1.0
 * @since 2013-2-20 上午9:27:08 
 * 
 */
public class ConponServiceTest extends TCTest {

	
	 @Autowired
	private CouponService couponService;

	 /**
	  * 赠券券新增
	  */
	//@Test
	public void conponaddTest(){
		try {
		//参数{"stype":"0","ncurprodprice":398,"curticketprice":0,"sorderid":"928203242",scontactid:"943398257",scrusr:"sa"}
		ConTicket conTicket=new ConTicket();
		conTicket.setStype("0");
		conTicket.setNcurprodprice(398.0);
		conTicket.setCurticketprice(0.0);
		conTicket.setSorderid("928203242");
		conTicket.setScontactid("943398257");
		conTicket.setScrusr("sa");
	            String str= couponService.getCouponproc(conTicket);
	            System.out.print("赠券新增成功");
	        } catch (Exception e) {
	            System.out.print("赠券新增失败" + e.getMessage());
	        }
	}

}
