package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.model.agent.Conpointuse;
import com.chinadrtv.erp.uc.test.AppTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;




public class ConpointuseServiceTest extends AppTest {
	@Autowired
	private ConpointuseService conpointuseService;
	
	@Test
	public void testInit(){
		Assert.assertNotNull(conpointuseService);
	}
	
	/**
	 * API-UC-25.	查询积分生成历史
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	@Test
	public void getAllConpointuseByContactId(){
		String contactId= "943492257";
		int index = 1;
		int size = 10;
		List<Conpointuse> list = conpointuseService.getAllConpointuseByContactId(contactId, index, size);
		Assert.assertTrue(list.size()>0);
	}
	
	/**
	 * API-UC-25.	查询积分生成历史的数量
	* <p>Title: </p>
	* <p>Description: </p>
	 */
	@Test
	public void getAllConpointcrByContactIdCount(){
		String contactId= "943492257";
	    int count = conpointuseService.getAllConpointuseByContactIdCount(contactId);
		Assert.assertTrue(count>0);
	}
	
	
}
