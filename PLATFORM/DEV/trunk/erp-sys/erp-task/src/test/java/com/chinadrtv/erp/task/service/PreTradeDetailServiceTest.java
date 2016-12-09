package com.chinadrtv.erp.task.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.task.core.test.TestSupport;
import com.chinadrtv.erp.task.entity.PreTradeDetail;

public class PreTradeDetailServiceTest extends TestSupport{

	@Autowired
	private PreTradeDetailService service;
	
	@Test
	public void testGet() {
		PreTradeDetail obj = service.get(8687L);
		System.err.println(obj.getTradeId());
	}
	
	@Test
	public void testGetAllPreTradeDetailByPerTradeID() {
		List<PreTradeDetail> list = service.getAllPreTradeDetailByPerTradeID("246477");
		for(PreTradeDetail obj : list){
			System.err.println(obj.getSkuName());
		}
	}

}
