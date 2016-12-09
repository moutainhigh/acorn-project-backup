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

import com.chinadrtv.erp.tc.core.service.DisHuifangMesService;


/**
 * @author taoyawei
 * @version 1.0
 * @since 2013-2-20 上午9:27:08 
 * 
 */
public class DisHuifangMesTest extends TCTest {

	@Autowired
    private DisHuifangMesService disHuifangMesService;
     /**
      * 处理回访数据信息
      */
    //@Test
    public void upDisHuifang() {
    	//参数{"remark":"1","fromdt":"2013-02-18 00:00:00","todt":"2013-02-20 00:00:00","ordertype":"2","mailtype":"1",
    	//"ordertype_renamed":"'1','2','3','21'","adusr":"sa","result":"1","adjusttrans":"false","adjustall":"false","adjusturgent":"false"}
    	Map<String, Object> map=new HashMap<String,Object>();
    	map.put("remark", "1");
    	map.put("fromdt", "2013-02-18 00:00:00");
    	map.put("todt", "2013-02-20 00:00:00");
    	map.put("ordertype", "2");
    	map.put("mailtype", "1");
    	map.put("ordertype_renamed", "'1','2','3','21'");
    	map.put("adusr", "sa");
    	map.put("result", "1");
    	map.put("adjusttrans", "false");
    	map.put("adjustall", "false");
    	map.put("adjusturgent", "false");
        try {
            boolean upid=disHuifangMesService.upOrderHistByfive(map);
            if(upid){
            System.out.print("处理回访信息成功");
            return;
            }
            System.out.print("处理回访信息失败");
        } catch (Exception e) {
        	System.out.print("处理回访信息失败");
        }
       }
    /**
     * 处理回访信息的赠券校正
     */
    //@Test
    public void couPon() {
    	Map<String,Object> integarl=new HashMap<String,Object>();
		integarl.put("sorderid","49996573744");
		integarl.put("scrusr","sa");
     try {
         disHuifangMesService.couponReviseproc(integarl);
         System.out.print("处理回访信息的校正赠券成功");
     } catch (Exception e) {
     	System.out.print("处理回访信息的校正赠券失败");
     }
    }
	}



