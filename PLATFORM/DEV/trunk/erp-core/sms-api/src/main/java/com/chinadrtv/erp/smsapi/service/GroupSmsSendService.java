/*
 * @(#)GroupSmsSendService.java 1.0 2013-2-18下午1:12:28
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinadrtv.erp.smsapi.dto.CampaignMonitorDto;
import com.chinadrtv.erp.smsapi.model.SmsBatch;
import com.chinadrtv.erp.smsapi.model.SmsSend;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-18 下午1:12:28
 * 
 */
public interface GroupSmsSendService {
	/***
	 * 外部调用批量发送短信
	 * 
	 * @Description: TODO
	 * @param department部门号
	 * @param templateMap参数
	 * @param startDateTime开始时间
	 * @param endDateTime结束时间
	 * @param timeScope分阶段发送
	 * @param priority优先级
	 * @param isReply是否要求上行
	 * @param realTime是否及时反馈客户短信内容
	 * @param signiture短信签名
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public boolean groupSend(String batchId, String allowChangeChannel,
			Date startDateTime, Date endDateTime,
			List<Map<String, String>> timeScope, Long priority, String isReply,
			String realTime, String signiture, String creator, String template,
			String source, Map channel, String groupCode, String groupName,
			String smsName);

	/**
	 * 批量插入批次表
	 * 
	 * @Description: TODO
	 * @param bathList
	 * @return
	 * @return boolean
	 * @throws
	 */
	public boolean insertBatch(List<SmsBatch> bathList);

	/**
	 * 批量短信发送停止
	 * 
	 * @Description: TODO
	 * @param batachid
	 * @param deparment
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public SmsSend smsStop(String batachid, String deparment);

	/**
	 * 
	 * @Description: 短信发送
	 * @param batchid
	 * @param smsSend
	 * @param filename
	 * @param ftpnames
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Map<String, Object> batchGroupSend(String batchid, SmsSend smsSend,
			String filename, String ftpnames,
			List<Map<String, String>> timeScope);

	/***
	 * 
	 * @Description: 文件上传到ftp
	 * @param ftpurl
	 * @param ftpport
	 * @param ftpname
	 * @param ftppass
	 * @param sendftp
	 * @param filename
	 * @param ftpnames
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean uploadCsvToFtp(String filename, String ftpnames,
			SmsSend smsSend);

	/**
	 * 
	 * @Description: 批次发送
	 * @param batchId
	 * @param filename
	 * @param template
	 * @param source
	 * @param ftpnames
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean batchCsv(String batchId, String filename, String template,
			String source, String ftpnames, List<SmsBatch> smsBatchList,
			Integer count, Integer begin, Integer end, String campaignId);

	/**
	 * 
	 * @Description: 创建文件夹
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean newFileDirs();

	/**
	 * 
	 * @Description: 设置每分钟平均发送量
	 * @param deparment
	 * @param campaignId
	 * @param type
	 * @param minuCount
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean changeSendSpeed(CampaignMonitorDto campaignMonitor);

	/**
	 * 通知大汉三通接受消息
	 * 
	 * @Description: TODO
	 * @param batchId
	 * @param maps
	 * @return
	 * @return Boolean
	 * @throws
	 */

	public Boolean sendXml(String batchId, Map<String, Object> maps);

}
