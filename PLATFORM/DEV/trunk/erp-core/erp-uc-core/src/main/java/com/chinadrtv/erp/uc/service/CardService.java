/*
 * @(#)CardService.java 1.0 2013-5-8上午10:10:49
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import java.util.List;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.model.agent.Card;
import com.chinadrtv.erp.uc.dto.CardDto;


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
 * @author dengqianyong@chinadrtv.com
 * @version 1.0
 * @since 2013-5-8 上午10:10:49 
 * 
 */
public interface CardService {
	
	/**
	 * 得到卡
	 * 
	 * @param cardId
	 * @return
	 */
	Card getCard(Long cardId);
	
	/**
	 * 得到卡
	 * 
	 * @param contactId
	 * @param cardType
	 * @return
	 */
	Card getCard(String contactId, String cardType);
	
	/**
	 * 得到身份证
	 * 
	 * @param cardId
	 * @return
	 */
	List<Card> getIdentityCard(String contactId);
	
	/**
	 * 得到其他身份卡
	 * 
	 * @param cardId
	 * @return
	 */
	List<Card> getOtherIdentityCard(String contactId);
	
	/**
	 * <p>新增客户卡信息</p>
	 * 
	 * @param contactId 客户编号
	 * @param cardNumber 卡号
	 * @param type 客户证件类型
	 * @param validDate 信用卡有效期
	 * @param extraCode 附加码
	 * @return 客户卡对象
	 */
	Card addCard(String contactId, String cardNumber, String type,
			String validDate, String extraCode) throws ServiceException;
	
	/**
	 * <p>新增客户卡信息</p>
	 * 
	 * @param contactId 客户编号
	 * @param cardNumber 卡号
	 * @param type 客户证件类型
	 * @param validDate 信用卡有效期
	 * @param extraCode 附加码
	 * @param state 状态
	 * @return 客户卡对象
	 */
	Card addCard(String contactId, String cardNumber, String type,
			String validDate, String extraCode, Integer state) throws ServiceException;
	
	/**
	 * <p>更新客户卡信息</p>
	 * 
	 * @param cardId 卡ID
	 * @param cardNumber 卡号
	 * @param type 客户证件类型
	 * @param validDate 信用卡有效期
	 * @param extraCode 附加码
	 * @return 
	 */
	boolean updateCard(Long cardId, String cardNumber, String type,
			String validDate, String extraCode) throws ServiceException;
	
	/**
	 * 更新卡状态
	 * 
	 * @param cardId
	 * @param state
	 * @return
	 */
	boolean updateCardState(Long cardId, Integer state);
	
	/**
	 * 更新附加码
	 * 
	 * @param cardId
	 * @param extraCode
	 * @return
	 */
	boolean updateExtraCode(Long cardId, String extraCode);
	
	/**
	 * 
	 * @param card
	 */
	Card merge(Card card) throws ServiceException;
	
	/**
	 * <p>查询客户卡信息</p>
	 * 
	 * @param contactId 客户编号
	 * @return
	 */
	List<CardDto> getCards(String contactId);
	
	/**
	 * <p>删除客户卡信息</p>
	 *
	 * @param cardId 卡ID
	 */
	void removeCard(Long cardId);

    /**
     * <p>保存客户卡列表信息</p>
     *
     * @param cards
     */
    void saveCards(List<Card> cards);
    
    /**
	 * 通过客户编号查询客户卡信息，并且排除卡类型
	 * 
	 * @param contactId
	 * @param type
	 * @return
	 */
	List<Card> getCardsByContactIdExcludeCardTypes(String contactId, String... type);
    /**
     * 获取制定类型的用户卡信息
     * @param contactId
     * @param type
     * @return
     */
    List<Card> getCardsByContactIdIncludeCardTypes(String contactId, String... type);
    
    /**
     * 通过卡号找到卡记录
     * 
     * @param cardNum
     * @return
     */
    CardDto getCardByCardNumber(String cardNum);
    
    /**
     * 验证卡是否重复
     * 
     * @param card
     * @return
     */
    boolean isCardDuplicate(Card card);

    List<Card> getCards(List<Long> cardIdList);


    List<Card> getAllIdentityCardByContact(String contactId);
}
