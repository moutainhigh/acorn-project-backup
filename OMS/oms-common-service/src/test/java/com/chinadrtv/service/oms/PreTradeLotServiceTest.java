/*
 * @(#)PreTradeLotServiceTest.java 1.0 2013年11月4日上午11:10:26
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.oms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.model.oms.PreTradeCard;
import com.chinadrtv.model.oms.PreTradeDetail;
import com.chinadrtv.model.oms.dto.PreTradeDto;
import com.chinadrtv.model.oms.dto.PreTradeLotDto;
import com.chinadrtv.service.oms.PreTradeLotService;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2013年11月4日 上午11:10:26 
 * 
 */
public class PreTradeLotServiceTest extends ServiceTest {

	@Autowired
	private PreTradeLotService preTradeLotService;
	
	@Test
//	@Rollback(false)
	public void testInsert() {
		List<PreTradeDetail> preTradeDetailList = new ArrayList<PreTradeDetail>();
		List<PreTradeCard> preTradeCardList = new ArrayList<PreTradeCard>();
		List<PreTradeDto> preTradeDtoList = new ArrayList<PreTradeDto>();
		
		Random random = new Random(100000);
		
		for(int i=0; i<2; i++) {
			PreTradeDetail ptd = new PreTradeDetail();
			ptd.setSkuId(1013408892L + i);
			ptd.setOutSkuId("10607252000" + i);
			ptd.setQty(1);
			ptd.setSkuName("紫环 多功能颈椎按摩器");
			ptd.setPrice(32.3);
			ptd.setUpPrice(32.3);
			ptd.setErrMsg("batch insert test"+i);
			ptd.setIsValid(true);
			
			preTradeDetailList.add(ptd);
			
			PreTradeCard ptc = new PreTradeCard();
			ptc.setBankCode("21212" + i);
			ptc.setAuthCode("21212" + i);
			ptc.setIdCardNumber("412727198910105020");
			ptc.setCreditCardNumber("12321323");
			ptc.setCreditCardExpire("0909");
			ptc.setCreditCardCycles(2L);
			ptc.setCreateUser("A" + i);
			
			preTradeCardList.add(ptc);
		}
		
		for(int i=0; i<2; i++) {
			PreTradeDto ptDto = new PreTradeDto();
			
			ptDto.getPreTradeDetails().addAll(preTradeDetailList);
			ptDto.getPreTradeCards().addAll(preTradeCardList);
			
			ptDto.setCrdt(new Date());
			
			ptDto.setOpsTradeId(random.nextInt() + i + "");
			ptDto.setAlipayTradeId("3232" + i);
			ptDto.setBuyerAlipayId("3232" + i);
			ptDto.setCustomerId("橡果国际官方旗舰店" + i);
			ptDto.setTradeType("127");
			ptDto.setTradeFrom("TAOBAO");
			ptDto.setTmsCode("110" + i);
			ptDto.setOutCrdt(new Date());
			ptDto.setSourceId(9L);
			ptDto.setIsValid(true);
			ptDto.setIsApproval(true);
			ptDto.setPaytype("3");
			ptDto.setFeedbackStatus("0");
			
			preTradeDtoList.add(ptDto);
		}
		
		PreTradeLotDto ptlDto = new PreTradeLotDto();
		ptlDto.setStatus(0L);
		ptlDto.setLotDsc("3333");
		ptlDto.setCrusr("A");
		ptlDto.setValidCount(3L);
		ptlDto.setErrCount(2L);
		ptlDto.setSourceId(2L);
		
		ptlDto.getPreTrades().addAll(preTradeDtoList);
		
		preTradeLotService.insert(ptlDto);
	}
}
