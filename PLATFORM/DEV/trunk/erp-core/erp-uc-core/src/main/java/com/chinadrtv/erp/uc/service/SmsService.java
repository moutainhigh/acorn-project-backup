/*
 * @(#)SmsService.java 1.0 2013-7-2下午1:19:12
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.uc.service;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.marketing.core.dto.SmsSendDto;
import com.chinadrtv.erp.model.agent.Order;
import com.chinadrtv.erp.user.model.AgentUser;

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
 * @since 2013-7-2 下午1:19:12 
 * 
 */
public interface SmsService {
	
	/* 短信 Source */
	public static final String SMS_SOURCE = "SALES";

	/**
	 * <p>发送短信</p>
	 * @param smsSendDto
	 */
	void sendSms(SmsSendDto smsSendDto, String orderId) throws ServiceException;

    /**
     * 创建订单发送短信
     *
     * @param user
     * @param order
     * @throws ServiceException
     */
    void sendOrderMessage(AgentUser user, Order order) throws ServiceException;
}
