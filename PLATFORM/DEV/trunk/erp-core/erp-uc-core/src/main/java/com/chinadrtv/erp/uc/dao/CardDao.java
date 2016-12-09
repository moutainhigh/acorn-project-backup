/*
 * @(#)CardDao.java 1.0 2013-5-7下午3:43:23
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.dao;

import java.util.List;

import com.chinadrtv.erp.core.dao.GenericDao;
import com.chinadrtv.erp.model.agent.Card;

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
 * @since 2013-5-7 下午3:43:23 
 * 
 */
public interface CardDao extends GenericDao<Card, Long> {
	
	/**
	 * <p>判断信用卡卡号是否已经存在</p>
	 *
	 * @param cardNumber 信用卡卡号
	 * @return
	 */
	boolean isCardNumberExists(Card card);
	
	/**
	 * <p>通过客户编号查询客户卡信息</p>
	 *
	 * @param contactId 客户编号
	 * @return
	 */
	List<Card> getCardsByContactId(String contactId);
	
	/**
	 * <p>通过客户编号查询客户卡信息</p>
	 *
	 * @param contactId 客户编号
	 * @param type 卡类型
	 * @return
	 */
	Card getCardByContactId(String contactId, String type);
	
	/**
	 * <p>通过客户编号查询客户卡信息</p>
	 *
	 * @param contactId 客户编号
	 * @param type 卡类型
	 * @return
	 */
	List<Card> getCardsByContactId(final String contactId, final String type);
	
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
	Card getCardByCardNumber(String cardNum);

    List<Card> getCards(List<Long> cardIdList);
    
    Card getCardByCondition(Card condition);
}
