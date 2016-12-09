package com.chinadrtv.erp.uc.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.erp.model.marketing.Lead;
import com.chinadrtv.erp.model.agent.CallHist;

import com.chinadrtv.erp.test.SpringTest;
import com.chinadrtv.erp.uc.dao.LeadDao;

public class CallHistServiceTest extends SpringTest {

	@Autowired
	//@Qualifier("callHistService")
	private CallHistService callHistService;
	
	@Autowired
	//@Qualifier("leadDao")
	private LeadDao leadDao;
	
	@Test
	@Rollback(true)
	public void testAdd() {
		Lead lead = leadDao.get(65L);
		Assert.assertNotNull("Lead为Null", lead);
		
		callHistService.addCallHist(lead, "99999999", new Date(), new Date(), "1111",
				"1", "1", "IN");
		Assert.assertTrue(true);
	}
	
	@Test
	public void getCallHistByContactId() {
		String contactId = "943498257";
		Date sdt = null;
		Date edt = null;
		int index =0;
		int size = 10;
		List<CallHist> list;
		try {
			list = callHistService.getCallHistByContactId(contactId, sdt, edt, index, size);
			Assert.assertTrue(list.size()>0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	@Test
	public void getCallHistByContactIdCount() {
		String contactId = "943498257";
		Date sdt = null;
		Date edt = null;
		int count;
		try {
			count = callHistService.getCallHistByContactIdCount(contactId, sdt, edt);
			Assert.assertTrue(count >0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
