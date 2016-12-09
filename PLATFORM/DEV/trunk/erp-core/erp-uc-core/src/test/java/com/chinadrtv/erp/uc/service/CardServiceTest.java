package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.uc.dto.CardDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.test.SpringTest;

/**
 * 
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	CardService Test
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-9 下午3:41:01 
 *
 */
public class CardServiceTest extends SpringTest {

	@Autowired
	@Qualifier("cardService")
	private CardService cardService;
	
	@Test
	@Rollback(true)
	public void testAddCard() throws ServiceException {
		
		Card card = cardService.addCard("999999999", "1234432156788776", "006", "2016-12", "999");
		Assert.assertTrue("新增信用卡成功", card != null && card.getCardId() != null);
		
		card = cardService.addCard("999999999", "1234432156788776", "006", "2016-12", "999");
		Assert.assertTrue("重复的信用卡", card == null);
	}
	
	@Test
	@Rollback(true)
	public void testUpdateCard() throws ServiceException {
		Card card = cardService.addCard("999999999", "1234432156788776", "006", "2016-12", "999");
		
		boolean isUpdated = cardService.updateCard(card.getCardId(), "1234432156788776", "006", "2017-12", "999");
		Assert.assertTrue("修改信用卡不成功", isUpdated);
	}
	
	@Test
	@Rollback(true)
	public void testRemoveCard() throws ServiceException {
		Card card = cardService.addCard("999999999", "1234432156788776", "006", "2016-12", "999");
		
		cardService.removeCard(card.getCardId());
		Assert.assertTrue("删除信用卡不成功", true);
	}
	
	@Test
	public void testGetCards() {
		List<CardDto> cards = cardService.getCards("953190267");
		Assert.assertEquals("查询客户卡信息不成功", 3, cards.size());
	}
}
