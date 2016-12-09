/*
 * @(#)SingleSmsSendStatusService.java 1.0 2013-2-22上午9:53:01
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.service;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-22 上午9:53:01
 * 
 */
public interface SmsSendStatusService {

	public String singleSendStatus(String xml);

	public String groupSendStatus(String xml);

	public String groupSendStatusByFile(String file, String batchId,
			String type, String path);

	public Boolean oracleSqlLoadByFile(String path, String type,
			String batchId, String file);

	public String groupSendStatusByZip(String xml);

}
