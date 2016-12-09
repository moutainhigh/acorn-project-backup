/*
 * @(#)GroupSmsSendServiceImpl.java 1.0 2013-2-18下午1:13:19
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.erp.smsapi.constant.DataGridModel;
import com.chinadrtv.erp.smsapi.dao.CampaignMonitorDao;
import com.chinadrtv.erp.smsapi.dao.SmsBatchDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao;
import com.chinadrtv.erp.smsapi.dto.CampaignMonitorDto;
import com.chinadrtv.erp.smsapi.dto.Channel;
import com.chinadrtv.erp.smsapi.dto.DateScope;
import com.chinadrtv.erp.smsapi.dto.Response;
import com.chinadrtv.erp.smsapi.dto.SendRequest;
import com.chinadrtv.erp.smsapi.dto.SendSla;
import com.chinadrtv.erp.smsapi.dto.TimeScope;
import com.chinadrtv.erp.smsapi.model.CampaignMonitor;
import com.chinadrtv.erp.smsapi.model.SmsBatch;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.smsapi.service.GroupSmsSendService;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.FileUtil;
import com.chinadrtv.erp.smsapi.util.FtpUtil;
import com.chinadrtv.erp.smsapi.util.Md5Util;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.chinadrtv.erp.smsapi.util.StringUtil;
import com.chinadrtv.erp.smsapi.util.ZipCompressor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-18 下午1:13:19
 * 
 */
@Service("groupSmsSendService")
public class GroupSmsSendServiceImpl implements GroupSmsSendService {
	private static final Logger logger = LoggerFactory
			.getLogger(GroupSmsSendServiceImpl.class);
	@Autowired
	private SmsBatchDao smsBatchDao;
	@Autowired
	private SmsSendDao smsSendDao;
	@Autowired
	private SmsSendDetailDao smsSendDetailDao;
	@Autowired
	private CampaignMonitorDao campaignMonitorDao;

	@Autowired
	private RestTemplate restTemplate;

	public String filepath = "";

	// @Autowired
	// private RestTemplate restTemplate;

	/**
	 * 外部调用批量发送短信
	 * 
	 * @param department
	 * 
	 * @param templateMap
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
	 * @see com.chinadrtv.erp.smsapi.service.GroupSmsSendService#groupSend(java.lang
	 *      .String, java.util.Map, java.lang.String, java.lang.String,
	 *      java.lang.String, java.util.List, java.lang.Long, java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public boolean groupSend(String batchId, String allowChangeChannel,
			Date startDateTime, Date endDateTime,
			List<Map<String, String>> timeScope, Long priority, String isReply,
			String realTime, String signiture, String creator, String template,
			String source, Map map, String groupCode, String groupName,
			String smsName) {
		// TODO Auto-generated method stub
		// 获得批量发送短信数据
		boolean flag = false;
		List<SmsBatch> smsBatchlist = smsBatchDao.getBatchList(batchId);
		if (smsBatchlist != null && !smsBatchlist.isEmpty()) {
			String uuid = smsBatchlist.get(0).getBatchId();
			// 文件名
			String fileString = DateTimeUtil.sim.format(new Date()) + batchId
					+ ".csv";
			// 生成MD5
			String secureHash = uuid + smsBatchlist.get(0).getDepartment()
					+ fileString + template + PropertiesUtil.getMd5Key();
			try {
				secureHash = Md5Util.MD5(new String(secureHash
						.getBytes("iso8859-1"), "gb2312"));
				logger.info("step -1调用批量短信接口。batchId:" + batchId
						+ "fileString:" + fileString + "secureHash:"
						+ secureHash);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// 把数据保存数据库
			try {
				// 生成sla xml
				SendRequest sendRequest = newSlaXml(batchId, uuid,
						DateTimeUtil.sim3.format(new Date()),
						smsBatchlist.get(0).getDepartment(), fileString,
						template, "2", allowChangeChannel,
						DateTimeUtil.sim3.format(startDateTime),
						DateTimeUtil.sim3.format(endDateTime), timeScope,
						priority, isReply, realTime, signiture, secureHash, ""
								+ smsBatchlist.size(), map, "");
				logger.info("step -2调用批量短信接口生成xml。sendRequest:" + batchId
						+ "fileString:" + fileString + "secureHash:"
						+ secureHash);
				// 生成csv文件 存放到ftp服务器
				Boolean ftpFlag = newCsvMoveToFtp(smsBatchlist, fileString,
						template, source);
				logger.info("step -3调用批量短信接口上传ftp服务器。  batchId: " + batchId
						+ "ftpFlag:" + ftpFlag);
				if (ftpFlag == true) {
					// 发送记录
					SmsSend smsSend = smsSendDao.getByBatchid(batchId);
					smsSend.setDepartment(smsBatchlist.get(0).getDepartment());
					smsSend.setRecordcount(Long.valueOf(smsBatchlist.size()));
					smsSend.setFtpStatus("1");
					smsSend.setSendFilename(fileString);
					smsSend.setType("2");
					// 设置时间段 和吞吐量
					// smsSend.setTimescope(timescope(timeScope));
					// 批量短信发送rest
					HttpHeaders requestHeaders = FtpUtil.createHttpHeader();
					HttpEntity<String> entity = new HttpEntity(sendRequest,
							requestHeaders);
					logger.info("step -4调用供应商短信接口。  batchId: " + batchId + "发送");
					Response response = restTemplate.postForObject(
							PropertiesUtil.getGroupSend(), entity,
							Response.class);
					if (response != null && !response.getStatus().equals("0")) {
						// 保存短信详情到数据库中
						logger.info("批量短信发送接口。  batchId: " + batchId + "创建成功");
						smsSend.setSendStatus("2");
						smsSendDao.saveSmsSend(smsSend);
						logger.info("批量短信发送接口。  batchId: " + batchId
								+ "smssend保存成功");
						// 删除批量发送短信的数据
						smsBatchDao.deleteAllByBatchId(batchId);
						logger.info("step -5批量短信发送接口。  batchId: " + batchId
								+ "删除批量发送短信的数据");
						flag = true;
					} else {
						smsSend.setSendStatus("0");
						smsSendDao.saveSmsSend(smsSend);
						flag = false;
						logger.info("step -6批量短信发送接口。  batchId: " + batchId
								+ "创建失败 errorCode" + response.getErrorCode()
								+ "errormsg" + response.getErrorMsg()
								+ "status" + response.getStatus());
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("批量短信发送接口异常batchId:" + batchId + "=" + e);
			}
		}

		return flag;
	}

	/**
	 * 
	 * @Description: 通知大汉三通发送短信
	 * @param sendRequest
	 * @param smsSend
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean sendXml(String batchId, Map<String, Object> maps) {
		Boolean flag = false;
		SmsSend smsSend = (SmsSend) maps.get("smsSend");
		// 生成sla xml
		Map map = new HashMap();
		try {
			SendRequest sendRequest = newSlaXml(batchId, batchId,
					DateTimeUtil.sim3.format(new Date()),
					smsSend.getDepartment(), maps.get("filename").toString(),
					smsSend.getSmsContent(), "2", smsSend.getAllowChannel(),
					DateTimeUtil.sim3.format(smsSend.getStarttime()),
					DateTimeUtil.sim3.format(smsSend.getEndtime()),
					(List<Map<String, String>>) maps.get("timeScope"),
					smsSend.getPriority(), smsSend.getIsreply(),
					smsSend.getRealtime(), "", "", ""// 更改短信标签为空
							+ maps.get("total").toString(), map,
					smsSend.getCampaignId());
			HttpHeaders requestHeaders = FtpUtil.createHttpHeader();
			HttpEntity<String> entity = new HttpEntity(sendRequest,
					requestHeaders);
			logger.info("step -5调用供应商短信接口。  batchId: " + batchId + "发送");
			// 删除批量发送短信的数据
			smsBatchDao.deleteAllByBatchId(batchId);
			Response response = restTemplate.postForObject(
					PropertiesUtil.getGroupSend(), entity, Response.class);
			if (response != null && !response.getStatus().equals("0")) {
				// 保存短信详情到数据库中
				logger.info("批量短信发送接口。  batchId: " + batchId + "创建成功");
				smsSend.setErrorCode(response.getErrorCode());
				smsSend.setErrorInfo(response.getErrorMsg());
				smsSend.setSendStatus("2");
				smsSendDao.saveSmsSend(smsSend);

			} else {
				smsSend.setSendStatus("0");
				smsSend.setErrorCode(response.getErrorCode());
				smsSend.setErrorInfo(response.getErrorMsg());
				smsSendDao.saveSmsSend(smsSend);
				logger.info("step -6批量短信发送接口。  batchId: " + batchId
						+ "创建失败 errorCode" + response.getErrorCode()
						+ "errormsg" + response.getErrorMsg() + "status"
						+ response.getStatus());
			}
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("发送大汉三通消息失败" + batchId);
		}
		return flag;
	}

	/**
	 * 
	 * 
	 * @Description: 分页合并发送
	 * @param batchId
	 * @param allowChangeChannel
	 * @param startDateTime
	 * @param endDateTime
	 * @param timeScope
	 * @param priority
	 * @param isReply
	 * @param realTime
	 * @param signiture
	 * @param creator
	 * @param template
	 * @param source
	 * @param map
	 * @param groupCode
	 * @param groupName
	 * @param smsName
	 * @param filepath
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Map<String, Object> batchGroupSend(String batchId, SmsSend smsSend,
			String filename, String ftpnames,
			List<Map<String, String>> timeScope) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 获得批量发送短信数据
		// 总数
		Integer total = smsBatchDao.getBatchCount(batchId);
		// 保存campaignMonitor
		CampaignMonitor campaignMonitor = campaignMonitorDao
				.getByCampaignId(Long.valueOf(smsSend.getCampaignId()));
		Long smscounts = campaignMonitor.getSmsCount();
		if (smscounts == null) {
			smscounts = 0l;
		}
		smscounts = smscounts + Long.valueOf(total);
		campaignMonitor.setSmsCount(smscounts);
		campaignMonitor.setSmsContent(smsSend.getSmsContent());
		campaignMonitorDao.saveOrUpdate(campaignMonitor);
		// 分页查询的数量
		Integer temps = Integer.valueOf(PropertiesUtil.getTemps());
		Integer begin = 0;
		Integer end = 0;
		// 执行次数
		Integer x = total / temps;
		// 余数
		Integer y = total % temps;
		if (y > 0) {
			x = x + 1;
		}
		Map map = new HashMap();
		// 分页查询出的数据
		List<SmsBatch> smsBatchlist = new ArrayList<SmsBatch>();
		DataGridModel dataGridModel = new DataGridModel();
		dataGridModel.setRows(temps);
		dataGridModel.setCount(total);
		logger.info("step -1调用批量短信接口。batchId:" + batchId + "fileString:"
				+ ftpnames + "");
		for (int i = 0; i < x; i++) {
			// 分页查询出的数据
			dataGridModel.setPage(i + 1);
			smsBatchlist = smsBatchDao.getBatchPage(dataGridModel, batchId);
			// 生成csv文件保存sendDetail
			batchCsv(batchId, filename, smsSend.getSmsContent(),
					smsSend.getSource(), ftpnames, smsBatchlist, total, begin,
					end, smsSend.getCampaignId());
			begin = end;
			smsBatchlist = null;
		}
		ZipCompressor zc = new ZipCompressor(ftpnames.replace("csv", "zip"));
		zc.compress(ftpnames);
		filename = filename.replace("csv", "zip");
		logger.info("step -2生成压缩文件。batchId:" + batchId + "fileString:"
				+ ftpnames.replace("csv", "zip") + "");
		// 把数据保存数据库
		try {
			Boolean ftpFlag = true;
			filepath = ftpnames;
			ftpFlag = uploadCsvToFtp(filename, ftpnames.replace("csv", "zip"),
					smsSend);
			logger.info("step -3调用批量短信接口上传ftp服务器。  batchId: " + batchId
					+ "ftpFlag:" + ftpFlag);
			if (ftpFlag == true) {
				new java.util.Timer().schedule(new TimerTask() {
					public void run() {
						// 延迟删除原文件
						FileUtil.deleteFile(filepath);
						// 如果只要这个延迟一次，用cancel方法取消掉．
						this.cancel();
					}
				}, 30000);
				// 删除zip文件
				FileUtil.deleteFile(ftpnames.replace("csv", "zip"));
				// 发送记录
				smsSend.setRecordcount(Long.valueOf(total));
				smsSend.setReceiveCount(0l);
				smsSend.setFtpStatus("1");
				smsSend.setSendFilename(filename);
				smsSend.setType("2");
				// 保存返回结果
				result.put("timeScope", timeScope);
				result.put("filename", filename);
				result.put("total", "" + total);
				result.put("map", map);
				result.put("smsSend", smsSend);
				logger.info("step -4调用供应商短信接口。  batchId: " + batchId + "发送");
				// 删除批量发送短信的数据
				smsBatchDao.deleteAllByBatchId(batchId);
			}
		} catch (Exception e) {
			logger.error("批量短信发送接口异常batchId:" + batchId + "=" + e);
		}
		return result;
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
		if (timeScope != null && !timeScope.isEmpty()) {
			for (int i = 0; i < timeScope.size(); i++) {
				times = timeScope.get(i).get("time");
				iops = timeScope.get(i).get("iops");
				timeScopeBuffer.append(times + "," + iops + "=");
			}
			return timeScopeBuffer.toString().substring(0,
					timeScopeBuffer.toString().lastIndexOf("="));
		} else {
			return "";
		}
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
	 * 获得模板参数
	 * 
	 * @Description: TODO
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	public List getparaMap(SmsBatch smsBatch) {
		List list = new ArrayList();
		if (smsBatch.getParam1() != null && !("").equals(smsBatch.getParam1())) {
			list.add(smsBatch.getParam1());
		}
		if (smsBatch.getParam2() != null && !("").equals(smsBatch.getParam2())) {
			list.add(smsBatch.getParam2());
		}
		if (smsBatch.getParam3() != null && !("").equals(smsBatch.getParam3())) {
			list.add(smsBatch.getParam3());
		}
		if (smsBatch.getParam4() != null && !("").equals(smsBatch.getParam4())) {
			list.add(smsBatch.getParam4());
		}
		if (smsBatch.getParam5() != null && !("").equals(smsBatch.getParam5())) {
			list.add(smsBatch.getParam5());
		}
		if (smsBatch.getParam6() != null && !("").equals(smsBatch.getParam6())) {
			list.add(smsBatch.getParam6());
		}
		if (smsBatch.getParam7() != null && !("").equals(smsBatch.getParam7())) {
			list.add(smsBatch.getParam7());
		}
		if (smsBatch.getParam8() != null && !("").equals(smsBatch.getParam8())) {
			list.add(smsBatch.getParam8());
		}
		if (smsBatch.getParam9() != null && !("").equals(smsBatch.getParam9())) {
			list.add(smsBatch.getParam9());
		}
		if (smsBatch.getParam10() != null
				&& !("").equals(smsBatch.getParam10())) {
			list.add(smsBatch.getParam10());
		}
		return list;
	}

	/**
	 * 
	 * 生成sla xml bean对象
	 * 
	 * 
	 * @param batchId
	 * 
	 * @param uuid
	 * 
	 * @param timestamp
	 * 
	 * @param department
	 * 
	 * @param file
	 * 
	 * @param template
	 * 
	 * @param paramlist
	 * 
	 * @param type
	 * 
	 * @param channels
	 * 
	 * @param allowChangeChannel
	 * 
	 * @param dateScope
	 * 
	 * @param timeList
	 * 
	 * @param priority
	 * 
	 * @param isReply
	 * 
	 * @param realTime
	 * 
	 * @param signiture
	 * 
	 * @param secureHash
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.GroupSmsSendService#newSlaXml(java.lang
	 *      .String, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.List,
	 *      java.lang.String, java.util.List, java.lang.String,
	 *      java.lang.String, java.util.List, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public SendRequest newSlaXml(String batchId, String uuid, String timestamp,
			String department, String file, String template, String type,
			String allowChangeChannel, String starttime, String endtime,
			List<Map<String, String>> timeList, Long priority, String isReply,
			String realTime, String signiture, String secureHash,
			String recordCount, Map channelmap, String campaignId) {
		// TODO Auto-generated method stub
		// 创建sendrequest 节点
		SendRequest sendRequest = new SendRequest();
		sendRequest.setBatchId(batchId);
		sendRequest.setRecordCount(recordCount);
		sendRequest.setDepartment(department);
		sendRequest.setTimestamp(timestamp);
		sendRequest.setUuid(uuid);
		sendRequest.setCampaignId(campaignId);
		sendRequest.setFile(file);
		sendRequest.setTemplate(template);
		// 设置sla 节点
		SendSla sla = new SendSla();
		List<Channel> list = new ArrayList<Channel>();
		// if (channelmap != null) {
		// if (channelmap.get("CMCC") != null) {
		// Channel channel1 = new Channel();
		// channel1.setType("CMCC");
		// channel1.setChannel(channelmap.get("CMCC").toString());
		// list.add(channel1);
		// }
		// }
		// if (channelmap.get("TELECOM") != null) {
		// Channel channel2 = new Channel();
		// channel2.setType("TELECOM");
		// channel2.setChannel(channelmap.get("TELECOM").toString());
		// list.add(channel2);
		// }
		// if (channelmap.get("UNICOM") != null) {
		// Channel channel3 = new Channel();
		// channel3.setType("UNICOM");
		// channel3.setChannel(channelmap.get("UNICOM").toString());
		// list.add(channel3);
		// }
		sla.setType(type);
		if (list != null && !list.isEmpty()) {
			sla.setChannels(list);
		}
		DateScope dateScope = new DateScope();
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
		} else {
			TimeScope timeScope = new TimeScope();
			timeScope.setTime("");
			timeScope.setIops("");
			list2.add(timeScope);
		}
		if (list2 != null && !list2.isEmpty()) {
			dateScope.setTimeScopes(list2);
		}
		sla.setAllowChangeChannel(allowChangeChannel);
		sla.setDateScope(dateScope);
		if (priority != null) {
			sla.setPriority(priority.toString());
		}
		sla.setIsReply(isReply);
		sla.setRealTime(realTime);
		sla.setSigniture(signiture);
		sendRequest.setSla(sla);
		sendRequest.setSecureHash(secureHash);
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		logger.info("调用批量短信接口生成xml。:" + batchId + "xml:"
				+ xstream.toXML(sendRequest));
		return sendRequest;
	}

	/**
	 * 创建文件夹
	 * 
	 * @Description: TODO
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean newFileDirs() {
		File dir = new File(PropertiesUtil.getSmsCsvPath()
				+ DateTimeUtil.sim2.format(new Date()));
		if (dir.exists()) {
			return false;
		}
		dir.mkdirs();
		return true;
	}

	/**
	 * csv文件长传到ftp (非 Javadoc)
	 * <p>
	 * Title: newCsvMoveToFtp
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smsBatchList
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.GroupSmsSendService#newCsvMoveToFtp(java.util.List)
	 */
	public Boolean newCsvMoveToFtp(List<SmsBatch> smsBatchList,
			String filename, String template, String source) {
		// TODO Auto-generated method stub
		FileWriter fw = null;
		PrintStream printStream = null;
		Boolean flag = true;
		newFileDirs();
		String ftpnames = PropertiesUtil.getSmsCsvPath()
				+ DateTimeUtil.sim2.format(new Date()) + "/" + filename;
		List<SmsSendDetail> smsSendDetails = new ArrayList<SmsSendDetail>();
		// 模板参数
		StringBuffer paramBuffer = new StringBuffer();
		// 获得所有参数
		try {
			File file2 = new File(ftpnames);
			file2.createNewFile();// 创建文件
			printStream = new PrintStream(new FileOutputStream(file2), false,
					"utf-8");
			fw = new FileWriter(ftpnames);
			for (int i = 0; i < smsBatchList.size(); i++) {
				SmsSendDetail smsSendDetail = new SmsSendDetail();
				smsSendDetail.setBatchId(smsBatchList.get(i).getBatchId());
				smsSendDetail.setConnectId(smsBatchList.get(i).getConnectId());
				smsSendDetail.setUuid(smsBatchList.get(i).getUuid());
				// 短信内容
				smsSendDetail.setContent(replaceParam(template,
						getparaMap(smsBatchList.get(i))));
				smsSendDetail.setCreatetime(new Date());
				smsSendDetail.setCreator(smsBatchList.get(i).getCreator());
				smsSendDetail.setMobile(smsBatchList.get(i).getTombile());
				smsSendDetail.setSource(source);
				smsSendDetail.setSmsType("2");
				smsSendDetail.setLastModifyDate(new Date());
				smsSendDetails.add(smsSendDetail);
				if (smsBatchList.get(i).getParam1() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam1() + "\t");
				}
				if (smsBatchList.get(i).getParam2() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam2() + "\t");
				}
				if (smsBatchList.get(i).getParam3() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam3() + "\t");
				}
				if (smsBatchList.get(i).getParam4() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam4() + "\t");
				}
				if (smsBatchList.get(i).getParam5() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam5() + "\t");
				}
				if (smsBatchList.get(i).getParam6() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam6() + "\t");
				}
				if (smsBatchList.get(i).getParam7() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam7() + "\t");
				}
				if (smsBatchList.get(i).getParam8() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam8() + "\t");
				}
				if (smsBatchList.get(i).getParam9() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam9() + "\t");
				}
				if (smsBatchList.get(i).getParam10() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam10() + "\t");
				}
				if (paramBuffer.length() > 1) {
					printStream.print(smsBatchList.get(i).getUuid()
							+ "\t"
							+ smsBatchList.size()
							+ "\t"
							+ smsBatchList.get(i).getTombile()
							+ "\t"
							+ paramBuffer.toString().substring(0,
									paramBuffer.toString().lastIndexOf("\t"))
							+ "\t\n");
					paramBuffer.setLength(0);
				} else {
					printStream.print(smsBatchList.get(i).getUuid() + "\t"
							+ smsBatchList.size() + "\t"
							+ smsBatchList.get(i).getTombile() + "\t" + "\t\n");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		try {
			printStream.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			flag = false;
		}
		try {
			boolean temp = FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
					Integer.valueOf(PropertiesUtil.getFtpPort()),
					PropertiesUtil.getFtpName(), PropertiesUtil.getFtpPass(),
					PropertiesUtil.getSendFtp(), filename, ftpnames);
			if (temp == true) {
				FileUtil.deleteFile(ftpnames);
				logger.info("savedeatilist保存数据到数据库"
						+ smsBatchList.get(0).getBatchId());
				smsSendDetailDao.saveDetailList(smsSendDetails);
				logger.info("savedeatilist保存数据到数据库"
						+ smsBatchList.get(0).getBatchId() + "成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
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
	public synchronized Boolean uploadCsvToFtp(String filename,
			String ftpnames, SmsSend smsSend) {

		boolean temp = FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
				Integer.valueOf(PropertiesUtil.getFtpPort()),
				PropertiesUtil.getFtpName(), PropertiesUtil.getFtpPass(),
				PropertiesUtil.getSendFtp(), filename, ftpnames);
		if (temp == false) {
			smsSend.setFtpStatus("2");
			// 上传ftp失败
			smsSend.setSendStatus("5");
			smsSendDao.saveSmsSend(smsSend);
		}
		return temp;
	}

	/***
	 * 
	 * @Description: 分页发送
	 * @param smsBatchList
	 * @param filename
	 * @param template
	 * @param source
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean batchCsv(String batchId, String filename, String template,
			String source, String ftpnames, List<SmsBatch> smsBatchList,
			Integer count, Integer begin, Integer end, String campaignId) {
		// TODO Auto-generated method stub
		// FileWriter fw = null;
		Boolean flag = true;
		newFileDirs();
		List<SmsSendDetail> smsSendDetails = new ArrayList<SmsSendDetail>();
		// 模板参数
		StringBuffer paramBuffer = new StringBuffer();
		Date createTime = new Date();
		File file2 = new File(ftpnames);
		PrintStream printStream = null;
		// 获得所有参数
		try {
			// fw = new FileWriter(ftpnames, true);
			file2.createNewFile();// 创建文件
			printStream = new PrintStream(new FileOutputStream(file2, true),
					false, "utf-8");
			String temps = "";
			SmsSendDetail smsSendDetail = null;
			for (int i = 0; i < smsBatchList.size(); i++) {
				smsSendDetail = new SmsSendDetail();
				smsSendDetail.setBatchId(batchId);

				smsSendDetail.setConnectId(smsBatchList.get(i).getConnectId());
				smsSendDetail.setUuid(smsBatchList.get(i).getUuid());
				smsSendDetail.setCampaignId(Long.valueOf(campaignId));
				// 短信内容
				smsSendDetail.setContent(replaceParam(template,
						getparaMap(smsBatchList.get(i))));
				smsSendDetail.setCreatetime(DateTimeUtil.sim3
						.parse(DateTimeUtil.sim3.format(createTime)));
				smsSendDetail.setCreator(smsBatchList.get(i).getCreator());
				smsSendDetail.setMobile(smsBatchList.get(i).getTombile());
				smsSendDetail.setSource(source);
				smsSendDetail.setSmsType("2");
				smsSendDetail.setSendtime(new Date());
				smsSendDetail.setLastModifyDate(new Date());
				smsSendDetails.add(smsSendDetail);
				if (smsBatchList.get(i).getParam1() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam1() + "\t");
				}
				if (smsBatchList.get(i).getParam2() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam2() + "\t");
				}
				if (smsBatchList.get(i).getParam3() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam3() + "\t");
				}
				if (smsBatchList.get(i).getParam4() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam4() + "\t");
				}
				if (smsBatchList.get(i).getParam5() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam5() + "\t");
				}
				if (smsBatchList.get(i).getParam6() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam6() + "\t");
				}
				if (smsBatchList.get(i).getParam7() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam7() + "\t");
				}
				if (smsBatchList.get(i).getParam8() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam8() + "\t");
				}
				if (smsBatchList.get(i).getParam9() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam9() + "\t");
				}
				if (smsBatchList.get(i).getParam10() != null) {
					paramBuffer.append(smsBatchList.get(i).getParam10() + "\t");
				}
				if (paramBuffer.length() > 1) {
					temps = smsBatchList.get(i).getUuid()
							+ "\t"
							+ count
							+ "\t"
							+ smsBatchList.get(i).getTombile()
							+ "\t"
							+ paramBuffer.toString().substring(0,
									paramBuffer.toString().lastIndexOf("\t"))
							+ "\t\n";
					printStream.print(temps);
					paramBuffer.setLength(0);
				} else {
					temps = smsBatchList.get(i).getUuid() + "\t" + count + "\t"
							+ smsBatchList.get(i).getTombile() + "\t" + "\t\n";
					printStream.print(temps);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		try {
			printStream.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			flag = false;
		}
		try {
			// FileUtil.deleteFile(ftpnames);
			logger.info("savedeatilist保存数据到数据库" + batchId + "数量"
					+ smsSendDetails.size());
			smsSendDetailDao.saveDetailList(smsSendDetails);
			logger.info("savedeatilist保存数据到数据库" + batchId + "成功");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("savedeatilist保存数据到数据库" + batchId + "失败" + e);
			flag = false;
		}
		return flag;
	}

	public static List<String> getVar(String str) {

		List<String> result = new ArrayList<String>();
		// Pattern p = Pattern.compile("\\{([^}]*)\\}");
		Pattern p = Pattern.compile("\\{[^}]*\\}");
		Matcher m = p.matcher(str);
		String var = "";
		while (m.find()) {
			var = m.group();
			// if(var!=null && var.indexOf(".")<0){
			result.add(var);
			// }
		}

		return result;

	}

	/*
	 * (非 Javadoc) <p>Title: insertBatch</p> <p>Description: </p>
	 * 
	 * @param bathList
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.service.GroupSmsSendService#insertBatch(java
	 * .util.List)
	 */
	public boolean insertBatch(List<SmsBatch> bathList) {
		// TODO Auto-generated method stub
		boolean flag = true;
		try {
			smsBatchDao.insertSmsBatch(bathList);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 批量短信停止
	 * 
	 * @param batachid
	 * 
	 * @param deparment
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.GroupSmsSendService#smsStop(java.lang
	 *      .String, java.lang.String)
	 */
	public SmsSend smsStop(String batchId, String department) {
		// TODO Auto-generated method stub
		Boolean flag = true;
		SendRequest sendRequest = new SendRequest();
		sendRequest.setBatchId(batchId);
		sendRequest.setDepartment(department);
		SendSla sla = new SendSla();
		sla.setType("-1");
		sendRequest.setSla(sla);
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		logger.info(xstream.toXML(sendRequest));
		HttpHeaders requestHeaders = FtpUtil.createHttpHeader();
		HttpEntity<String> entity = new HttpEntity(sendRequest, requestHeaders);
		SmsSend smsSend = null;
		Long counts = 0l;
		try {
			smsSend = smsSendDao.getByBatchid(batchId);
			Response response = restTemplate.postForObject(
					PropertiesUtil.getStopSms(), entity, Response.class);
			if (response != null && !response.getStatus().equals("0")) {
				flag = true;
				smsSend.setSendStatus("3");
				smsSend.setSmsstopStatus("1");
				if (response.getErrorMsg() != null
						&& response.getErrorCode() != null) {
					smsSend.setErrorInfo(response.getErrorMsg());
					smsSend.setErrorCode(response.getErrorCode());
				}
			} else {
				smsSend.setSendStatus("4");
				smsSend.setSmsstopStatus("0");
				if (response != null) {
					if (response.getErrorMsg() != null
							&& response.getErrorCode() != null) {
						if (response.getErrorCode().equals("r:999")) {
							counts = smsSendDetailDao.getCounts(batchId,
									"receiveStatus");
							if (counts > 0l) {
								smsSend.setSendStatus("7");
							} else {
								smsSend.setSendStatus("8");
							}
						}
						smsSend.setErrorInfo(response.getErrorMsg());
						smsSend.setErrorCode(response.getErrorCode());
					}
				}
				flag = false;
			}
			smsSendDao.saveSmsSend(smsSend);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return smsSend;
	}

	/*
	 * (非 Javadoc) <p>Title: batchGroupSend</p> <p>Description: </p>
	 * 
	 * @param batchid
	 * 
	 * @param smsSend
	 * 
	 * @param filename
	 * 
	 * @param ftpnames
	 * 
	 * @param timeScope
	 * 
	 * @param count
	 * 
	 * @return
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.service.GroupSmsSendService#batchGroupSend(java
	 * .lang.String, com.chinadrtv.erp.smsapi.model.SmsSend, java.lang.String,
	 * java.lang.String, java.util.List, java.lang.Integer)
	 */
	public Boolean batchGroupSend(String batchid, SmsSend smsSend,
			String filename, String ftpnames,
			List<Map<String, String>> timeScope, Integer count) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: changeSendSpeed
	 * </p>
	 * <p>
	 * Description:设置每分钟平均发送量
	 * </p>
	 * 
	 * @param deparment
	 * 
	 * @param campaignId
	 * 
	 * @param type
	 * 
	 * @param minuCount
	 * 
	 * @return
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.GroupSmsSendService#changeSendSpeed(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean changeSendSpeed(CampaignMonitorDto campaignMonitor) {
		// TODO Auto-generated method stub
		SendRequest sendRequest = new SendRequest();
		sendRequest.setCampaignId("" + campaignMonitor.getCampaignId());
		if (campaignMonitor.getType().equals("0")) {
			sendRequest.setMinuCount("0");
		} else {
			sendRequest.setMinuCount(campaignMonitor.getMinuCount());
		}
		sendRequest.setTimestamp(DateTimeUtil.sim3.format(new Date()));
		sendRequest.setType(campaignMonitor.getType());
		sendRequest.setDepartment(campaignMonitor.getDeparment());
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		logger.info("设置campaign每分钟匀速发送" + xstream.toXML(sendRequest));
		HttpHeaders requestHeaders = FtpUtil.createHttpHeader();
		HttpEntity<String> entity = new HttpEntity(sendRequest, requestHeaders);
		Boolean flag = true;
		try {
			Response response = restTemplate
					.postForObject(PropertiesUtil.getChangeSendSpeed(), entity,
							Response.class);
			if (response.getStatus().equals("1")) {
				campaignMonitor.setType(campaignMonitor.getType());
				if (campaignMonitor.getType().equals("0")) {
					campaignMonitor.setStatus("3");
				}
				if (campaignMonitor.getType().equals("1")) {
					campaignMonitor.setStatus("1");
				}
			} else {
				if (campaignMonitor.getType().equals("0")) {
					campaignMonitor.setStatus("4");
				}
				if (campaignMonitor.getType().equals("1")) {
					campaignMonitor.setStatus("2");
				}
				flag = false;
			}
			if (response != null
					&& !StringUtil.isNullOrBank(response.getErrorMsg())
					&& !StringUtil.isNullOrBank(response.getErrorCode())) {
				campaignMonitor.setErrorMessage(response.getErrorMsg());
				campaignMonitor.setErrorCode(response.getErrorCode());
			}
			CampaignMonitor campaignMonitors = new CampaignMonitor();
			campaignMonitors.setCampaignId(campaignMonitor.getCampaignId());
			campaignMonitors.setCreateTime(campaignMonitor.getCreateTime());
			campaignMonitors.setCreateUser(campaignMonitor.getCreateUser());
			campaignMonitors.setDeparment(campaignMonitor.getDeparment());
			campaignMonitors.setErrorCode(campaignMonitor.getErrorCode());
			campaignMonitors.setErrorMessage(campaignMonitor.getErrorMessage());
			campaignMonitors.setId(campaignMonitor.getId());
			campaignMonitors.setMinuCount(campaignMonitor.getMinuCount());
			campaignMonitors.setSmsContent(campaignMonitor.getSmsContent());
			campaignMonitors.setSmsCount(campaignMonitor.getSmsCount());
			campaignMonitors.setStatus(campaignMonitor.getStatus());
			campaignMonitors.setUpdateTime(campaignMonitor.getUpdateTime());
			campaignMonitors.setUpdateUser(campaignMonitor.getUpdateUser());
			campaignMonitorDao.merge(campaignMonitors);
			// campaignMonitorDao.saveOrUpdate(campaignMonitors);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			logger.error("设置campaign每分钟匀速发送 失败  campaignid"
					+ campaignMonitor.getCampaignId() + e);
		}
		return flag;
	}

}
