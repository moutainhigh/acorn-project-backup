/*
 * @(#)SingleSmsSendService.java 1.0 2013-2-21上午9:48:54
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.service;

import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-21 上午9:48:54
 * 
 */
public interface SingleSmsSendService {

	public void singleSend(String allowChangeChannel, String startDateTime,
			String endDateTime, List<Map<String, String>> timeScope,
			Long priority, String isReply, String realTime, String signiture,
			String creator, String template, List paramList, String source,
			String department, String to, String connectId, String timestamp,
			String uuid, String orderType, String templateTheme, String smsName);

	public void saveDB(SmsSend smsSend, List<SmsSendDetail> list);

	public SmsSendDetail getByUuid(String uuid);

}
