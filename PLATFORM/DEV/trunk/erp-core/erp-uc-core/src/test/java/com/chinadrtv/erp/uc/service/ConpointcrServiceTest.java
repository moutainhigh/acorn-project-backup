package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.agent.Conpointcr;
import com.chinadrtv.erp.uc.test.AppTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
/**
 * 积分服务测试用例 
 *  
 * @author haoleitao
 * @date 2013-5-9 下午1:09:03
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class ConpointcrServiceTest extends AppTest {

	@Autowired
	private ConpointcrService conpointcrService;
	@Autowired
	private ContactService contactService;
	@Test
	public void testInit(){
		Assert.assertNotNull(conpointcrService);
	}
	
	/**
	 * API-UC-25.	查询积分生成历史
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	@Test
	public void getAllConpointcrByContactId(){
		String contactId= "925546186";
		int index = 1;
		int size = 10;
		List<Conpointcr> list = conpointcrService.getAllConpointcrByContactId(contactId, index, size);
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * API-UC-25.	查询积分生成历史的数量
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	@Test
	public void getAllConpointcrByContactIdCount(){
		String contactId= "925546186";
	    int count = conpointcrService.getAllConpointcrByContactIdCount(contactId);
		Assert.assertTrue(count>0);
	}
	
	
	/**
	 * API-UC-28.	消费积分
     * API-UC-26.	新增积分
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	@Test
	public void conpointfeedback(){
		String sorderid= "928503232";//orderid
		String scrusr="sa";//坐席ID
	    try {
			conpointcrService.conpointfeedback(sorderid, scrusr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
     * API-UC-24.	查询积分
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	//@Test
	public void findJiFenByContactId() throws Exception {
		String contactId= "925546186";//orderid
        Double d=0.0;
		try {
			d = contactService.findJiFenByContactId(contactId);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(d==8.946);
	}
	
	
	
	
}
