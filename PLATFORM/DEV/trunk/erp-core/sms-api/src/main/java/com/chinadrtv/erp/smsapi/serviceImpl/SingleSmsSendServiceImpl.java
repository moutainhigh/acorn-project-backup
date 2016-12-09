/*
 * @(#)SingleSmsSendServiceImpl.java 1.0 2013-2-21上午9:49:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.serviceImpl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.erp.smsapi.dao.SmsSendDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao;
import com.chinadrtv.erp.smsapi.dto.Channel;
import com.chinadrtv.erp.smsapi.dto.Content;
import com.chinadrtv.erp.smsapi.dto.DateScope;
import com.chinadrtv.erp.smsapi.dto.Message;
import com.chinadrtv.erp.smsapi.dto.Response;
import com.chinadrtv.erp.smsapi.dto.SendRequest;
import com.chinadrtv.erp.smsapi.dto.SendSla;
import com.chinadrtv.erp.smsapi.dto.TimeScope;
import com.chinadrtv.erp.smsapi.dto.Variables;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.smsapi.service.SingleSmsSendService;
import com.chinadrtv.erp.smsapi.util.BatchIdUtil;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.FtpUtil;
import com.chinadrtv.erp.smsapi.util.Md5Util;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.chinadrtv.erp.smsapi.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-21 上午9:49:19
 * 
 */
@Service("singleSmsSendService")
public class SingleSmsSendServiceImpl implements SingleSmsSendService {
	private static final Logger logger = LoggerFactory
			.getLogger(SingleSmsSendServiceImpl.class);
	@Autowired
	private SmsSendDetailDao smsSendDetailDao;
	@Autowired
	private SmsSendDao smsSendDao;

	@Autowired
	RestTemplate restTemplate;

	/**
	 * 单条短信发送
	 * 
	 * @param batchId
	 * 
	 * @param allowChangeChannel
	 * 
	 * @param startDateTime
	 * 
	 * @param endDateTime
	 * 
	 * @param timeScope
	 * 
	 * @param priority
	 * 
	 * @param isReply
	 * 
	 * @param realTime
	 * 
	 * @param signiture
	 * 
	 * @param creator
	 * 
	 * @param template
	 * 
	 * @param source
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.SingleSmsSendService#singleSend(java
	 *      .lang.String, java.lang.String, java.util.Date, java.util.Date,
	 *      java.util.List, java.lang.Long, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void singleSend(String allowChangeChannel, String startDateTime,
			String endDateTime, List<Map<String, String>> timeScope,
			Long priority, String isReply, String realTime, String signiture,
			String creator, String template, List paramList, String source,
			String department, String to, String connectId, String timestamp,
			String uuid, String orderType, String templateTheme, String smsName) {
		// TODO Auto-generated method stub

		// 生成MD5码
		String secureHash = uuid + department + to + template
				+ PropertiesUtil.getMd5Key();
		try {
			secureHash = Md5Util.MD5(new String(secureHash
					.getBytes("iso8859-1"), "gb2312"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 生成批次号
		String batchId = BatchIdUtil.getBatchId();
		logger.info("调用单条短信接口:" + batchId);
		// 生成 sla对象
		SendRequest singleSendRequest = newSla(batchId, uuid, timestamp,
				department, to, template, paramList, "1", allowChangeChannel,
				startDateTime, endDateTime, timeScope, priority, isReply,
				realTime, signiture, secureHash);

		// 保存数据库信息
		try {
			SmsSendDetail smsSendDetail = new SmsSendDetail();
			smsSendDetail.setConnectId(connectId);
			smsSendDetail.setContent(replaceParam(template, paramList));
			smsSendDetail.setCreatetime(new Date());
			smsSendDetail.setCreator(creator);
			smsSendDetail.setMobile(to);
			smsSendDetail.setSendtime(new Date());
			smsSendDetail.setSource(source);
			smsSendDetail.setUuid(uuid);
			smsSendDetail.setSmsType("1");
			smsSendDetail.setBatchId(batchId);
			smsSendDetail.setLastModifyDate(new Date());
			smsSendDetail.setCampaignId(0l);
			List<SmsSendDetail> list = new ArrayList<SmsSendDetail>();
			list.add(smsSendDetail);
			SmsSend smsSend = new SmsSend();
			if (!StringUtil.isNullOrBank(smsName)) {
				smsSend.setSmsName(smsName);
			}
			smsSend.setBatchId(batchId);
			smsSend.setCreatetime(new Date());
			smsSend.setCreator(creator);
			smsSend.setDepartment(department);
			if (!StringUtil.isNullOrBank(orderType)) {
				smsSend.setOrderType(orderType);
			}
			if (!StringUtil.isNullOrBank(templateTheme)) {
				smsSend.setTemplateTheme(templateTheme);
			}
			if (endDateTime != null && !endDateTime.equals("")) {
				smsSend.setEndtime(DateTimeUtil.sim3.parse(endDateTime));
			}
			if (startDateTime != null && !startDateTime.equals("")) {
				smsSend.setStarttime(DateTimeUtil.sim3.parse(startDateTime));
			}
			smsSend.setIsreply(isReply);
			smsSend.setPriority(priority);
			smsSend.setRealtime(realTime);
			smsSend.setRecordcount(1L);
			smsSend.setSigniture(signiture);
			smsSend.setSmsContent(template);
			smsSend.setType("1");
			smsSend.setUuid(uuid);
			smsSend.setTomobile(to);
			smsSend.setTimestamps(new Date());
			smsSend.setSource(source);
			// 时间区段记录
			if (timeScope != null && !timeScope.isEmpty()) {
				smsSend.setTimescope(timescope(timeScope));
			}
			HttpHeaders requestHeaders = FtpUtil.createHttpHeader();
			HttpEntity<String> entity = new HttpEntity(singleSendRequest,
					requestHeaders);
			logger.info("调用单条短信接口生成xml:" + batchId + "保存数据库成功");
			// 单条短信发送 rest url
			Response response = restTemplate.postForObject(
					PropertiesUtil.getSingleSend(), entity, Response.class);
			logger.info("调用单条短信接口生成xml:" + batchId + "短信供应商返回状态"
					+ response.getStatus());
			if (response != null && !response.getStatus().equals("0")) {
				// 保存数据库
				smsSend.setErrorCode(response.getErrorCode());
				smsSend.setErrorInfo(response.getErrorMsg());
				smsSend.setSendStatus("2");
				saveDB(smsSend, list);
			} else {
				if (response != null && response.getStatus().equals("0")) {
					smsSend.setErrorCode(response.getErrorCode());
					smsSend.setErrorInfo(response.getErrorMsg());
				}
				smsSend.setSendStatus("6");
				saveDB(smsSend, list);
			}
			logger.info("调用单条短信接口生成xml:" + batchId + "更新短信发送状态");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("单条短信发送异常batchId" + batchId + "=" + e);
			e.printStackTrace();
		}
	}

	public void saveDB(SmsSend smsSend, List<SmsSendDetail> list) {
		smsSendDao.saveSmsSend(smsSend);
		smsSendDetailDao.saveDetailList(list);

	}

	public void updates(Response response) {
		smsSendDao.updateSmsSend(response.getUuid(), response.getStatus(),
				response.getErrorCode(), response.getErrorMsg());

	}

	/***
	 * 替换短信模板参数
	 * 
	 * @Description: TODO
	 * @param template
	 * @param paramList
	 * @return
	 * @return String
	 * @throws
	 */
	public String replaceParam(String template, List paramList) {
		for (int i = 0; i < paramList.size(); i++) {
			template = template.replace("{t" + (i + 1) + "}", paramList.get(i)
					.toString());
		}
		return template;
	}

	/***
	 * 时间段和吞吐量生成字符串保存到数据库中
	 * 
	 * @Description: TODO
	 * @param timeScope
	 * @return
	 * @return String
	 * @throws
	 */
	public String timescope(List<Map<String, String>> timeScope) {
		StringBuffer timeScopeBuffer = new StringBuffer();
		String times = "";
		String iops = "";
		for (int i = 0; i < timeScope.size(); i++) {
			times = timeScope.get(i).get("time");
			iops = timeScope.get(i).get("iops");
			timeScopeBuffer.append(times + "," + iops + "=");
		}
		return timeScopeBuffer.toString().substring(0,
				timeScopeBuffer.toString().lastIndexOf("="));
	}

	public SendRequest newSla(String batchId, String uuid, String timestamp,
			String department, String to, String template, List paramList,
			String type, String allowChangeChannel, String starttime,
			String endtime, List<Map<String, String>> timeList, Long priority,
			String isReply, String realTime, String signiture, String secureHash) {
		SendRequest singleSendRequest = new SendRequest();
		Message message = new Message();
		Content content = new Content();
		DateScope dateScope = new DateScope();
		SendSla sla = new SendSla();
		message.setBatchId(batchId);
		message.setUuid(uuid);
		message.setDepartment(department);
		message.setTo(to);
		message.setSlotId("-1");
		message.setTimestamp(DateTimeUtil.sim3.format(new Date()));
		List<Variables> list = new ArrayList<Variables>();
		if (paramList != null && !paramList.isEmpty()) {
			for (int i = 0; i < paramList.size(); i++) {
				Variables variables = new Variables();
				variables.setName("{t" + (i + 1) + "}");
				variables.setValue(paramList.get(i).toString());
				list.add(variables);
			}
		} else {
			Variables variables = new Variables();
			variables.setName("");
			variables.setValue("");
			list.add(variables);
		}
		content.setVariables(list);
		content.setTemplate(template);
		message.setContent(content);
		sla.setType(type);
		List<Channel> channels = new ArrayList<Channel>();
		sla.setChannels(channels);
		dateScope.setEndTime(endtime);
		dateScope.setStartTime(starttime);
		List<TimeScope> list2 = new ArrayList<TimeScope>();
		if (timeList != null && !timeList.isEmpty()) {
			for (int i = 0; i < timeList.size(); i++) {
				TimeScope timeScope = new TimeScope();
				Map<String, String> map = timeList.get(i);
				timeScope.setTime(map.get("time"));
				timeScope.setIops(map.get("iops"));
				list2.add(timeScope);
			}
		}
		dateScope.setTimeScopes(list2);
		sla.setAllowChangeChannel(allowChangeChannel);
		sla.setDateScope(dateScope);
		if (priority != null) {
			sla.setPriority(priority.toString());
		}
		sla.setIsReply(isReply);
		sla.setRealTime(realTime);
		sla.setSigniture(signiture);
		singleSendRequest.setMessage(message);
		singleSendRequest.setSla(sla);
		singleSendRequest.setSecureHash(secureHash);
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		logger.info("调用单条短信接口生成xml:" + batchId + "=xml:"
				+ xstream.toXML(singleSendRequest));
		return singleSendRequest;
	}

	/*
	 * (非 Javadoc) <p>Title: getByUuid</p> <p>Description: </p>
	 * 
	 * @param uuid
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.service.SingleSmsSendService#getByUuid(java.
	 * lang.String)
	 */
	public SmsSendDetail getByUuid(String uuid) {
		// TODO Auto-generated method stub

		return smsSendDetailDao.getByUuid(uuid);
	}
}
