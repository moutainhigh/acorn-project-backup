package com.chinadrtv.erp.uc.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.uc.test.AppTest;

/**
 * API-IC-1.	SCM满赠促销
 *  
 * @author haoleitao
 * @date 2013-5-14 下午3:52:38
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class SCMPromotiongServiceTest extends AppTest {
	
	@Autowired
	private SCMpromotionService sCMpromotionService;
	
	@Test
	public void testInit(){
		Assert.assertNotNull(sCMpromotionService);
	}
	
	@Test
	  public void scmPromotion(){

		  List list = sCMpromotionService.getCmsPromotion("1120865000,1140247309,1110401110,1150206505,1140100701","148.00,1372.00,1036.00,698.00,398.00", "sa", null);
		  System.out.println("list:"+list);
		  assertTrue(list != null);
	  }

}
