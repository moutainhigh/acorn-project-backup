package com.chinadrtv.erp.task.jobs.demo3;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;

/**
 * 本实例通过打印日志来展示如何获取用户设置的参数。
 * @author zhangguosheng
 */
@Task(name="Demo3QuartzJob", description="测试打印info及获取参数")
public class Demo3QuartzJob extends SimpleJob{

	@Override
	public void doExecute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		@SuppressWarnings("unchecked")
		Map<String, String> parms = (Map<String, String>) jobDataMap.get(SimpleJob.JOB_PARMS);
		Iterator<Entry<String, String>> iterator = parms.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String> entity = iterator.next();
			logger.info("参数名：" + entity.getKey() + ", 参数值：" + entity.getValue());
		}
		logger.info("这是Demo3QuartzJob测试程序输出的。");
	}
	
}
