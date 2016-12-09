/*
 * @(#)CampaignExecuteService.java 1.0 2013-4-26上午11:23:59
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service;

import java.util.Map;

import com.chinadrtv.erp.model.marketing.Campaign;

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
 * @since 2013-4-26 上午11:23:59
 * 
 */
public interface CampaignExecuteService {

	public void execute(Campaign campaign) throws Exception;

	public void executeCoupon(Map<String, Object> map) throws Exception;
}
