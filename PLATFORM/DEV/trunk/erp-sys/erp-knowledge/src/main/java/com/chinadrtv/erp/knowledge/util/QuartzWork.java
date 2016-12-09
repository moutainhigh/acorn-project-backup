/*
 * @(#)QuartzWork.java 1.0 2014-1-6下午2:13:24
 *
 * 橡果国际-erp开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.knowledge.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.chinadrtv.erp.knowledge.service.LuceneIndexService;
import com.chinadrtv.erp.util.SpringUtil;

/**
 * 
 * @author cuiming
 * @version 1.0
 * @since 2014-1-6 下午2:13:24
 * 
 */
public class QuartzWork extends QuartzJobBean {
	private static final Logger logger = LoggerFactory
			.getLogger(QuartzWork.class);

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		LuceneIndexService luceneIndexService = (LuceneIndexService) SpringUtil
				.getBean("luceneIndexService");
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			logger.info("开始时间" + simpleDateFormat.format(date));
			luceneIndexService.index("in", "1");
			luceneIndexService.index("in", "2");
			luceneIndexService.index("out", "1");
			luceneIndexService.index("out", "2");
			Date date2 = new Date();
			logger.info("结束时间" + simpleDateFormat.format(date2));
			logger.info("创建知识库索引成功");
		} catch (Exception e) {
			logger.error("创建知识库索引失败" + e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
