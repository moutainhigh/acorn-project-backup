package com.chinadrtv.service.oms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.model.iagent.MailStatusHis;
import com.chinadrtv.service.iagent.MailStatusHisService;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-3-14
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class MailStatusHisServiceTest extends ServiceTest{
	
	@Autowired
    private MailStatusHisService<MailStatusHis> mailStatusHisService;
    
    /*@Test
    public void testInit(){
    	Assert.assertNotNull(mailStatusHisService);
    }*/

	@Test
	@Rollback(false)
	public void testSaveAll() throws Exception {
		List<MailStatusHis> list = new ArrayList<MailStatusHis>();
		for (int i = 0; i < 1; i++) {
			MailStatusHis mailStatusHis = new MailStatusHis();
			mailStatusHis.setMailid("ata308118604");
			//mailStatusHis.setShipmentId("928409222");
			//mailStatusHis.setStatusCode("AP");
			//mailStatusHis.setReasonCode("AZ");
			mailStatusHis.setEmsDateTimeStamp(new Date());
			mailStatusHis.setOperatype("OP01");
            mailStatusHis.setOperadate(new Date());
        mailStatusHis.setCompanyid("260");
        mailStatusHis.setOptor("柯丽娟");
        mailStatusHis.setStation("南昌分公司代收货款业务部");
        mailStatusHis.setRemarks("您的邮件于 2013-07-26 08:27:00 （莲花县坊楼邮政所）未投递成功 拒收退回,退回寄件人x");
			list.add(mailStatusHis);
		}
		mailStatusHisService.saveAll(list);
	}
}
