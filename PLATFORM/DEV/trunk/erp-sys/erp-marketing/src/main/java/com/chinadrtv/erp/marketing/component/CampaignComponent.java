/*
 * @(#)CampaignComponent.java 1.0 2013-4-26下午2:14:50
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.component;

import java.util.Date;
import java.util.Map;

import com.chinadrtv.erp.marketing.core.dto.SmssnedDto;
import com.chinadrtv.erp.model.marketing.Campaign;
import com.chinadrtv.erp.smsapi.model.SmsSend;

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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-4-26 下午2:14:50
 * 
 */
public interface CampaignComponent {

	public SmsSend saveByBatchId(String userId, SmssnedDto smssnedDto,
			String department) throws Exception;

	public Map<String, Object> newSmsSend(SmsSend smsSend, Campaign campaign,
			Date now);

	public Map<String, Object> newCouponCrs(Campaign campaign);

	public Map<String, Object> newSmsSendByCoupon(SmsSend smsSend,
			Campaign campaign, Date now);
}
