package com.chinadrtv.erp.task.quartz.service;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.task.common.TriggerType;
import com.chinadrtv.erp.task.core.exception.BizException;
import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.quartz.dao.QuartzDao;
import com.chinadrtv.erp.task.util.ClassFilter;
import com.chinadrtv.erp.task.util.ClassScanUtils;
import com.chinadrtv.erp.task.vo.PageVo;
import com.chinadrtv.erp.task.vo.SimpleJobClassVo;
import com.chinadrtv.erp.task.vo.TriggerVo;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public final static String USER_CREATE_GROUP = "DEFAULT";
	
	@Autowired
	private QuartzDao quartzDao;
	
	@Autowired
	private SchedulerFactoryBean quartzScheduler;
	
	private static Set<Class<?>> taskClassList;
	
	static{
		ClassFilter filter = new ClassFilter() {
			@Override
			public boolean accept(Class<?> clazz) {
				boolean b = clazz.isAnnotationPresent(Task.class) 
						&& SimpleJob.class.isAssignableFrom(clazz) 
						&& Modifier.isPublic(clazz.getModifiers()) 
						&& !Modifier.isAbstract(clazz.getModifiers()) 
						&& !Modifier.isInterface(clazz.getModifiers()) 
						&& !Modifier.isStatic(clazz.getModifiers());
				return  b;
			}
		};
		taskClassList = ClassScanUtils.scanPackage("com.chinadrtv.erp.task.jobs", filter);
	}
	
	@Override
	public PageVo<TriggerVo> getQrtzTriggers(int pageNo, int pageSize){
		PageVo<TriggerVo> page = quartzDao.getQrtzTriggers(pageNo, pageSize);
		for(TriggerVo vo : page.getRows()){
			Trigger trigger = this.getTrigger(vo.getTriggerName(), vo.getTriggerGroup());
			if(trigger!=null){
				JobDetail jobDetail = this.getJobDetail(trigger.getJobKey().getName(), trigger.getJobKey().getGroup());
				if(jobDetail!=null){
					JobDataMap jobDataMap = jobDetail.getJobDataMap();
					int successCount = jobDataMap.getInt(SimpleJob.JOB_SUCCESS_COUNT_KEY);
					int failCount = jobDataMap.getInt(SimpleJob.JOB_FAIL_KEY);
					@SuppressWarnings("unchecked")
					Map<String, String> parms = (Map<String, String>) jobDataMap.get(SimpleJob.JOB_PARMS);
					vo.setSuccessCount(successCount);
					vo.setFailCount(failCount);
					vo.setJobClass(jobDetail.getJobClass().getName());
					vo.setParms(parms);	
				}
			}
		}
		return page;
	}
	
	@Override
	public void pauseTrigger(String triggerName,String group){		
		try {
			TriggerKey key = new TriggerKey(triggerName, group);
			quartzScheduler.getScheduler().pauseTrigger(key);//停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void resumeTrigger(String triggerName,String group){		
		try {
			TriggerKey key = new TriggerKey(triggerName, group);
			quartzScheduler.getScheduler().resumeTrigger(key);//重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean removeTrigger(String triggerName,String group){		
		try {
			TriggerKey key = new TriggerKey(triggerName, group);
			quartzScheduler.getScheduler().pauseTrigger(key);//停止触发器
			return quartzScheduler.getScheduler().unscheduleJob(key);//移除触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void executeJob(String jobName, String groupName) {
		try {
			JobKey jobKey = new JobKey(jobName,groupName);
			quartzScheduler.getScheduler().triggerJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public JobDetail getJobDetail(String jobName, String jobGroup) {
		JobDetail jobDetail = null;
		try {
			JobKey jobKey = new JobKey(jobName,jobGroup);
			jobDetail = quartzScheduler.getScheduler().getJobDetail(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return jobDetail;
	}

	@Override
	public Trigger getTrigger(String triggerName, String triggerGroup) {
		Trigger trigger = null;
		if(triggerName!=null && triggerGroup!=null){
			TriggerKey key = new TriggerKey(triggerName, triggerGroup);
			try {
				trigger = quartzScheduler.getScheduler().getTrigger(key);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}	
		}
		return trigger;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageVo<SimpleJobClassVo> getRegisteredJob(int pageNo, int pageSize) {
		List<SimpleJobClassVo> sjcList = new ArrayList<SimpleJobClassVo>();
		for (Class<?> clazz : taskClassList) {
			Task task = clazz.getAnnotation(Task.class);
			sjcList.add(new SimpleJobClassVo(task.name(), (Class<? extends SimpleJob>) clazz, task.description()));
		}
		
		//排序
		Collections.sort(sjcList, new Comparator<SimpleJobClassVo>() {
			@Override
			public int compare(SimpleJobClassVo o1, SimpleJobClassVo o2) {
				Task task1 = o1.getClazz().getAnnotation(Task.class);
				Task task2 = o2.getClazz().getAnnotation(Task.class);
				return task1.name().compareTo(task2.name());
			}
		});
        
		List<SimpleJobClassVo> list = new ArrayList<SimpleJobClassVo>();
		for(int i=(pageNo-1)*pageSize; (i<pageNo*pageSize) && i<sjcList.size(); i++){
			list.add(sjcList.get(i));
		}
		return new PageVo<SimpleJobClassVo>(list, sjcList.size());
	}

	@Override
	public Date createTrigger(TriggerType type, Class<? extends SimpleJob> clazz, String triggerName, String jobName, 
			String cronExpression, Integer startDelay, Integer loopDelay, Integer repeatCount, String description, Map<String, String> parms) throws BizException {

		if(triggerName==null || jobName==null){
			return null;
		}
		Date date = null;
		JobBuilder  jobBuilder = JobBuilder.newJob(clazz);
		jobBuilder.withIdentity(jobName, USER_CREATE_GROUP);
		JobDetail job = jobBuilder.build();
		job.getJobDataMap().put(SimpleJob.JOB_EXCEPTION_KEY, "");
		job.getJobDataMap().put(SimpleJob.JOB_SUCCESS_COUNT_KEY, 0);
		job.getJobDataMap().put(SimpleJob.JOB_FAIL_KEY, 0);
		job.getJobDataMap().put(SimpleJob.JOB_PARMS, parms);
		
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		triggerBuilder.withIdentity(triggerName, USER_CREATE_GROUP);
		if(startDelay==null){
			startDelay = 0;
		}
		Date startTime = DateBuilder.nextGivenSecondDate(new Date(),startDelay);//设置延时
		triggerBuilder.startAt(startTime);
//		triggerBuilder.endAt(triggerEndTime);//结束时间
		
		Trigger trigger = null;
		
		if(TriggerType.CRON == type){
			if(cronExpression==null){
				return null;
			}
			CronScheduleBuilder csb = null;
			try {
				csb = CronScheduleBuilder.cronSchedule(cronExpression);
			} catch (Exception e) {
				throw new BizException("表达式错误！");
			}
			triggerBuilder.withSchedule(csb);
			CronTriggerImpl cronTriggerImpl = (CronTriggerImpl)triggerBuilder.build();
			cronTriggerImpl.setDescription(description);
			trigger = cronTriggerImpl;
		}else if(TriggerType.SIMPLE == type){
			if(loopDelay==null){
				return null;
			}
			SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule();
			ssb.withIntervalInSeconds(loopDelay);//x秒运行一次
			if(repeatCount==null){
				ssb.repeatForever();
			}else{
				ssb.withRepeatCount(repeatCount);
			}
			triggerBuilder.withSchedule(ssb);
			SimpleTriggerImpl simpleTriggerImpl = (SimpleTriggerImpl)triggerBuilder.build();
			simpleTriggerImpl.setDescription(description);
			trigger = simpleTriggerImpl;
		}else{
			return null;
		}

		try {
			date = quartzScheduler.getScheduler().scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new BizException(e.getMessage());
		}
		return date;
	}

	@Override
	public Date updateTrigger(TriggerKey oldTriggerKey, JobKey oldJobKey, Trigger newTrigger, Class<? extends SimpleJob> clazz, String jobName, Map<String, String> parms) throws BizException{

		if(oldJobKey==null && newTrigger==null && clazz==null || jobName==null){
			return null;
		}
		Date date = null;
		JobBuilder  jobBuilder = JobBuilder.newJob(clazz);
		jobBuilder.withIdentity(jobName, USER_CREATE_GROUP);
		JobDetail job = jobBuilder.build();
		job.getJobDataMap().put(SimpleJob.JOB_EXCEPTION_KEY, "");
		job.getJobDataMap().put(SimpleJob.JOB_SUCCESS_COUNT_KEY, 0);
		job.getJobDataMap().put(SimpleJob.JOB_FAIL_KEY, 0);
		job.getJobDataMap().put(SimpleJob.JOB_PARMS, parms);
		
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		triggerBuilder.withIdentity(newTrigger.getKey().getName(), newTrigger.getKey().getGroup());
		
		Trigger trigger = null;
		
		if(newTrigger instanceof CronTriggerImpl){
			CronTriggerImpl impl = (CronTriggerImpl) newTrigger;
			CronScheduleBuilder csb = null;
			try {
				csb = CronScheduleBuilder.cronSchedule(impl.getCronExpression());
			} catch (Exception e) {
				throw new BizException("表达式错误！");
			}
			triggerBuilder.withSchedule(csb);
			CronTriggerImpl cronTriggerImpl = (CronTriggerImpl)triggerBuilder.build();
			cronTriggerImpl.setDescription(newTrigger.getDescription());
			trigger = cronTriggerImpl;
		}else if(newTrigger instanceof SimpleTriggerImpl){
			SimpleTriggerImpl impl = (SimpleTriggerImpl) newTrigger;
			SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule();
			ssb.withIntervalInSeconds((int) impl.getRepeatInterval());//x秒运行一次
			if(impl.getRepeatCount()==0){
				ssb.repeatForever();
			}else{
				ssb.withRepeatCount(impl.getRepeatCount());
			}
			triggerBuilder.withSchedule(ssb);
			SimpleTriggerImpl simpleTriggerImpl = (SimpleTriggerImpl)triggerBuilder.build();
			simpleTriggerImpl.setDescription(impl.getDescription());
			trigger = simpleTriggerImpl;
		}else{
			return null;
		}
		
		try {
			quartzScheduler.getScheduler().pauseTrigger(oldTriggerKey);//停止触发器
			quartzScheduler.getScheduler().unscheduleJob(oldTriggerKey);//移除触发器
			quartzScheduler.getScheduler().deleteJob(oldJobKey);// 移除当前进程的Job 
			date = quartzScheduler.getScheduler().scheduleJob(job, trigger); // 重新调度jobDetail 
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new BizException(e.getMessage());
		}
		return date;
	}

}
