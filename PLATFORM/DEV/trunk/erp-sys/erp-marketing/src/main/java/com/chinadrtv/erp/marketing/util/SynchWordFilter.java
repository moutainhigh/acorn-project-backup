/*
 * @(#)SynchWordFilter.java 1.0 2013-6-3下午2:34:21
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.util;

import java.io.ObjectInputStream;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.smsapi.dto.Result;
import com.chinadrtv.erp.smsapi.util.DateTimeUtil;
import com.chinadrtv.erp.smsapi.util.PropertiesUtil;
import com.ctc.ctcoss.webservice.service.IKeywordServiceProxy;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-6-3 下午2:34:21
 * 
 */
public class SynchWordFilter extends QuartzJobBean {
	private static final Logger logger = LoggerFactory
			.getLogger(SynchWordFilter.class);

	/**
	 * 定时任务同步敏感词
	 */
	protected void executeInternal(JobExecutionContext jobexecutioncontext)
			throws JobExecutionException {
		XStream xstream = new XStream(new DomDriver());
		xstream.autodetectAnnotations(true);
		xstream.alias("result", Result.class);
		try {
			String endpoint = PropertiesUtil.getWordFilterUrl();
			IKeywordServiceProxy ywsnp = new IKeywordServiceProxy();
			String xml = ywsnp.getIKeywordService().getKeywords("");
			ObjectInputStream in = null;
			xstream.addImplicitCollection(Result.class, "keywordSegs");
			StringReader reader = new StringReader(xml);
			Constants.result = (Result) xstream.fromXML(xml);
			logger.info(DateTimeUtil.sim3.format(new Date()));
			logger.debug("定时同步更新敏感词" + xml);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			logger.error("定时同步更新敏感词 失败" + e);
		}

	}
}
