/*
 * @(#)ShipmentTest.java 1.0 2013-2-20上午9:27:08
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.tc.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.tc.core.service.OrderIntegarlService;


/**
 * @author taoyawei
 * @version 1.0
 * @since 2013-2-20 上午9:27:08 
 * 
 */
public class JifenTest extends TCTest{

	
      @Autowired
	 private OrderIntegarlService orderIntegarlService;
     
	 /**
	  * 积分新增
	  */
	//@Test
	public void jifenaddTest(){
		try {
		//参数{"stype":"0","sorderid":"9913498969","scontactid":"972502205","npoint":0.0,"susrid":"sa"}	
			Map<String,Object> integarl=new HashMap<String,Object>();
			integarl.put("stype","0");
			integarl.put("sorderid","928203242");
			integarl.put("scontactid","943398257");
			integarl.put("npoint","0.0");
			integarl.put("scrusr","sa");
			
			  String str= orderIntegarlService.getIntegarlproc(integarl);
			  System.out.print("积分新增成功"+str);
	        } catch (Exception e) {
	            System.out.print("积分新增失败" + e.getMessage());
	        }
	}

}
