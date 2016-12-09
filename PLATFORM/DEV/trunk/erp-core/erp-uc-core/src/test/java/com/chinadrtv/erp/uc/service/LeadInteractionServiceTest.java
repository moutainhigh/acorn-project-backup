package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.uc.test.AppTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  
 * @author haoleitao
 * @date 2013-5-14 下午2:08:11
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class LeadInteractionServiceTest extends AppTest {
	
	@Autowired
	private LeadInteractionService leadInteractionService;
	
	@Test
	public void testInit(){
		Assert.assertNotNull(leadInteractionService);
	}
    @Test
    public void testAllCount(){
       Assert.assertTrue(0<leadInteractionService.getAllLeadInteractionByContactIdCount("943398257")); ;
    }

    @Test
    public void testAll(){
        System.out.println(leadInteractionService.getAllLeadInteractionByContactId("943398257",0,10).size());
        Assert.assertTrue(0<leadInteractionService.getAllLeadInteractionByContactId("943398257",0,10).size()); ;
    }
	

}
