/*
 * @(#)CardServiceImpl.java 1.0 2013-5-8上午10:11:12
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.compass.core.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.Cardtype;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.uc.dao.CardDao;
import com.chinadrtv.erp.uc.dao.CardtypeDao;
import com.chinadrtv.erp.uc.dto.CardDto;
import com.chinadrtv.erp.uc.service.CardService;
import com.chinadrtv.erp.uc.util.IdcardUtils;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-8 上午10:11:12
 * 
 */
@Service("cardService")
public class CardServiceImpl implements CardService {

	@Autowired
	@Qualifier("cardDao")
	private CardDao cardDao;

    @Autowired
    private CardtypeDao cardtypeDao;

	private static final Pattern VALID_NUMBER_PATTERN = Pattern
			.compile("^\\d{16,19}$");

	@Override
	public Card getCard(Long cardId) {
		return cardDao.get(cardId);
	}

	@Override
	public Card getCard(String contactId, String cardType) {
		return cardDao.getCardByContactId(contactId, cardType);
	}

	@Override
	public Card addCard(String contactId, String cardNumber, String type,
			String validDate, String extraCode) throws ServiceException {
		return addCard(contactId, cardNumber, type, validDate, extraCode, null);
	}
	
	@Override
	public Card addCard(String contactId, String cardNumber, String type,
			String validDate, String extraCode, Integer state) throws ServiceException {
		Card card = new Card();
		setCardProperties(card, contactId, cardNumber,
				type, validDate, extraCode);
		card.setState(state);
		validateIdCardUnique(card);
		validate(card);
		return cardDao.save(card);
	}

	@Override
	public boolean updateCard(Long cardId, String cardNumber, String type,
			String validDate, String extraCode) throws ServiceException {
		Card card = cardDao.get(cardId);
		if (card == null) {
			return false;
		}
		setCardProperties(card, card.getContactId(), cardNumber,
				type, validDate, extraCode);
		validate(card);
		
		cardDao.saveOrUpdate(card);
		return true;
	}

	@Override
	public boolean updateCardState(Long cardId, Integer state) {
		Card card = cardDao.get(cardId);
		if (card == null) {
			return false;
		}
		card.setState(state);
		cardDao.saveOrUpdate(card);
		return true;
	}

	@Override
	public boolean updateExtraCode(Long cardId, String extraCode) {
		Card card = cardDao.get(cardId);
		if (card == null) {
			return false;
		}
		card.setExtraCode(extraCode);
		cardDao.saveOrUpdate(card);
		return true;
	}

	@Override
	public Card merge(Card card) throws ServiceException {
		validate(card);
		return cardDao.merge(card);
	}

	@Override
	public List<CardDto> getCards(String contactId) {
		List<Card> cards = cardDao.getCardsByContactId(contactId);
        List<CardDto> cardDtos = new ArrayList<CardDto>(cards.size());
        for (Card card : cards) {
            CardDto cardDto = new CardDto(card);
            Cardtype ctype = cardtypeDao.get(card.getType());
            cardDto.setCardtypeName(ctype.getCardname());
            cardDto.setBankName(ctype.getBankName());
            cardDtos.add(cardDto);
        }
        return cardDtos;
    }

	@Override
	public void removeCard(Long cardId) {
		cardDao.remove(cardId);
	}

	@Override
    public void saveCards(List<Card> cards) {
        for (Card card : cards) {
            cardDao.saveOrUpdate(card);
        }
    }
    
	private void validate(Card card) throws ServiceException {
		if (StringUtils.isEmpty(card.getContactId())) {
			throw new ServiceException("联系人不能为空");
		}
		if (StringUtils.isEmpty(card.getType())) {
			throw new ServiceException("卡类型不能为空");
		}
		if (StringUtils.isEmpty(card.getCardNumber())) {
			throw new ServiceException("卡号不能为空");
		}
		if (!isBankCard(card.getType())) {
			if (StringUtils.equals(card.getType(), "001")) {
				if (card.getCardNumber().length() != 15
						&& card.getCardNumber().length() != 18) {
					throw new ServiceException("身份证卡号必须为15位或者18位");
				}
				if (!IdcardUtils.isIdcard(card.getCardNumber())) {
					throw new ServiceException("不是有效的身份证号码");
				}
			}
		} else {
			if (card.getCardNumber().length() < 16
					|| card.getCardNumber().length() > 19
					|| !isCardNumberValid(card.getCardNumber())) {
				throw new ServiceException("银行卡号必须为16位到19位的数字");
			}
			if (isCardDuplicate(card)) {
				throw new ServiceException("不能添加重复的卡");
			}
		}
	}
	
	private void validateIdCardUnique(Card card) throws ServiceException {
		if (StringUtils.equals(card.getType(), "001") && !CollectionUtils.isEmpty(
				cardDao.getCardsByContactId(card.getContactId(), "001"))) {
			throw new ServiceException("当前用户已存在身份证，不能再次添加");
		}
	}

    private void setCardProperties(Card card, String contactId,
			String cardNumber, String type, String validDate, String extraCode) {
		if (StringUtils.isNotEmpty(contactId)) {
			card.setContactId(contactId);
		}
		if (StringUtils.isNotEmpty(cardNumber)) {
			card.setCardNumber(cardNumber);
		}
		if (StringUtils.isNotEmpty(type)) {
			card.setType(type);
		}
		if (StringUtils.isNotEmpty(validDate)) {
			card.setValidDate(validDate);
		}
		if (StringUtils.isNotEmpty(extraCode)) {
			card.setExtraCode(extraCode);
		}
	}

	// check the card number valid
	private boolean isCardNumberValid(String cardNumber) {
		if (StringUtils.isNotEmpty(cardNumber)) {
			Matcher matcher = VALID_NUMBER_PATTERN.matcher(cardNumber);
			return matcher.find();
		} else {
			return false;
		}
	}

	@Override
	public List<Card> getCardsByContactIdExcludeCardTypes(String contactId,
			String... type) {
		return cardDao.getCardsByContactIdExcludeCardTypes(contactId, type);
	}

	@Override
    public List<Card> getCardsByContactIdIncludeCardTypes(String contactId, String... type){
        return cardDao.getCardsByContactIdIncludeCardTypes(contactId, type);
    }

	@Override
	public CardDto getCardByCardNumber(String cardNum) {
		Card card = cardDao.getCardByCardNumber(cardNum);
		if (card == null) return null;
		CardDto dto = new CardDto(card);
		dto.setCardtypeName(cardtypeDao.get(card.getType()).getCardname());
		return dto;
	}

	@Override
	public List<Card> getIdentityCard(String contactId) {
		return cardDao.getCardsByContactId(contactId, "001");
	}

	@Override
	public List<Card> getOtherIdentityCard(String contactId) {
		return cardDao.getCardsByContactIdIncludeCardTypes(contactId, "002", "011");
	}
	
	@Override
	public boolean isCardDuplicate(Card card) {
		return cardDao.getCardByCondition(card) != null;
	}
	
	private boolean isBankCard(String cardType) {
		return !StringUtils.equals(cardType, "001")
				&& !StringUtils.equals(cardType, "002")
				&& !StringUtils.equals(cardType, "011")
				&& !StringUtils.equals(cardType, "014");
	}


    public List<Card> getCards(List<Long> cardIdList)
    {
        return cardDao.getCards(cardIdList);
    }

    public List<Card> getAllIdentityCardByContact(String contactId)
    {
         return this.getCardsByContactIdIncludeCardTypes(contactId,"001","002","011","014");
    }
}
