/*
 * @(#)PropertiesUtil.java 1.0 2013-2-20上午11:12:42
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-20 上午11:12:42
 * 
 */
public class PropertiesUtil {
	public static Properties props = new Properties();
	public static InputStream in = PropertiesUtil.class.getClassLoader()
			.getResourceAsStream("system.properties");

	public static String getFtpPort() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.port");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpName() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.name");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpUrl() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpPass() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.pass");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpFile() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.file");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getSmsCsvPath() {
		try {
			props.load(in);
			String value = props.getProperty("sms.csvPath");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getMd5Key() {
		try {
			props.load(in);
			String value = props.getProperty("md5.key");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDownloadStatus() {
		try {
			props.load(in);
			String value = props.getProperty("sms.downloadStatus");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDownloadFeedBack() {
		try {
			props.load(in);
			String value = props.getProperty("sms.downloadFeedBack");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDownloadAnswer() {
		try {
			props.load(in);
			String value = props.getProperty("sms.downloadAnswer");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getLogFile() {
		try {
			props.load(in);
			String value = props.getProperty("log.file");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getStatusctlFile() {
		try {
			props.load(in);
			String value = props.getProperty("statusctl.file");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getAnswerctlFile() {
		try {
			props.load(in);
			String value = props.getProperty("answerctl.file");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFeedBackctlFile() {
		try {
			props.load(in);
			String value = props.getProperty("feedbackctl.file");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getSmsStopFile() {
		try {
			props.load(in);
			String value = props.getProperty("smsStopctl.file");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得 群发短信url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getGroupSend() {
		try {
			props.load(in);
			String value = props.getProperty("groupSend.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信回复url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getSingleAnswer() {
		try {
			props.load(in);
			String value = props.getProperty("singleAnswer.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/****
	 * 获得单条短信状态
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getSingleStatus() {
		try {
			props.load(in);
			String value = props.getProperty("singleStatus.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送状态
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getSingleResponse() {
		try {
			props.load(in);
			String value = props.getProperty("singleResponse.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得群发短信状态发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getGroupResponse() {
		try {
			props.load(in);
			String value = props.getProperty("groupResponse.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getSingleSend() {
		try {
			props.load(in);
			String value = props.getProperty("singleSend.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getSendFtp() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.sendFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getAnswerFtp() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.answerFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getStatusFtp() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.statusFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getFeedbackFile() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.feedbackFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getSendBakeFile() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.send_bakeFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getAnswerBakeFile() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.answer_bakeFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getStatusBakeFile() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.status_bakeFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/***
	 * 获得单条短信发送url
	 * 
	 * @Description: TODO
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getFeedbackBakeFile() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.feedback_bakeFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getStopSms() {
		try {
			props.load(in);
			String value = props.getProperty("smsStop.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpStopSms() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.stopsmsFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getFtpStopSmsBake() {
		try {
			props.load(in);
			String value = props.getProperty("ftp.stopsms_bakeFile");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getTemps() {
		try {
			props.load(in);
			String value = props.getProperty("temps");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getSqlLoadDns() {
		try {
			props.load(in);
			String value = props.getProperty("sqlloadDns");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getSqlLoadPass() {
		try {
			props.load(in);
			String value = props.getProperty("sqlloadPass");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getSqlLoadUser() {
		try {
			props.load(in);
			String value = props.getProperty("sqlloadUser");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getHtml() {
		try {
			props.load(in);
			String value = props.getProperty("html.file");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getHtmlUrl() {
		try {
			props.load(in);
			String value = props.getProperty("html.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getPic() {
		try {
			props.load(in);
			String value = props.getProperty("pic.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getCopyPic() {
		try {
			props.load(in);
			String value = props.getProperty("copyPic.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getWordFilterUrl() {
		try {
			props.load(in);
			String value = props.getProperty("wordfilterUrl");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getChangeSendSpeed() {
		try {
			props.load(in);
			String value = props.getProperty("SendSpeed.url");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getTypes() {
		try {
			props.load(in);
			String value = props.getProperty("type");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getTimesDo() {
		try {
			props.load(in);
			String value = props.getProperty("timesDo");
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
