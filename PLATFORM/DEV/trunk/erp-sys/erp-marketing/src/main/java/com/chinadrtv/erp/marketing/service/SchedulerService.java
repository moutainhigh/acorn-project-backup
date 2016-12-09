/*
 * @(#)SchedulerService.java 1.0 2013-1-25上午9:49:41
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.service;

import java.util.Date;
import java.util.Map;

import org.quartz.CronExpression;
import org.quartz.JobDetail;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.dto.QuartzTriggerDto;
import com.chinadrtv.erp.marketing.util.JobCronSet;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author cuiming
 * @version 1.0
 * @since 2013-1-25 上午9:49:41
 * 
 */
public interface SchedulerService {
	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param cronExpression
	 *            Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	void schedule(String cronExpression);

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param cronExpression
	 *            Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 */
	void schedule(String name, String cronExpression);

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param cronExpression
	 *            Quartz Cron 表达式，如 "0/10 * * ? * * *"等
	 * @param group
	 *            Quartz CronExpression Group
	 */
	void schedule(String name, String cronExpression, String group);

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param cronExpression
	 *            Quartz CronExpression
	 */
	void schedule(CronExpression cronExpression);

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param cronExpression
	 *            Quartz CronExpression
	 */
	void schedule(String name, CronExpression cronExpression);

	/**
	 * 根据 Quartz Cron Expression 调试任务
	 * 
	 * @param name
	 *            Quartz CronTrigger名称
	 * @param cronExpression
	 *            Quartz CronExpression
	 * @param group
	 *            Quartz CronExpression Group
	 */
	void schedule(String name, CronExpression cronExpression, String group);

	/**
	 * 在startTime时执行调试一次
	 * 
	 * @param startTime
	 *            调度开始时间
	 */
	void schedule(Date startTime);

	/**
	 * 在startTime时执行调试一次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(Date startTime, String group);

	/**
	 * 在startTime时执行调试一次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 */
	void schedule(String name, Date startTime);

	/**
	 * 在startTime时执行调试一次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(String name, Date startTime, String group);

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 */
	void schedule(Date startTime, Date endTime);

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(Date startTime, Date endTime, String group);

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 */
	void schedule(String name, Date startTime, Date endTime);

	/**
	 * 在startTime时执行调试，endTime结束执行调度
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(String name, Date startTime, Date endTime, String group);

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 */
	void schedule(Date startTime, Date endTime, int repeatCount);

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(Date startTime, Date endTime, int repeatCount, String group);

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 */
	void schedule(String name, Date startTime, Date endTime, int repeatCount);

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(String name, Date startTime, Date endTime, int repeatCount,
			String group);

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param repeatInterval
	 *            执行时间隔间
	 */
	void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval);

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * 
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param repeatInterval
	 *            执行时间隔间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval, String group);

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param repeatInterval
	 *            执行时间隔间
	 */
	void schedule(String name, Date startTime, Date endTime, int repeatCount,
			long repeatInterval);

	/**
	 * 在startTime时执行调试，endTime结束执行调度，重复执行repeatCount次，每隔repeatInterval秒执行一次
	 * 
	 * @param name
	 *            Quartz SimpleTrigger 名称
	 * @param startTime
	 *            调度开始时间
	 * @param endTime
	 *            调度结束时间
	 * @param repeatCount
	 *            重复执行次数
	 * @param repeatInterval
	 *            执行时间隔间
	 * @param group
	 *            Quartz SimpleTrigger Group
	 */
	void schedule(String name, Date startTime, Date endTime, int repeatCount,
			long repeatInterval, String group);

	/**
	 * Trigger 参数,以com.sundoctor.example.Constant常量为键封装的Map
	 * 
	 * @param map
	 */
	void schedule(Map<String, String> map);

	/**
	 * 取得所有调度Triggers
	 * 
	 * @return
	 */
	Map<String, Object> getQrtzTriggers(QuartzTriggerDto quartzTriggerDto,
			DataGridModel dataGridModel);

	/**
	 * 根据名称和组别暂停Tigger
	 * 
	 * @param triggerName
	 * @param group
	 */
	void pauseTrigger(String triggerName, String group);

	/**
	 * 恢复Trigger
	 * 
	 * @param triggerName
	 * @param group
	 */
	void resumeTrigger(String triggerName, String group);

	/**
	 * 删除Trigger
	 * 
	 * @param triggerName
	 * @param group
	 */
	boolean removeTrigdger(String triggerName, String group);

	public void scheduleAddJob(JobCronSet jobCronSet, String group,
			Map<String, Object> params) throws Exception;

	public void scheduleUpdateJob(JobCronSet jobCronSet, String group,
			Map<String, Object> params) throws Exception;

	public void scheduleAddJob(JobCronSet jobCronSet) throws Exception;

	public void scheduleUpdateJob(JobCronSet jobCronSet) throws Exception;

	public JobDetail getJobDetail(String jobName, String group)
			throws Exception;

	public void deleteJob(String jobName, String group) throws Exception;

	public void scheduleAddJob(JobCronSet jobCronSet, Map<String, Object> params)
			throws Exception;

	public void scheduleUpdateJob(JobCronSet jobCronSet,
			Map<String, Object> params) throws Exception;

}
