package com.chinadrtv.gonghang.biz.jms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.gonghang.dal.model.OrderConfig;
import com.chinadrtv.gonghang.service.OrderImportService;
import com.chinadrtv.runtime.jms.receive.JmsListener;

/**
 * Created with (oms). 
 * User: liukuan 
 * Date: 14-03-20 橡果国际-系统集成部 Copyright (c)
 * 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderImportTopicListener extends JmsListener<Object> {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderImportTopicListener.class);

	private AtomicBoolean isRun = new AtomicBoolean(false);

	@Autowired
	private OrderImportService gonghangOrderService;

	private List<OrderConfig> configList;

	public OrderImportTopicListener() {
		logger.info("OrderImportTopicListener is created.");
	}

	@Override
	public void messageHandler(Object msg) throws Exception {
		// 如果有在处理，那么直接忽略此消息
		if (isRun.compareAndSet(false, true)) {
			try {
				logger.info("Import start");
				
				Date startDate, endDate;
				endDate = new Date();
				startDate = getAddDay(endDate, Calendar.DATE, -60);

				gonghangOrderService.input(configList, startDate, endDate);
			} catch (Exception exp) {
				logger.error("Import order error:", exp);
			} finally {
				isRun.set(false);
				logger.info("Import finished.");
			}
		} else {
			logger.error("Import is running.");
		}

	}

	/**
	 * 转换当前日期的方法
	 * 
	 * @param d
	 *            当前日期
	 * @param field
	 *            参数类型
	 * @param amount
	 *            参数区间
	 * @return
	 */
	public static Date getAddDay(Date d, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(field, amount);
		return cal.getTime();
	}

	public void setConfigList(List<OrderConfig> configList) {
		this.configList = configList;
	}

}
