/*
 * @(#)SynchSmsStatus.java 1.0 2013-7-18下午3:34:32
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.sms.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.smsapi.service.SmsSendStatusService;
import com.chinadrtv.erp.smsapi.util.DeCompressBook;
import com.chinadrtv.erp.smsapi.util.FileUtil;
import com.chinadrtv.erp.smsapi.util.FtpUtil;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.chinadrtv.erp.smsapi.util.StringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-7-18 下午3:34:32
 * 
 */
public class SynchSmsStatus {

	private static final Logger logger = LoggerFactory
			.getLogger(SynchSmsStatus.class);

	@Autowired
	private SmsSendStatusService smsSendStatusService;

	/*
	 * (非 Javadoc) <p>Title: executeInternal</p> <p>Description: </p>
	 * 
	 * @param context
	 * 
	 * @throws JobExecutionException
	 * 
	 * @see
	 * org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org
	 * .quartz.JobExecutionContext)
	 */
	protected void doStatus() {
		String type = PropertiesUtil.getTypes();
		String files = "";
		String path = "";
		String ftpPath = "";
		Boolean downFlag = false;
		String newlocals = "" + System.currentTimeMillis();
		String zipPath = "";
		if (type.equals("1")) {
			files = PropertiesUtil.getStatusctlFile() + newlocals + "/";
			path = PropertiesUtil.getStatusBakeFile();
			ftpPath = PropertiesUtil.getStatusFtp();
			// zipPath = files + newlocals + "/";
		} else {
			files = PropertiesUtil.getFeedBackctlFile() + newlocals + "/";
			path = PropertiesUtil.getFeedbackBakeFile();
			ftpPath = PropertiesUtil.getFeedbackFile();
			// zipPath = files + newlocals + "/";

		}
		// 从ftp 上下载文件
		downFlag = FtpUtil.startDown(ftpPath, files);
		logger.info("从ftp批量下载文件成功" + downFlag);
		File myDir = new File(files);
		File[] contents = myDir.listFiles();
		File zipDir = null;
		File[] zipContents = null;
		String batchid = "";
		String filename = "";
		String localPath = "";
		Boolean unzipFlag = false;
		DeCompressBook dec = new DeCompressBook();
		if (downFlag == true) {
			for (File file : contents) {
				filename = file.getName();
				localPath = file.getAbsolutePath();
				try {
					zipPath = files + "" + System.currentTimeMillis() + "/";
					unzipFlag = dec.unZip(localPath, zipPath);
					if (unzipFlag == true) {
						zipDir = new File(zipPath);
						zipContents = zipDir.listFiles();
						for (File zip : zipContents) {
							batchid = FileUtil.readBatchid(zip
									.getAbsolutePath());
							filename = zip.getName();
							localPath = zip.getAbsolutePath();
							if (!StringUtil.isNullOrBank(batchid)) {
								smsSendStatusService.groupSendStatusByFile(
										localPath, batchid, type, files);
								FileUtil.deleteFile(localPath);
							}
						}
					} else {
						logger.error("解压缩文件异常" + zipPath);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					logger.error("解压缩失败" + localPath + e1);
				}
				logger.info("处理数据成功" + filename + batchid);
			}
			FtpUtil.uploadFiles("", 0, "", "", path, localPath, contents);
			logger.info("把文件上传ftp成功");
			if (unzipFlag == true) {
				for (File file : contents) {
					FileUtil.deleteFile(file.getAbsolutePath());
					logger.info("删除本地文件" + filename + batchid);
				}
			}
		}
	}
}
