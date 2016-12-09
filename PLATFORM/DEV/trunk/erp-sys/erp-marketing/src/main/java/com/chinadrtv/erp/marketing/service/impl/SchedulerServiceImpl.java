package com.chinadrtv.erp.marketing.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.constant.DataGridModel;
import com.chinadrtv.erp.marketing.core.common.Constants;
import com.chinadrtv.erp.marketing.dao.QuartzDao;
import com.chinadrtv.erp.marketing.dto.QuartzTriggerDto;
import com.chinadrtv.erp.marketing.service.SchedulerService;
import com.chinadrtv.erp.marketing.util.CronParser;
import com.chinadrtv.erp.marketing.util.JobCronSet;
import com.chinadrtv.erp.util.DateUtil;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

	private Scheduler scheduler;
	private JobDetail jobDetail;
	private QuartzDao quartzDao;

	private JobDetail commonJobDetail;

	private static final Logger logger = LoggerFactory
			.getLogger(SchedulerServiceImpl.class);

	@Autowired
	public void setJobDetail(@Qualifier("jobDetail") JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	/**
	 * @param commonJobDetail
	 *            the commonJobDetail to set
	 */
	@Autowired
	public void setCommonJobDetail(
			@Qualifier("commonJobDetail") JobDetail commonJobDetail) {
		this.commonJobDetail = commonJobDetail;
	}

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Autowired
	public void setQuartzDao(@Qualifier("quartzDao") QuartzDao quartzDao) {
		this.quartzDao = quartzDao;
	}

	public void schedule(String cronExpression) {
		schedule("", cronExpression);
	}

	public void schedule(String name, String cronExpression) {
		schedule(name, cronExpression, Scheduler.DEFAULT_GROUP);
	}

	public void schedule(String name, String cronExpression, String group) {
		try {
			schedule(name, new CronExpression(cronExpression), group);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public void schedule(CronExpression cronExpression) {
		schedule(null, cronExpression);
	}

	public void schedule(String name, CronExpression cronExpression) {
		schedule(name, cronExpression, Scheduler.DEFAULT_GROUP);
	}

	public void schedule(String name, CronExpression cronExpression,
			String group) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			name += "&" + UUID.randomUUID().toString();
		}

		try {
			scheduler.addJob(jobDetail, true);

			CronTrigger cronTrigger = new CronTrigger(name, group,
					jobDetail.getName(), Scheduler.DEFAULT_GROUP);
			cronTrigger.setCronExpression(cronExpression);
			scheduler.scheduleJob(cronTrigger);
			scheduler.rescheduleJob(cronTrigger.getName(),
					cronTrigger.getGroup(), cronTrigger);

		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public void schedule(Date startTime) {
		schedule(startTime, Scheduler.DEFAULT_GROUP);
	}

	public void schedule(Date startTime, String group) {
		schedule(startTime, null, group);
	}

	public void schedule(String name, Date startTime) {
		schedule(name, startTime, Scheduler.DEFAULT_GROUP);
	}

	public void schedule(String name, Date startTime, String group) {
		schedule(name, startTime, null, group);
	}

	public void schedule(Date startTime, Date endTime) {
		schedule(startTime, endTime, Scheduler.DEFAULT_GROUP);
	}

	public void schedule(Date startTime, Date endTime, String group) {
		schedule(startTime, endTime, 0, group);
	}

	public void schedule(String name, Date startTime, Date endTime) {
		schedule(name, startTime, endTime, Scheduler.DEFAULT_GROUP);
	}

	public void schedule(String name, Date startTime, Date endTime, String group) {
		schedule(name, startTime, endTime, 0, group);
	}

	public void schedule(Date startTime, Date endTime, int repeatCount) {
		schedule(startTime, endTime, 0, Scheduler.DEFAULT_GROUP);
	}

	public void schedule(Date startTime, Date endTime, int repeatCount,
			String group) {
		schedule(null, startTime, endTime, 0, group);
	}

	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount) {
		schedule(name, startTime, endTime, 0, Scheduler.DEFAULT_GROUP);
	}

	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, String group) {
		schedule(name, startTime, endTime, 0, 1L, group);
	}

	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval) {
		schedule(startTime, endTime, repeatCount, repeatInterval,
				Scheduler.DEFAULT_GROUP);
	}

	public void schedule(Date startTime, Date endTime, int repeatCount,
			long repeatInterval, String group) {
		schedule(null, startTime, endTime, repeatCount, repeatInterval, group);
	}

	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval) {
		this.schedule(name, startTime, endTime, repeatCount, repeatInterval,
				Scheduler.DEFAULT_GROUP);
	}

	public void schedule(String name, Date startTime, Date endTime,
			int repeatCount, long repeatInterval, String group) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			name += "&" + UUID.randomUUID().toString();
		}

		try {
			scheduler.addJob(jobDetail, true);

			SimpleTrigger SimpleTrigger = new SimpleTrigger(name, group,
					jobDetail.getName(), Scheduler.DEFAULT_GROUP, startTime,
					endTime, repeatCount, repeatInterval);
			scheduler.scheduleJob(SimpleTrigger);
			scheduler.rescheduleJob(SimpleTrigger.getName(),
					SimpleTrigger.getGroup(), SimpleTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public void schedule(Map<String, String> map) {

		String temp = null;
		// 实例化SimpleTrigger
		SimpleTrigger SimpleTrigger = new SimpleTrigger();

		// 这些值的设置也可以从外面传入，这里采用默放值
		SimpleTrigger.setJobName(jobDetail.getName());
		SimpleTrigger.setJobGroup(Scheduler.DEFAULT_GROUP);
		SimpleTrigger.setRepeatInterval(1000L);

		// 设置名称
		temp = map.get(Constants.TRIGGERNAME);
		if (StringUtils.isEmpty(StringUtils.trim(temp))) {
			temp = UUID.randomUUID().toString();
		} else {
			// 在名称后添加UUID，保证名称的唯一性
			temp += "&" + UUID.randomUUID().toString();
		}
		SimpleTrigger.setName(temp);

		// 设置Trigger分组
		temp = map.get(Constants.TRIGGERGROUP);
		if (StringUtils.isEmpty(temp)) {
			temp = Scheduler.DEFAULT_GROUP;
		}
		SimpleTrigger.setGroup(temp);

		// 设置开始时间
		temp = map.get(Constants.STARTTIME);
		if (StringUtils.isNotEmpty(temp)) {
			SimpleTrigger.setStartTime(this.parseDate(temp));
		}

		// 设置结束时间
		temp = map.get(Constants.ENDTIME);
		if (StringUtils.isNotEmpty(temp)) {
			SimpleTrigger.setEndTime(this.parseDate(temp));
		}

		// 设置执行次数
		temp = map.get(Constants.REPEATCOUNT);
		if (StringUtils.isNotEmpty(temp) && NumberUtils.toInt(temp) > 0) {
			SimpleTrigger.setRepeatCount(NumberUtils.toInt(temp));
		}

		// 设置执行时间间隔
		temp = map.get(Constants.REPEATINTERVEL);
		if (StringUtils.isNotEmpty(temp) && NumberUtils.toLong(temp) > 0) {
			SimpleTrigger.setRepeatInterval(NumberUtils.toLong(temp) * 1000);
		}

		try {
			scheduler.addJob(jobDetail, true);

			scheduler.scheduleJob(SimpleTrigger);
			scheduler.rescheduleJob(SimpleTrigger.getName(),
					SimpleTrigger.getGroup(), SimpleTrigger);
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> getQrtzTriggers(
			QuartzTriggerDto quartzTriggerDto, DataGridModel dataGridModel) {
		Map<String, Object> result = new HashMap<String, Object>();
		Long count = quartzDao.getQrtzTriggersCount(quartzTriggerDto);
		result.put("total", count);
		dataGridModel.setCount(Integer.valueOf("" + count));
		result.put("rows",
				quartzDao.getQrtzTriggers(quartzTriggerDto, dataGridModel));
		return result;
	}

	public void pauseTrigger(String triggerName, String group) {
		try {
			scheduler.pauseTrigger(triggerName, group);// 停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public void resumeTrigger(String triggerName, String group) {
		try {
			// Trigger trigger = scheduler.getTrigger(triggerName, group);

			scheduler.resumeTrigger(triggerName, group);// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean removeTrigdger(String triggerName, String group) {
		try {
			scheduler.pauseTrigger(triggerName, group);// 停止触发器
			return scheduler.unscheduleJob(triggerName, group);// 移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private Date parseDate(String time) {
		try {
			return DateUtils.parseDate(time,
					new String[] { "yyyy-MM-dd HH:mm" });
		} catch (ParseException e) {
			logger.error("日期格式错误{}，正确格式为：yyyy-MM-dd HH:mm", time);
			throw new RuntimeException(e);
		}
	}

	public void scheduleAddJob(JobCronSet jobCronSet, String group,
			Map<String, Object> params) throws Exception {

		CronTrigger trigger = null;

		String jobName = jobCronSet.getJobName();
		if (StringUtils.isEmpty(jobName)) {
			UUID.randomUUID().toString();
		}

		commonJobDetail.setName(jobName);
		commonJobDetail.setGroup(group);
		trigger = new CronTrigger(jobName, group);
		JobDataMap map = commonJobDetail.getJobDataMap();
		commonJobDetail.setDurability(true);
		trigger.setCronExpression(CronParser.getCronExpress(jobCronSet));
		if (jobCronSet.getFrequency().equals("0")) {
			trigger.setStartTime(DateUtil.string2SqlTimestamp(DateUtil
					.date2FormattedString(new Date(), "yyyy-MM-dd HH:mm"),
					"yyyy-MM-dd HH:mm"));
		} else if (jobCronSet.getFrequency().equals("5")) {
			trigger.setStartTime(DateUtil.string2SqlTimestamp(
					jobCronSet.getTimeStr(), "yyyy-MM-dd HH:mm"));
		} else {
			trigger.setStartTime(DateUtil.string2SqlTimestamp(
					jobCronSet.getDateOfStart(), "yyyy-MM-dd HH:mm"));
		}

		if (jobCronSet.getNever().equals("1")) {
			trigger.setEndTime(DateUtil.string2SqlTimestamp("2099-01-01 00:00",
					"yyyy-MM-dd HH:mm"));
		} else if (jobCronSet.getFrequency().equals("0")) {
			trigger.setEndTime(DateUtil.string2SqlTimestamp(DateUtil
					.date2FormattedString(new Date(), "yyyy-MM-dd HH:mm"),
					"yyyy-MM-dd HH:mm"));
		} else if (jobCronSet.getFrequency().equals("5")) {
			trigger.setEndTime(DateUtil.string2SqlTimestamp("2099-01-01 00:00",
					"yyyy-MM-dd HH:mm"));
		} else {
			trigger.setEndTime(DateUtil.string2SqlTimestamp(
					jobCronSet.getEndDay(), "yyyy-MM-dd HH:mm"));
		}

		jobCronSet.setJobName(jobName);
		map.put("JOB_CRON_SET", jobCronSet);

		if (params != null) {
			Iterator<String> it = params.keySet().iterator();
			String mapKey = null;
			while (it.hasNext()) {
				mapKey = it.next();
				map.put(mapKey, params.get(mapKey));
			}
		}
		trigger.setVolatility(false);
		scheduler.scheduleJob(commonJobDetail, trigger);

	}

	public void scheduleUpdateJob(JobCronSet jobCronSet, String group,
			Map<String, Object> params) throws Exception {

		CronTrigger trigger = null;

		String jobId = jobCronSet.getJobName();
		commonJobDetail = scheduler.getJobDetail(jobId, group);
		trigger = (CronTrigger) scheduler.getTrigger(jobId, group);
		// trigger.setEndTime(null);

		// trigger.setStartTime(DateUtil.string2SqlTimestamp(jobCronSet.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
		// if(jobCronSet.getNever().equals("1")){
		// trigger.setEndTime(DateUtil.string2SqlTimestamp("2099-01-01 00:00:00","yyyy-MM-dd HH:mm:ss"));
		// }else{
		// trigger.setEndTime(DateUtil.string2SqlTimestamp(jobCronSet.getEndDate(),"yyyy-MM-dd HH:mm:ss"));
		// }
		boolean isExist = true;
		if (trigger == null) {
			isExist = false;
			trigger = new CronTrigger(jobId, group, jobId, group);
		}
		trigger.setCronExpression(CronParser.getCronExpress(jobCronSet));
		if (jobCronSet.getFrequency().equals("0")) {
			trigger.setStartTime(DateUtil.string2SqlTimestamp(DateUtil
					.date2FormattedString(new Date(), "yyyy-MM-dd HH:mm"),
					"yyyy-MM-dd HH:mm"));
		} else if (jobCronSet.getFrequency().equals("5")) {
			trigger.setStartTime(DateUtil.string2SqlTimestamp(
					jobCronSet.getTimeStr(), "yyyy-MM-dd HH:mm"));
		} else {
			trigger.setStartTime(DateUtil.string2SqlTimestamp(
					jobCronSet.getDateOfStart(), "yyyy-MM-dd HH:mm"));
		}

		if (jobCronSet.getNever().equals("1")) {
			trigger.setEndTime(DateUtil.string2SqlTimestamp("2099-01-01 00:00",
					"yyyy-MM-dd HH:mm"));
		} else if (jobCronSet.getFrequency().equals("0")) {
			trigger.setEndTime(DateUtil.string2SqlTimestamp(DateUtil
					.date2FormattedString(new Date(), "yyyy-MM-dd HH:mm"),
					"yyyy-MM-dd HH:mm"));
		} else if (jobCronSet.getFrequency().equals("5")) {
			trigger.setEndTime(DateUtil.string2SqlTimestamp("2099-01-01 00:00",
					"yyyy-MM-dd HH:mm"));
		} else {
			trigger.setEndTime(DateUtil.string2SqlTimestamp(
					jobCronSet.getEndDay(), "yyyy-MM-dd HH:mm"));
		}
		// trigger.setVolatility(false);
		if (isExist) {
			scheduler.rescheduleJob(jobId, group, trigger);
		} else {
			scheduler.scheduleJob(trigger);
		}

		// scheduler.rescheduleJob(jobId, group, trigger);
		commonJobDetail.getJobDataMap().put("JOB_CRON_SET", jobCronSet);
		if (params != null) {
			Iterator<String> it = params.keySet().iterator();
			String mapKey = null;
			while (it.hasNext()) {
				mapKey = it.next();
				commonJobDetail.getJobDataMap().put(mapKey, params.get(mapKey));
			}
		}
		scheduler.addJob(commonJobDetail, true);

	}

	/**
	 * 新增job
	 */
	public void scheduleAddJob(JobCronSet jobCronSet) throws Exception {
		if (!StringUtils.isEmpty(jobCronSet.getGroup())) {
			scheduleAddJob(jobCronSet, jobCronSet.getGroup(), null);
		} else {
			scheduleAddJob(jobCronSet, Scheduler.DEFAULT_GROUP, null);
		}
	}

	/**
	 * 更新Job
	 */
	public void scheduleUpdateJob(JobCronSet jobCronSet) throws Exception {
		if (!StringUtils.isEmpty(jobCronSet.getGroup())) {
			scheduleUpdateJob(jobCronSet, jobCronSet.getGroup(), null);
		} else {
			scheduleUpdateJob(jobCronSet, Scheduler.DEFAULT_GROUP, null);
		}
	}

	public void scheduleAddJob(JobCronSet jobCronSet, Map<String, Object> params)
			throws Exception {
		if (!StringUtils.isEmpty(jobCronSet.getGroup())) {
			scheduleAddJob(jobCronSet, jobCronSet.getGroup(), params);
		} else {
			scheduleAddJob(jobCronSet, Scheduler.DEFAULT_GROUP, params);
		}
	}

	/**
	 * 更新Job
	 */
	public void scheduleUpdateJob(JobCronSet jobCronSet,
			Map<String, Object> params) throws Exception {
		if (!StringUtils.isEmpty(jobCronSet.getGroup())) {
			scheduleUpdateJob(jobCronSet, jobCronSet.getGroup(), params);
		} else {
			scheduleUpdateJob(jobCronSet, Scheduler.DEFAULT_GROUP, params);
		}
	}

	public JobDetail getJobDetail(String jobName, String group)
			throws Exception {

		JobDetail job = scheduler.getJobDetail(jobName, group);
		return job;
	}

	public void deleteJob(String jobName, String group) throws Exception {
		scheduler.deleteJob(jobName, group);
	}

}
