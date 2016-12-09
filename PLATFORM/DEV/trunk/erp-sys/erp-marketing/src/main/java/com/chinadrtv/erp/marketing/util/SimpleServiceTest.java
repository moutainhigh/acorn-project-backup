package com.chinadrtv.erp.marketing.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chinadrtv.erp.marketing.service.SchedulerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-quartz.xml",
		"classpath:applicationContext-dao.xml",
		"classpath:applicationContext-resources.xml"})
public class SimpleServiceTest {

	@Resource
	private SchedulerService schedulerService;

	public void setSchedulerService(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@Test
	public void test() {
		// 执行业务逻辑...

		// 设置高度任务
		// 每10秒中执行调试一次
		schedulerService.schedule("0/10 * * ? * * *");

		Date startTime = this.parse("2013-01-25 11:34:00");
		Date endTime = this.parse("2013-01-25 11:33:00");

		// schedulerService.schedule(startTime);

		// schedulerService.schedule(startTime, endTime);
		//
		// schedulerService.schedule(startTime, null, 5);
		//
		schedulerService.schedule(startTime, null, 5, 20);

	}

	private Date parse(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
