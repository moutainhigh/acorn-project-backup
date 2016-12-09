package com.chinadrtv.erp.oms.service;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.model.PreTrade;
import com.chinadrtv.erp.model.PreTradeDetail;
import com.chinadrtv.erp.oms.controller.ExcludeUnionResource;
import com.chinadrtv.erp.test.SpringTest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * 前置订单 测试
 * @author haoleitao
 * @date 2012-12-28 下午4:38:12
 *
 */
public class PreTradeServiceTest extends SpringTest{
	
	 @Autowired
	 private PreTradeService preTradeService;
	 
	@Test
    public void getById(){
		assertTrue(preTradeService.getById(Long.valueOf("235089")).getId().equals(Long.valueOf("235089")));
    }
	
	@Test
	public void PreTradeToJson(){
		PreTrade preTrade = preTradeService.getById(Long.valueOf("17554"));
		Gson gson = new GsonBuilder()
		.setDateFormat("yyyy-MM-dd")
		.setExclusionStrategies(
				new ExcludeUnionResource(new String[] { "preTradeLot",
						"preTradeDetails","preTrade" })).create();
		String r = gson.toJson(preTrade);
	
		System.out.println("rr:"+r);
		assertTrue(r.length() >10);
		Set<PreTradeDetail> set = preTrade.getPreTradeDetails();
		String detail = "";
		if(set != null){
			detail = gson.toJson(set);
		}else{
			detail = "[]";
		}
		
		r = r.replace("}", ",\"preTradeDetails\": "+detail+"}");
		
		System.out.println("sssss:"+r);
	
		
//		List<PreTradeDetail> list = preTradeDetailService
//				.getAllPreTradeDetailByPerTradeID(preTrade.getTradeId());
//		
//	    String detail = gson.toJson(list);

		assertTrue(detail.length() >10);
	}

       
    }
