/*
 * @(#)ShipmentTest.java 1.0 2013-2-20上午9:27:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.tc.core.model.ConTicket;
import com.chinadrtv.erp.tc.core.service.CouponReviseService;


/**
 * @author taoyawei
 * @version 1.0
 * @since 2013-2-20 上午9:27:08 
 * 
 */
public class ConponReviseServiceTest extends TCTest {

	 @Autowired
	private CouponReviseService couponReviseService;

    @Test
    public void testMok()
    {

    }

	 /**
	  * 返券校正
	  */
	//@Test
	public void conponReviseTest(){
		try {
		//参数{"sorderid":"9913498969","scontactid":"972502205","scrusr":"sa"}
		ConTicket conTicket=new ConTicket();
		conTicket.setSorderid("9913498969");
		conTicket.setScontactid("97250220");
		conTicket.setScrusr("sa");
			couponReviseService.getCouponReviseproc(conTicket);
			System.out.print("返券校正成功");
		} catch (SQLException e) {
			System.out.print("返券校正错误"+e.getMessage());
		}
		
	}
	

}
