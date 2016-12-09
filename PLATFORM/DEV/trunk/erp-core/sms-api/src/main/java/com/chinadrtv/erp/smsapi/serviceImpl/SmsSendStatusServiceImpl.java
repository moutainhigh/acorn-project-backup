/*
 * @(#)SmsSendStatusServiceImpl.java 1.0 2013-2-22下午1:11:18
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.erp.smsapi.dao.SmsSendDao;
import com.chinadrtv.erp.smsapi.dao.SmsSendDetailDao;
import com.chinadrtv.erp.smsapi.dto.FeedBack;
import com.chinadrtv.erp.smsapi.dto.SingleResponse;
import com.chinadrtv.erp.smsapi.dto.SingleSendResponse;
import com.chinadrtv.erp.smsapi.model.SmsSend;
import com.chinadrtv.erp.smsapi.model.SmsSendDetail;
import com.chinadrtv.erp.smsapi.service.SmsSendStatusService;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.DeCompressBook;
import com.chinadrtv.erp.smsapi.util.FileUtil;
import com.chinadrtv.erp.smsapi.util.FtpUtil;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.chinadrtv.erp.smsapi.util.StringUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-22 下午1:11:18
 * 
 */
@Service("smsSendStatusService")
public class SmsSendStatusServiceImpl implements SmsSendStatusService {
	private static final Logger logger = LoggerFactory
			.getLogger(SmsSendStatusServiceImpl.class);
	@Autowired
	private SmsSendDao smsSendDao;

	@Autowired
	private SmsSendDetailDao smsSendDetailDao;

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplateSms;

	/*
	 * 单条短信发送状态 回执状态
	 * 
	 * @param xml
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.service.SmsSendStatusService#singleSendStatus
	 * (java.lang.String)
	 */
	public String singleSendStatus(String xml) {
		// TODO Auto-generated method stub
		SingleResponse singleResponse = new SingleResponse();
		SingleSendResponse singleSendResponse = new SingleSendResponse();
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("sendResponse", SingleSendResponse.class);
		singleSendResponse = (SingleSendResponse) xstream.fromXML(xml);
		String type = singleSendResponse.getType();
		String uuid = singleSendResponse.getUuid();
		SmsSend smsSend = smsSendDao.getByUuid(uuid);
		SmsSendDetail smsSendDetail = smsSendDetailDao.getByUuid(uuid);
		List<SmsSendDetail> list = new ArrayList<SmsSendDetail>();
		logger.info("单条短信状态返回 uuid:" + uuid + "type:" + type);
		try {
			// 状态为1 是发送状态反馈 2为 回执状态反馈
			if (type != null && type.equals("1")) {
				smsSendDetail.setReceiveStatus(singleSendResponse.getStatus());
				smsSendDetail.setReceiveStatusTime(DateTimeUtil.sim3
						.parse(singleSendResponse.getTimestamp()));
			} else {
				smsSendDetail.setFeedbackStatus(singleSendResponse.getStatus());
				smsSendDetail.setFeedbackStatusTime(DateTimeUtil.sim3
						.parse(singleSendResponse.getTimestamp()));
			}
			smsSendDetail.setLastModifyDate(new Date());
			if (singleSendResponse.getErrorCode() != null
					&& !("").equals(singleSendResponse.getErrorCode())) {
				smsSend.setErrorCode(singleSendResponse.getErrorCode());
			}
			if (singleSendResponse.getErrorMsg() != null
					&& !("").equals(singleSendResponse.getErrorMsg())) {
				smsSend.setErrorInfo(singleSendResponse.getErrorMsg());
			}
			smsSendDao.saveSmsSend(smsSend);
			list.add(smsSendDetail);
			smsSendDetailDao.updateDetailList(list);
			singleResponse.setStatus("1");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			xstream.autodetectAnnotations(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			singleResponse.setErrorCode("");
			singleResponse.setErrorMsg("");
			singleResponse.setStatus("0");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			xstream.autodetectAnnotations(true);
			;
		}
		HttpHeaders requestHeaders = FtpUtil.createHttpHeader();
		HttpEntity<String> entity = new HttpEntity(singleResponse,
				requestHeaders);
		// // // rest 返回 接受状态信息
		// restTemplate.postForObject(PropertiesUtil.getSingleResponse(), null,
		// String.class, entity);
		return xstream.toXML(singleResponse);
	}

	/***
	 * 多条状态统计
	 * 
	 * @Description: TODO
	 * @param xml
	 * @return void
	 * @throws
	 */
	public String groupSendStatus(String xml) {
		FeedBack feedBack = new FeedBack();
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("feedBack", FeedBack.class);
		feedBack = (FeedBack) xstream.fromXML(xml);
		String file = feedBack.getFile();
		String batchId = feedBack.getBatchId();
		SingleResponse singleResponse = new SingleResponse();
		Boolean flag = false;
		// ftp下载文件标志
		Boolean downloadFlag = true;
		// ftp 删除文件标志
		Boolean deleteFlag = true;
		// ftp 上传文件
		Boolean uploadFlag = true;

		// 从ftp 读出文件到本地
		try {
			logger.info("批量短信发送状态返回 batchId:" + batchId + "type:"
					+ feedBack.getType());
			// 状态1 为发送状态反馈
			if (feedBack.getType().equals("1")) {
				// 从获取下载文件
				downloadFlag = FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getDownloadStatus() + file,
						PropertiesUtil.getStatusFtp(),
						PropertiesUtil.getDownloadStatus());
				if (downloadFlag == false) {
					logger.error("批量短信发送状态返回 batchId:" + batchId + "ftp下载文件失败"
							+ file);
				} else {
					logger.info("批量短信发送状态返回 batchId:" + batchId + "ftp下载文件成功"
							+ file);
				}
				flag = oracleSqlLoad(PropertiesUtil.getDownloadStatus() + file,
						feedBack.getType(), batchId);
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getDownloadStatus() + file);

				// 删除ftp服务器上文件
				deleteFlag = FtpUtil.deleteFiles(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getStatusFtp());
				if (deleteFlag == false) {
					logger.error("批量短信发送状态返回 batchId:" + batchId
							+ "删除ftp服务器上文件失败" + file);
				} else {
					logger.info("批量短信发送状态返回 batchId:" + batchId + "删除ftp服务器上文件"
							+ file);
				}
				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getDownloadStatus() + file;
				// 上传到ftp备份服务器上
				uploadFlag = FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(),
						PropertiesUtil.getStatusBakeFile(), file, ftpnames);
				if (uploadFlag == false) {
					logger.error("批量短信发送状态返回 batchId:" + batchId
							+ "上传ftp服务器文件失败" + file);
				} else {
					logger.info("批量短信发送状态返回 batchId:" + batchId
							+ "上传ftp服务器文件成功" + file);
				}
				// 删除本地文件
				FileUtil.deleteFile(PropertiesUtil.getDownloadStatus() + file);
				logger.info("批量短信发送状态返回 batchId:" + batchId
						+ "上传到ftp服务器备份文件夹上并删除本地文件");
			}
			// 状态2 为短信发送回执反馈
			if (feedBack.getType().equals("2")) {

				downloadFlag = FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getDownloadFeedBack() + file,
						PropertiesUtil.getFeedbackFile(),
						PropertiesUtil.getDownloadFeedBack());
				if (downloadFlag == false) {
					logger.error("批量短信发送状态返回 batchId:" + batchId + "ftp下载文件失败"
							+ file);
				} else {
					logger.info("批量短信发送状态返回 batchId:" + batchId + "ftp下载文件成功"
							+ file);
				}
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getDownloadFeedBack() + file);
				flag = oracleSqlLoad(PropertiesUtil.getDownloadFeedBack()
						+ file, feedBack.getType(), batchId);
				// 删除ftp服务器上文件
				FtpUtil.deleteFiles(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getFeedbackFile());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "删除ftp服务器上文件");
				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getDownloadFeedBack() + file;

				// 上传到ftp备份服务器上
				FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(),
						PropertiesUtil.getFeedbackBakeFile(), file, ftpnames);
				// 删除本地文件
				FileUtil.deleteFile(PropertiesUtil.getDownloadFeedBack() + file);
				logger.info("批量短信发送状态返回 batchId:" + batchId
						+ "上传到ftp服务器备份文件夹上并删除本地文件");
			}
			// 状态3为 短信批量回复反馈
			if (feedBack.getType().equals("3")) {
				FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getDownloadAnswer() + file,
						PropertiesUtil.getAnswerFtp(),
						PropertiesUtil.getDownloadAnswer());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getDownloadAnswer() + file);
				flag = oracleSqlLoad(PropertiesUtil.getDownloadAnswer() + file,
						feedBack.getType(), batchId);
				// 删除ftp服务器上文件
				FtpUtil.deleteFiles(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getAnswerFtp());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "删除ftp服务器上文件");
				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getDownloadAnswer() + file;
				// 上传到ftp备份服务器上
				FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(),
						PropertiesUtil.getAnswerBakeFile(), file, ftpnames);
				// 删除本地文件
				FileUtil.deleteFile(PropertiesUtil.getDownloadAnswer() + file);
				logger.info("批量短信发送状态返回 batchId:" + batchId
						+ "上传到ftp服务器备份文件夹上并删除本地文件");

			}
			// 状态4为 短信批量停止
			if (feedBack.getType().equals("4")) {
				FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getSmsStopFile() + file,
						PropertiesUtil.getFtpStopSms(),
						PropertiesUtil.getSmsStopFile());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getSmsStopFile() + file);
				flag = oracleSqlLoad(PropertiesUtil.getSmsStopFile() + file,
						feedBack.getType(), batchId);
				// 删除ftp服务器上文件
				FtpUtil.deleteFiles(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getFtpStopSms());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "删除ftp服务器上文件");

				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getSmsStopFile() + file;
				// 上传到ftp备份服务器上
				FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(),
						PropertiesUtil.getFtpStopSmsBake(), file, ftpnames);
				// 删除本地文件
				FileUtil.deleteFile(PropertiesUtil.getSmsStopFile() + file);
				logger.info("批量短信发送状态返回 batchId:" + batchId
						+ "上传到ftp服务器备份文件夹上并删除本地文件");
			}
			if (flag == true) {
				singleResponse.setStatus("1");
				singleResponse.setTimestamp(DateTimeUtil.sim3
						.format(new Date()));
			} else {
				singleResponse.setStatus("0");
				singleResponse.setTimestamp(DateTimeUtil.sim3
						.format(new Date()));
				singleResponse.setErrorCode("1001");
				singleResponse.setErrorMsg("sqlloader 异常");
			}
			// rest 返回 接受状态信息
			// restTemplate.postForObject(PropertiesUtil.getSingleResponse(),
			// null, String.class, xstream.toXML(singleResponse));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("批量短信发送状态返回 异常batchId:" + batchId + "==" + e);
			singleResponse.setErrorCode("1000");
			singleResponse.setErrorMsg("" + e);
			singleResponse.setStatus("0");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			// rest 返回 接受状态信息
			// restTemplate.postForObject(PropertiesUtil.getSingleResponse(),
			// null, String.class, xstream.toXML(singleResponse));
		}
		return xstream.toXML(singleResponse);
	}

	/**
	 * 调用sqlloader
	 * 
	 * @Description: TODO
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean oracleSqlLoad(String path, String type, String batchId) {
		Boolean result = false;
		// 生成的ctl路径
		String answerfilepath = PropertiesUtil.getAnswerctlFile()
				+ new Date().getTime();
		// 生成的ctl路径
		String statusfilepath = PropertiesUtil.getStatusctlFile()
				+ new Date().getTime();
		// 生成的ctl路径
		String feedbackfilepath = PropertiesUtil.getFeedBackctlFile()
				+ new Date().getTime();
		// 生成的ctl路径
		String stopSmsfilepath = PropertiesUtil.getSmsStopFile()
				+ new Date().getTime();
		if (type.equals("3")) {
			String sql = "load data \r\n" + "characterset  UTF8 \r\n"
					+ "infile '" + path + "'" + "\r\n" + "append \r\n"
					+ "INTO TABLE SMS_ANSWER \r\n"
					+ "fields terminated by X'09'" + "TRAILING NULLCOLS "
					+ "( " + "MOBILE," + "\r\n"
					+ "RECEIVE_TIME  date 'YYYY-MM-DD HH24:MI:SS'," + "\r\n"
					+ "RECEIVE_CONTENT," + "\r\n" + "RECEIVE_CHANNEL," + "\r\n"
					+ "SMS_CHILD_ID," + "\r\n"
					+ "id  'SEQ_SMS_ANSWER.NEXTVAL' " + ")";
			logger.info("批量短信发送状态生成sql:" + batchId + "sql" + sql + "=type"
					+ type);
			FileUtil.write(answerfilepath + ".ctl", sql);
			// 执行的sqlload命令
			String sqlLoaderCommand = "sqlldr  userid="
					+ PropertiesUtil.getSqlLoadUser() + "/"
					+ PropertiesUtil.getSqlLoadPass() + "@"
					+ PropertiesUtil.getSqlLoadDns() + " control='"
					+ answerfilepath + ".ctl" + "' log='" + answerfilepath
					+ ".log'";
			logger.info("批量短信发送状态生成sqlLoaderCommand:" + batchId
					+ "sqlLoaderCommand" + sqlLoaderCommand + "=type" + type);
			try {
				Process ldr = Runtime.getRuntime().exec(sqlLoaderCommand);
				InputStream stderr = ldr.getInputStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println("*** " + line);
				stderr.close();
				isr.close();
				br.close();
				try {
					// ldr.waitFor();
					// FileUtil.deleteFile(answerfilepath + ".ctl");
					// FileUtil.deleteFile(answerfilepath + ".log");
					result = true;
				} catch (Exception e) {
					System.out.println("process function:loader wait for != 0");
				}
			} catch (Exception ex) {
				System.out.println("process function:loader execute exception"
						+ ex.toString());
			}
		}
		if (type.equals("1")) {
			String sql = "load data \r\n" + "characterset  UTF8 \r\n"
					+ "infile '"
					+ path
					+ "'"
					+ "\r\n"
					+ "append \r\n"
					+ "INTO TABLE  send_status_temp  \r\n"
					+ "fields terminated by X'09'"
					+ "TRAILING NULLCOLS "
					+ "( "
					+ "UUID,"
					+ "\r\n"
					+ "BATCHID,"
					+ "\r\n"
					+ "MOBILE,"
					+ "\r\n"
					+ "RECEIVRE_STATUS_TIME  date 'YYYY-MM-DD HH24:MI:SS',"
					+ "\r\n"
					+ "CHANNEL ,"
					+ "\r\n"
					+ "RECEIVRE_STATUS,"
					+ "\r\n"
					+ "ERROR_MESSAGE,"
					+ "\r\n"
					+ "type \""
					+ type
					+ "\"," + "\r\n" + "id  \"SEQ_SEND_TEMP.NEXTVAL\" )";
			logger.info("批量短信发送状态生成sql:" + batchId + "sql" + sql + "=type"
					+ type);
			FileUtil.write(statusfilepath + ".ctl", sql);
			// 执行的sqlload命令
			String sqlLoaderCommand = "sqlldr  userid="
					+ PropertiesUtil.getSqlLoadUser() + "/"
					+ PropertiesUtil.getSqlLoadPass() + "@"
					+ PropertiesUtil.getSqlLoadDns() + " control='"
					+ statusfilepath + ".ctl' log='" + statusfilepath + ".log'";
			try {
				logger.info("批量短信发送状态生成sqlLoaderCommand:" + batchId
						+ "sqlLoaderCommand" + sqlLoaderCommand + "=type"
						+ type);
				Process ldr = Runtime.getRuntime().exec(sqlLoaderCommand);
				logger.info("ldr:" + batchId + ldr);
				InputStream stderr = ldr.getInputStream();
				logger.info("ldr:" + batchId + stderr);
				InputStreamReader isr = new InputStreamReader(stderr);
				logger.info("ldr:" + batchId + isr);
				BufferedReader br = new BufferedReader(isr);
				// logger.info("ldrwaitFor" + ldr.waitFor());
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println("*** " + line);
				stderr.close();
				isr.close();
				br.close();
				logger.info("sqlldr执行结束");
				try {
					// ldr.waitFor();
					// 调用存储过程
					jdbcTemplateSms.execute("call statusNew (" + batchId + ","
							+ type + ")");
					FileUtil.deleteFile(statusfilepath + ".ctl");
					FileUtil.deleteFile(statusfilepath + ".log");
					result = true;
				} catch (Exception e) {
					logger.error("process function:loader wait for != 0" + e);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("sqlldr执行失败" + e);
			}
		}
		if (type.equals("2")) {
			String sql = "load data \r\n" + "characterset  UTF8 \r\n"
					+ "infile '"
					+ path
					+ "'"
					+ "\r\n"
					+ "append \r\n"
					+ "INTO TABLE  send_status_temp  \r\n"
					+ "fields terminated by X'09'"
					+ "TRAILING NULLCOLS "
					+ "( "
					+ "UUID,"
					+ "\r\n"
					+ "BATCHID,"
					+ "\r\n"
					+ "MOBILE,"
					+ "\r\n"
					+ "FEEDBACK_STATUS_TIME  date 'YYYY-MM-DD HH24:MI:SS',"
					+ "\r\n"
					+ "CHANNEL ,"
					+ "\r\n"
					+ "FEEDBACK_STATUS,"
					+ "\r\n"
					+ "ERROR_MESSAGE,"
					+ "\r\n"
					+ "type \""
					+ type
					+ "\"," + "\r\n" + "id  \"SEQ_SEND_TEMP.NEXTVAL\" )";
			// logger.info("批量短信发送状态生成sql:" + batchId + "sql" + sql + "=type"
			// + type);
			FileUtil.write(feedbackfilepath + ".ctl", sql);
			// 执行的sqlload命令
			String sqlLoaderCommand = "sqlldr  userid="
					+ PropertiesUtil.getSqlLoadUser() + "/"
					+ PropertiesUtil.getSqlLoadPass() + "@"
					+ PropertiesUtil.getSqlLoadDns() + " control='"
					+ feedbackfilepath + ".ctl' log='" + feedbackfilepath
					+ ".log'";
			try {
				logger.info("批量短信发送状态生成sqlLoaderCommand:" + batchId
						+ "sqlLoaderCommand" + sqlLoaderCommand + "=type"
						+ type);
				Process ldr = Runtime.getRuntime().exec(sqlLoaderCommand);
				// logger.info("ldr:" + batchId + ldr);
				InputStream stderr = ldr.getInputStream();
				// logger.info("ldr:" + batchId + stderr);
				InputStreamReader isr = new InputStreamReader(stderr);
				// logger.info("ldr:" + batchId + isr);
				BufferedReader br = new BufferedReader(isr);
				// logger.info("ldrwaitFor" + ldr.waitFor());
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println("*** " + line);
				stderr.close();
				isr.close();
				br.close();
				logger.info("sqlldr执行结束");
				try {
					// ldr.waitFor();
					// 调用存储过程
					jdbcTemplateSms.execute("call statusNew (" + batchId + ","
							+ type + ")");
					FileUtil.deleteFile(feedbackfilepath + ".ctl");
					FileUtil.deleteFile(feedbackfilepath + ".log");
					result = true;
				} catch (Exception e) {
					logger.error("process function:loader wait for != 0" + e);
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("sqlldr执行失败" + e);
				e.printStackTrace();
			}
		}
		if (type.equals("4")) {
			String sql = "load data \r\n" + "characterset  UTF8 \r\n"
					+ "infile '"
					+ path
					+ "'"
					+ "\r\n"
					+ "append \r\n"
					+ "INTO TABLE  send_status_temp  \r\n"
					+ "fields terminated by X'09'"
					+ "TRAILING NULLCOLS "
					+ "( "
					+ "UUID,"
					+ "\r\n"
					+ "BATCHID,"
					+ "\r\n"
					+ "MOBILE,"
					+ "\r\n"
					+ "SMSSTOP_STATUS_TIME  date 'YYYY-MM-DD HH24:MI:SS',"
					+ "\r\n"
					+ "CHANNEL ,"
					+ "\r\n"
					+ "SMSSTOP_STATUS,"
					+ "\r\n"
					+ "ERROR_MESSAGE,"
					+ "\r\n"
					+ "type \""
					+ type
					+ "\"," + "\r\n" + "id  \"SEQ_SEND_TEMP.NEXTVAL\" )";
			logger.info("批量短信发送状态生成sql:" + batchId + "sql" + sql + "=type"
					+ type);
			FileUtil.write(stopSmsfilepath + ".ctl", sql);
			// 执行的sqlload命令
			String sqlLoaderCommand = "sqlldr  userid="
					+ PropertiesUtil.getSqlLoadUser() + "/"
					+ PropertiesUtil.getSqlLoadPass() + "@"
					+ PropertiesUtil.getSqlLoadDns() + " control='"
					+ stopSmsfilepath + ".ctl' log='" + stopSmsfilepath
					+ ".log'";
			try {
				logger.info("批量短信发送状态生成sqlLoaderCommand:" + batchId
						+ "sqlLoaderCommand" + sqlLoaderCommand + "=type"
						+ type);
				Process ldr = Runtime.getRuntime().exec(sqlLoaderCommand);
				InputStream stderr = ldr.getInputStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println("*** " + line);
				stderr.close();
				isr.close();
				br.close();
				try {
					// ldr.waitFor();
					// 调用存储过程
					jdbcTemplateSms.execute("call statusNew (" + batchId + ","
							+ type + ")");
					FileUtil.deleteFile(stopSmsfilepath + ".ctl");
					FileUtil.deleteFile(stopSmsfilepath + ".log");
					result = true;
				} catch (Exception e) {
					System.out.println("process function:loader wait for != 0");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/***
	 * 多条状态统计
	 * 
	 * @Description: TODO
	 * @param xml
	 * @return void
	 * @throws
	 */
	public String groupSendStatusByFile(String file, String batchId,
			String type, String path) {
		Boolean flag = false;
		// ftp下载文件标志
		Boolean downloadFlag = true;
		// ftp 删除文件标志
		Boolean deleteFlag = true;
		// ftp 上传文件
		Boolean uploadFlag = true;
		// 从ftp 读出文件到本地
		try {
			logger.info("批量短信发送状态返回 batchId:" + batchId + "type:" + type);
			// 状态1 为发送状态反馈
			if (type.equals("1")) {
				flag = oracleSqlLoadByFile(path, type, batchId, file);
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:" + path);
				// 删除ftp服务器上文件
				deleteFlag = FtpUtil.deleteFiles(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getStatusFtp());
				if (deleteFlag == false) {
					logger.error("批量短信发送状态返回 batchId:" + batchId
							+ "删除ftp服务器上文件失败" + file);
				} else {
					logger.info("批量短信发送状态返回 batchId:" + batchId + "删除ftp服务器上文件"
							+ file);
				}
			}
			// 状态2 为短信发送回执反馈
			if (type.equals("2")) {
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getDownloadFeedBack() + file);
				flag = oracleSqlLoadByFile(path, type, batchId, file);
				// 删除ftp服务器上文件
				deleteFlag = FtpUtil.deleteFiles(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getFeedbackFile());
			}
			// 状态3为 短信批量回复反馈
			if (type.equals("3")) {
				FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getDownloadAnswer() + file,
						PropertiesUtil.getAnswerFtp(),
						PropertiesUtil.getDownloadAnswer());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getDownloadAnswer() + file);
				flag = oracleSqlLoad(PropertiesUtil.getDownloadAnswer() + file,
						type, batchId);
				// 删除ftp服务器上文件
				FtpUtil.deleteFiles(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getAnswerFtp());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "删除ftp服务器上文件");
				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getDownloadAnswer() + file;
				// 上传到ftp备份服务器上
				FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(),
						PropertiesUtil.getAnswerBakeFile(), file, ftpnames);
				// 删除本地文件
				FileUtil.deleteFile(PropertiesUtil.getDownloadAnswer() + file);
				logger.info("批量短信发送状态返回 batchId:" + batchId
						+ "上传到ftp服务器备份文件夹上并删除本地文件");
			}
			// 状态4为 短信批量停止
			if (type.equals("4")) {
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getSmsStopFile() + file);
				flag = oracleSqlLoadByFile(path, type, batchId, file);
			}
		} catch (Exception e) {
			logger.error("批量短信发送状态返回 异常batchId:" + batchId + "==" + e);
		}
		return "";
	}

	public Boolean oracleSqlLoadByFile(String path, String type,
			String batchId, String file) {
		Boolean result = false;
		// 生成的ctl路径
		String answerfilepath = PropertiesUtil.getAnswerctlFile()
				+ new Date().getTime();
		// 生成的ctl路径
		String statusfilepath = path + new Date().getTime();
		// 生成的ctl路径
		String feedbackfilepath = path + new Date().getTime();
		// 生成的ctl路径
		String stopSmsfilepath = PropertiesUtil.getSmsStopFile()
				+ new Date().getTime();
		if (type.equals("3")) {
			String sql = "load data \r\n" + "characterset  UTF8 \r\n"
					+ "infile '" + file + "'" + "\r\n" + "append \r\n"
					+ "INTO TABLE SMS_ANSWER \r\n"
					+ "fields terminated by X'09'" + "TRAILING NULLCOLS "
					+ "( " + "MOBILE," + "\r\n"
					+ "RECEIVE_TIME  date 'YYYY-MM-DD HH24:MI:SS'," + "\r\n"
					+ "RECEIVE_CONTENT," + "\r\n" + "RECEIVE_CHANNEL," + "\r\n"
					+ "SMS_CHILD_ID," + "\r\n"
					+ "id  'SEQ_SMS_ANSWER.NEXTVAL' " + ")";
			logger.info("批量短信发送状态生成sql:" + batchId + "sql" + sql + "=type"
					+ type);
			FileUtil.write(answerfilepath + ".ctl", sql);
			// 执行的sqlload命令
			String sqlLoaderCommand = "sqlldr  userid="
					+ PropertiesUtil.getSqlLoadUser() + "/"
					+ PropertiesUtil.getSqlLoadPass() + "@"
					+ PropertiesUtil.getSqlLoadDns() + " control='"
					+ answerfilepath + ".ctl" + "' log='" + answerfilepath
					+ ".log'";
			logger.info("批量短信发送状态生成sqlLoaderCommand:" + batchId
					+ "sqlLoaderCommand" + sqlLoaderCommand + "=type" + type);
			try {
				Process ldr = Runtime.getRuntime().exec(sqlLoaderCommand);
				InputStream stderr = ldr.getInputStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println("*** " + line);
				stderr.close();
				isr.close();
				br.close();
				try {
					// ldr.waitFor();
					FileUtil.deleteFile(answerfilepath + ".ctl");
					FileUtil.deleteFile(answerfilepath + ".log");
					result = true;
				} catch (Exception e) {
					System.out.println("process function:loader wait for != 0");
				}
			} catch (Exception ex) {
				System.out.println("process function:loader execute exception"
						+ ex.toString());
			}
		}
		if (type.equals("1")) {
			String sql = "load data \r\n" + "characterset  UTF8 \r\n"
					+ "infile '"
					+ file
					+ "'"
					+ "\r\n"
					+ "append \r\n"
					+ "INTO TABLE  send_status_temp  \r\n"
					+ "fields terminated by X'09'"
					+ "TRAILING NULLCOLS "
					+ "( "
					+ "UUID,"
					+ "\r\n"
					+ "BATCHID,"
					+ "\r\n"
					+ "MOBILE,"
					+ "\r\n"
					+ "RECEIVRE_STATUS_TIME  date 'YYYY-MM-DD HH24:MI:SS',"
					+ "\r\n"
					+ "CHANNEL ,"
					+ "\r\n"
					+ "RECEIVRE_STATUS,"
					+ "\r\n"
					+ "ERROR_MESSAGE,"
					+ "\r\n"
					+ "type \""
					+ type
					+ "\"," + "\r\n" + "id  \"SEQ_SEND_TEMP.NEXTVAL\" )";
			logger.info("批量短信发送状态生成sql:" + batchId + "sql" + sql + "=type"
					+ type);
			FileUtil.write(statusfilepath + ".ctl", sql);
			// 执行的sqlload命令
			String sqlLoaderCommand = "sqlldr  userid="
					+ PropertiesUtil.getSqlLoadUser() + "/"
					+ PropertiesUtil.getSqlLoadPass() + "@"
					+ PropertiesUtil.getSqlLoadDns() + " control='"
					+ statusfilepath + ".ctl' log='" + statusfilepath + ".log'";
			try {
				logger.info("批量短信发送状态生成sqlLoaderCommand:" + batchId
						+ "sqlLoaderCommand" + sqlLoaderCommand + "=type"
						+ type);
				Process ldr = Runtime.getRuntime().exec(sqlLoaderCommand);
				logger.info("ldr:" + batchId + ldr);
				InputStream stderr = ldr.getInputStream();
				logger.info("ldr:" + batchId + stderr);
				InputStreamReader isr = new InputStreamReader(stderr);
				logger.info("ldr:" + batchId + isr);
				BufferedReader br = new BufferedReader(isr);
				// logger.info("ldrwaitFor" + ldr.waitFor());
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println("*** " + line);
				stderr.close();
				isr.close();
				br.close();
				logger.info("sqlldr执行结束");
				try {
					// ldr.waitFor();
					// 调用存储过程
					jdbcTemplateSms.execute("call statusNew (" + batchId + ","
							+ type + ")");
					FileUtil.deleteFile(statusfilepath + ".ctl");
					FileUtil.deleteFile(statusfilepath + ".log");

					result = true;
				} catch (Exception e) {
					logger.error("process function:loader wait for != 0" + e);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("sqlldr执行失败" + e);
			}
		}
		if (type.equals("2")) {
			String sql = "load data \r\n" + "characterset  UTF8 \r\n"
					+ "infile '"
					+ file
					+ "'"
					+ "\r\n"
					+ "append \r\n"
					+ "INTO TABLE  send_status_temp  \r\n"
					+ "fields terminated by X'09'"
					+ "TRAILING NULLCOLS "
					+ "( "
					+ "UUID,"
					+ "\r\n"
					+ "BATCHID,"
					+ "\r\n"
					+ "MOBILE,"
					+ "\r\n"
					+ "FEEDBACK_STATUS_TIME  date 'YYYY-MM-DD HH24:MI:SS',"
					+ "\r\n"
					+ "CHANNEL ,"
					+ "\r\n"
					+ "FEEDBACK_STATUS,"
					+ "\r\n"
					+ "ERROR_MESSAGE,"
					+ "\r\n"
					+ "type \""
					+ type
					+ "\"," + "\r\n" + "id  \"SEQ_SEND_TEMP.NEXTVAL\" )";
			// logger.info("批量短信发送状态生成sql:" + batchId + "sql" + sql + "=type"
			// + type);
			FileUtil.write(feedbackfilepath + ".ctl", sql);
			// 执行的sqlload命令
			String sqlLoaderCommand = "sqlldr  userid="
					+ PropertiesUtil.getSqlLoadUser() + "/"
					+ PropertiesUtil.getSqlLoadPass() + "@"
					+ PropertiesUtil.getSqlLoadDns() + " control='"
					+ feedbackfilepath + ".ctl' log='" + feedbackfilepath
					+ ".log'";
			try {
				logger.info("批量短信发送状态生成sqlLoaderCommand:" + batchId
						+ "sqlLoaderCommand" + sqlLoaderCommand + "=type"
						+ type);
				Process ldr = Runtime.getRuntime().exec(sqlLoaderCommand);
				// logger.info("ldr:" + batchId + ldr);
				InputStream stderr = ldr.getInputStream();
				// logger.info("ldr:" + batchId + stderr);
				InputStreamReader isr = new InputStreamReader(stderr);
				// logger.info("ldr:" + batchId + isr);
				BufferedReader br = new BufferedReader(isr);
				// logger.info("ldrwaitFor" + ldr.waitFor());
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println("*** " + line);
				stderr.close();
				isr.close();
				br.close();
				logger.info("sqlldr执行结束");
				try {
					// ldr.waitFor();
					// 调用存储过程
					jdbcTemplateSms.execute("call statusNew (" + batchId + ","
							+ type + ")");
					FileUtil.deleteFile(feedbackfilepath + ".ctl");
					FileUtil.deleteFile(feedbackfilepath + ".log");
					result = true;
				} catch (Exception e) {
					logger.error("process function:loader wait for != 0" + e);
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("sqlldr执行失败" + e);
				e.printStackTrace();
			}
		}
		if (type.equals("4")) {
			String sql = "load data \r\n" + "characterset  UTF8 \r\n"
					+ "infile '"
					+ file
					+ "'"
					+ "\r\n"
					+ "append \r\n"
					+ "INTO TABLE  send_status_temp  \r\n"
					+ "fields terminated by X'09'"
					+ "TRAILING NULLCOLS "
					+ "( "
					+ "UUID,"
					+ "\r\n"
					+ "BATCHID,"
					+ "\r\n"
					+ "MOBILE,"
					+ "\r\n"
					+ "SMSSTOP_STATUS_TIME  date 'YYYY-MM-DD HH24:MI:SS',"
					+ "\r\n"
					+ "CHANNEL ,"
					+ "\r\n"
					+ "SMSSTOP_STATUS,"
					+ "\r\n"
					+ "ERROR_MESSAGE,"
					+ "\r\n"
					+ "type \""
					+ type
					+ "\"," + "\r\n" + "id  \"SEQ_SEND_TEMP.NEXTVAL\" )";
			logger.info("批量短信发送状态生成sql:" + batchId + "sql" + sql + "=type"
					+ type);
			FileUtil.write(stopSmsfilepath + ".ctl", sql);
			// 执行的sqlload命令
			String sqlLoaderCommand = "sqlldr  userid="
					+ PropertiesUtil.getSqlLoadUser() + "/"
					+ PropertiesUtil.getSqlLoadPass() + "@"
					+ PropertiesUtil.getSqlLoadDns() + " control='"
					+ stopSmsfilepath + ".ctl' log='" + stopSmsfilepath
					+ ".log'";
			try {
				logger.info("批量短信发送状态生成sqlLoaderCommand:" + batchId
						+ "sqlLoaderCommand" + sqlLoaderCommand + "=type"
						+ type);
				Process ldr = Runtime.getRuntime().exec(sqlLoaderCommand);
				InputStream stderr = ldr.getInputStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null)
					System.out.println("*** " + line);
				stderr.close();
				isr.close();
				br.close();
				try {
					// ldr.waitFor();
					// 调用存储过程
					jdbcTemplateSms.execute("call statusNew (" + batchId + ","
							+ type + ")");
					FileUtil.deleteFile(stopSmsfilepath + ".ctl");
					FileUtil.deleteFile(stopSmsfilepath + ".log");
					result = true;
				} catch (Exception e) {
					System.out.println("process function:loader wait for != 0");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 短信回执zip格式
	 */
	public String groupSendStatusByZip(String xml) {
		DeCompressBook dec = new DeCompressBook();
		FeedBack feedBack = new FeedBack();
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("feedBack", FeedBack.class);
		feedBack = (FeedBack) xstream.fromXML(xml);
		String file = feedBack.getFile();
		String batchId = feedBack.getBatchId();
		SingleResponse singleResponse = new SingleResponse();
		String type = feedBack.getType();
		Boolean flag = false;
		// ftp下载文件标志
		Boolean downloadFlag = true;
		// ftp 删除文件标志
		Boolean deleteFlag = true;
		// ftp 上传文件
		Boolean uploadFlag = true;
		// 获得当前毫秒数
		Long currents = System.currentTimeMillis();
		// 当前本地文件夹
		String nowLocal = "";
		// 解压标志
		Boolean unZipFlag = false;
		// 从ftp 读出文件到本地
		try {
			logger.info("批量短信发送状态返回 batchId:" + batchId + "type:"
					+ feedBack.getType());
			// 状态1 为发送状态反馈
			if (feedBack.getType().equals("1")) {
				// 从获取下载文件
				downloadFlag = FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getDownloadStatus() + file,
						PropertiesUtil.getStatusFtp(),
						PropertiesUtil.getDownloadStatus());
				if (downloadFlag == false) {
					logger.error("批量短信发送状态返回 batchId:" + batchId + "ftp下载文件失败"
							+ file);
				} else {
					logger.info("批量短信发送状态返回 batchId:" + batchId + "ftp下载文件成功"
							+ file);
				}
				nowLocal = PropertiesUtil.getStatusctlFile() + currents;
				logger.info("批量短信发送状态返回 batchId:" + batchId + "ftp下载zip到本地路径"
						+ nowLocal);
				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getDownloadStatus() + file;
				if (downloadFlag == true) {
					// 创建一个新文件夹 按照当前毫秒数
					unZipFlag = dec.unZip(PropertiesUtil.getDownloadStatus()
							+ file, nowLocal);
					if (unZipFlag == true) {
						doLocal(nowLocal, type, downloadFlag);
						// 删除ftp服务器上文件
						deleteFlag = FtpUtil.deleteFiles(
								PropertiesUtil.getFtpUrl(),
								Integer.valueOf(PropertiesUtil.getFtpPort()),
								PropertiesUtil.getFtpName(),
								PropertiesUtil.getFtpPass(), file,
								PropertiesUtil.getStatusFtp());
						if (deleteFlag == false) {
							logger.error("批量短信发送状态返回 batchId:" + batchId
									+ "删除ftp服务器上文件失败" + file);
						} else {
							logger.info("批量短信发送状态返回 batchId:" + batchId
									+ "删除ftp服务器上文件" + file);
						}
						// 上传到ftp备份服务器上
						uploadFlag = FtpUtil.uploadFile(
								PropertiesUtil.getFtpUrl(),
								Integer.valueOf(PropertiesUtil.getFtpPort()),
								PropertiesUtil.getFtpName(),
								PropertiesUtil.getFtpPass(),
								PropertiesUtil.getStatusBakeFile(), file,
								ftpnames);
						if (uploadFlag == false) {
							logger.error("批量短信发送状态返回 batchId:" + batchId
									+ "上传ftp服务器文件失败" + file);
						} else {
							logger.info("批量短信发送状态返回 batchId:" + batchId
									+ "上传ftp服务器文件成功" + file);
						}
						// 删除本地文件
						FileUtil.deleteFile(PropertiesUtil.getDownloadStatus()
								+ file);
						logger.info("批量短信发送状态返回 batchId:" + batchId
								+ "上传到ftp服务器备份文件夹上并删除本地文件");
					} else {
						logger.error("解压缩失败" + ftpnames);
					}
				} else {
					logger.error("下载文件失败" + ftpnames);
				}
			}
			// 状态2 为短信发送回执反馈
			if (feedBack.getType().equals("2")) {
				downloadFlag = FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getDownloadFeedBack() + file,
						PropertiesUtil.getFeedbackFile(),
						PropertiesUtil.getDownloadFeedBack());
				if (downloadFlag == false) {
					logger.error("批量短信发送状态返回 batchId:" + batchId + "ftp下载文件失败"
							+ file);
				} else {
					logger.info("批量短信发送状态返回 batchId:" + batchId + "ftp下载文件成功"
							+ file);
				}
				nowLocal = PropertiesUtil.getDownloadFeedBack() + currents;
				logger.info("批量短信发送状态返回 batchId:" + batchId + "ftp下载zip到本地路径"
						+ nowLocal);
				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getDownloadFeedBack() + file;
				if (downloadFlag == true) {
					logger.info("处理数据1" + ftpnames);
					// 创建一个新文件夹 按照当前毫秒数
					unZipFlag = dec.unZip(ftpnames, nowLocal);
					if (unZipFlag == true) {
						logger.info("处理数据2" + ftpnames);
						doLocal(nowLocal, type, downloadFlag);
						// 删除ftp服务器上文件
						deleteFlag = FtpUtil.deleteFiles(
								PropertiesUtil.getFtpUrl(),
								Integer.valueOf(PropertiesUtil.getFtpPort()),
								PropertiesUtil.getFtpName(),
								PropertiesUtil.getFtpPass(), file,
								PropertiesUtil.getFeedbackFile());
						if (deleteFlag == false) {
							logger.error("批量短信发送状态返回 batchId:" + batchId
									+ "删除ftp服务器上文件失败" + file);
						} else {
							logger.info("批量短信发送状态返回 batchId:" + batchId
									+ "删除ftp服务器上文件" + file);
						}
						// 上传到ftp备份服务器上
						FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
								Integer.valueOf(PropertiesUtil.getFtpPort()),
								PropertiesUtil.getFtpName(),
								PropertiesUtil.getFtpPass(),
								PropertiesUtil.getFeedbackBakeFile(), file,
								ftpnames);
						logger.info("批量短信发送状态返回 batchId:" + batchId
								+ "删除ftp服务器上文件");
						// 删除本地文件
						// FileUtil.deleteFile(PropertiesUtil.getDownloadFeedBack()
						// + file);
						logger.info("批量短信发送状态返回 batchId:" + batchId
								+ "上传到ftp服务器备份文件夹上并删除本地文件");
					} else {
						logger.error("解压缩失败" + ftpnames);
					}
				} else {
					logger.error("下载文件失败" + ftpnames);
				}
			}
			// 状态3为 短信批量回复反馈
			if (feedBack.getType().equals("3")) {
				downloadFlag = FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getDownloadAnswer() + file,
						PropertiesUtil.getAnswerFtp(),
						PropertiesUtil.getDownloadAnswer());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getDownloadAnswer() + file);

				nowLocal = PropertiesUtil.getDownloadAnswer() + currents;

				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getDownloadAnswer() + file;
				if (downloadFlag == true) {
					dec.unZip(ftpnames, nowLocal);
					doLocal(nowLocal, type, downloadFlag);
					// 删除ftp服务器上文件
					FtpUtil.deleteFiles(PropertiesUtil.getFtpUrl(),
							Integer.valueOf(PropertiesUtil.getFtpPort()),
							PropertiesUtil.getFtpName(),
							PropertiesUtil.getFtpPass(), file,
							PropertiesUtil.getAnswerFtp());
					logger.info("批量短信发送状态返回 batchId:" + batchId + "删除ftp服务器上文件");
					// 上传到ftp备份服务器上
					FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
							Integer.valueOf(PropertiesUtil.getFtpPort()),
							PropertiesUtil.getFtpName(),
							PropertiesUtil.getFtpPass(),
							PropertiesUtil.getAnswerBakeFile(), file, ftpnames);
					// 删除本地文件
					FileUtil.deleteFile(PropertiesUtil.getDownloadAnswer()
							+ file);
					logger.info("批量短信发送状态返回 batchId:" + batchId
							+ "上传到ftp服务器备份文件夹上并删除本地文件");
				}
			}
			// 状态4为 短信批量停止
			if (feedBack.getType().equals("4")) {
				downloadFlag = FtpUtil.download(PropertiesUtil.getFtpUrl(),
						Integer.valueOf(PropertiesUtil.getFtpPort()),
						PropertiesUtil.getFtpName(),
						PropertiesUtil.getFtpPass(), file,
						PropertiesUtil.getSmsStopFile() + file,
						PropertiesUtil.getFtpStopSms(),
						PropertiesUtil.getSmsStopFile());
				logger.info("批量短信发送状态返回 batchId:" + batchId + "file:"
						+ PropertiesUtil.getSmsStopFile() + file);
				nowLocal = PropertiesUtil.getSmsStopFile() + currents;
				logger.info("批量短信发送状态返回 batchId:" + batchId + "ftp下载zip到本地路径"
						+ nowLocal);
				// 获得输出流文件地址
				String ftpnames = PropertiesUtil.getSmsStopFile() + file;
				if (downloadFlag == true) {
					// 创建一个新文件夹 按照当前毫秒数
					unZipFlag = dec.unZip(ftpnames, nowLocal);
					if (unZipFlag == true) {
						doLocal(nowLocal, type, downloadFlag);
						// 删除ftp服务器上文件
						deleteFlag = FtpUtil.deleteFiles(
								PropertiesUtil.getFtpUrl(),
								Integer.valueOf(PropertiesUtil.getFtpPort()),
								PropertiesUtil.getFtpName(),
								PropertiesUtil.getFtpPass(), file,
								PropertiesUtil.getFtpStopSms());
						if (deleteFlag == false) {
							logger.error("批量短信发送状态返回 batchId:" + batchId
									+ "删除ftp服务器上文件失败" + file);
						} else {
							logger.info("批量短信发送状态返回 batchId:" + batchId
									+ "删除ftp服务器上文件" + file);
						}
						// 上传到ftp备份服务器上
						FtpUtil.uploadFile(PropertiesUtil.getFtpUrl(),
								Integer.valueOf(PropertiesUtil.getFtpPort()),
								PropertiesUtil.getFtpName(),
								PropertiesUtil.getFtpPass(),
								PropertiesUtil.getFtpStopSmsBake(), file,
								ftpnames);
					} else {
						logger.error("解压缩失败" + ftpnames);
					}
				} else {
					logger.error("下载文件失败" + ftpnames);
				}
			}
			if (flag == true) {
				singleResponse.setStatus("1");
				singleResponse.setTimestamp(DateTimeUtil.sim3
						.format(new Date()));
			} else {
				singleResponse.setStatus("0");
				singleResponse.setTimestamp(DateTimeUtil.sim3
						.format(new Date()));
				singleResponse.setErrorCode("1001");
				singleResponse.setErrorMsg("sqlloader 异常");
			}
			// rest 返回 接受状态信息
			// restTemplate.postForObject(PropertiesUtil.getSingleResponse(),
			// null, String.class, xstream.toXML(singleResponse));
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("批量短信发送状态返回 异常batchId:" + batchId + "==" + e);
			singleResponse.setErrorCode("1000");
			singleResponse.setErrorMsg("" + e);
			singleResponse.setStatus("0");
			singleResponse.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			// rest 返回 接受状态信息
			// restTemplate.postForObject(PropertiesUtil.getSingleResponse(),
			// null, String.class, xstream.toXML(singleResponse));
		}
		return xstream.toXML(singleResponse);
	}

	/**
	 * 本地处理文件导入数据库
	 * 
	 * @Description: TODO
	 * @param nowLocal
	 * @param type
	 * @param downloadFlag
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean doLocal(String nowLocal, String type, Boolean downloadFlag) {
		Boolean result = false;
		File myDir = new File(nowLocal);
		File[] contents = myDir.listFiles();
		String batchid = "";
		String filename = "";
		String localPath = "";
		if (downloadFlag == true) {
			for (File files : contents) {
				batchid = FileUtil.readBatchid(files.getAbsolutePath());
				filename = files.getName();
				localPath = files.getAbsolutePath();
				if (!StringUtil.isNullOrBank(batchid)) {
					groupSendStatusByFile(localPath, batchid, type, nowLocal);
				}
				logger.info("处理数据成功" + filename + batchid);
			}
			for (File files : contents) {
				FileUtil.deleteFile(files.getAbsolutePath());
				logger.info("删除本地文件" + files.getAbsolutePath());
			}
		}
		return result;
	}

	/**
	 * 解压缩下载到本地的zip包
	 * 
	 * @Description: TODO
	 * @return
	 * @return Boolean
	 * @throws
	 */
	public Boolean unzipSmsStatus(String filePath, String localPath) {
		DeCompressBook dec = new DeCompressBook();
		Boolean result = false;
		try {
			dec.unZip(filePath, localPath);
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("解压缩失败" + e);
		}
		return result;
	}

	public static void main(String[] args) {
		PropertiesUtil ropertiesUtil = new PropertiesUtil();
		String sql = "load data \r\n" + "characterset  UTF8 \r\n" + "infile '"
				+ "123" + "'" + "\r\n" + "append \r\n"
				+ "INTO TABLE  send_status_temp  \r\n"
				+ "fields terminated by X'09'" + "TRAILING NULLCOLS " + "( "
				+ "UUID," + "\r\n" + "BATCHID," + "\r\n" + "MOBILE," + "\r\n"
				+ "SMSSTOP_STATUS_TIME  date 'YYYY-MM-DD HH24:MI:SS'," + "\r\n"
				+ "CHANNEL ," + "\r\n" + "SMSSTOP_STATUS," + "\r\n"
				+ "ERROR_MESSAGE," + "\r\n" + "id  \"SEQ_SEND_TEMP.NEXTVAL\" )";

		System.out.println(sql);
	}
}
