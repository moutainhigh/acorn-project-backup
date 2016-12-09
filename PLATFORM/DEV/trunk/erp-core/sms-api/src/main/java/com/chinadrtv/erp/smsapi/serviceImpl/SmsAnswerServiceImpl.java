/*
 * @(#)SmsAnswerServiceImpl.java 1.0 2013-2-22下午5:22:14
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.smsapi.serviceImpl;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.chinadrtv.erp.smsapi.dao.SmsAnswerDao;
import com.chinadrtv.erp.smsapi.dto.Reply;
import com.chinadrtv.erp.smsapi.dto.SingleResponse;
import com.chinadrtv.erp.smsapi.model.SmsAnswer;
import com.chinadrtv.erp.smsapi.service.SmsAnswerService;
import com.chinadrtv.erp.smsapi.service.SmsSendStatusService;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.FtpUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-2-22 下午5:22:14
 * 
 */
@Service("smsAnswerService")
public class SmsAnswerServiceImpl implements SmsAnswerService {
	@Autowired
	private SmsAnswerDao smsAnswerDao;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private SmsSendStatusService smsSendStatusService;

	/**
	 * 
	 * 短信回复及时入库
	 * 
	 * @param xml
	 * 
	 * @see com.chinadrtv.erp.smsapi.service.SmsAnswerService#singleSmsAnswer(java
	 *      .lang.String)
	 */
	public void singleSmsAnswer(String xml) {
		// TODO Auto-generated method stub
		SmsAnswer smsAnswer = new SmsAnswer();
		XStream xstream = new XStream(new DomDriver());
		SingleResponse response = new SingleResponse();
		xstream.alias("reply", Reply.class);
		Reply reply = (Reply) xstream.fromXML(xml);
		smsAnswer.setCreatetime(new Date());
		smsAnswer.setMobile(reply.getMobile());
		if (reply.getReceiveChannel() != null) {
			smsAnswer.setReceiveChannel(reply.getReceiveChannel());
		}
		smsAnswer.setReceiveContent(reply.getContent());
		smsAnswer.setSmsChildId(reply.getSubCode());
		smsAnswer.setState("0");
		try {
			smsAnswer.setReceiveTime(DateTimeUtil.sim3.parse(reply
					.getReceivTime()));
			smsAnswerDao.saveSmsAnswer(smsAnswer);
			response.setStatus("1");
			response.setErrorCode("");
			response.setErrorMsg("");
			response.setTimestamp(DateTimeUtil.sim3.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			response.setStatus("0");
			response.setErrorCode("");
			response.setErrorMsg("");
			response.setTimestamp(DateTimeUtil.sim3.format(new Date()));
			e.printStackTrace();
		}
		HttpHeaders requestHeaders = FtpUtil.createHttpHeader();
		HttpEntity<String> entity = new HttpEntity(response, requestHeaders);
	}

	/*
	 * (非 Javadoc) <p>Title: groupSmsAnswer</p> <p>Description: </p>
	 * 
	 * @param xml
	 * 
	 * @see
	 * com.chinadrtv.erp.smsapi.service.SmsAnswerService#groupSmsAnswer(java
	 * .lang.String)
	 */
	public void groupSmsAnswer(String xml) {
		// TODO Auto-generated method stub
		try {
			smsSendStatusService.groupSendStatus(xml);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String xx = "{t1}我很好你好啊{t2}";
		xx = xx.replace("{t1}", "ok");
		System.out.println(xx);
		// SmsAnswerServiceImpl smsAnswerService = new SmsAnswerServiceImpl();
		// String xml =
		// "<reply> <mobile>15921450096</mobile> <receivTime> 2013-02-23 11:11:11 </receivTime> <content>咨询益尔健价格</content><receiveChannel>1066738123</receiveChannel><subCode>8347</subCode></reply>";
		// smsAnswerService.singleSmsAnswer(xml);
	}
}
