package com.chinadrtv.erp.task.quartz.service;

import java.util.Date;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.chinadrtv.erp.task.common.TriggerType;
import com.chinadrtv.erp.task.core.exception.BizException;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.vo.PageVo;
import com.chinadrtv.erp.task.vo.SimpleJobClassVo;
import com.chinadrtv.erp.task.vo.TriggerVo;

public interface SchedulerService {
	
	/**
	 * 取得所有调度Triggers
	 * @return
	 */
	public PageVo<TriggerVo> getQrtzTriggers(int pageNo, int pageSize);
	
	/**
	 * 根据名称和组别暂停Tigger
	 * @param triggerName
	 * @param group
	 */
	public void pauseTrigger(String triggerName,String group);
	
	/**
	 * 恢复Trigger
	 * @param triggerName
	 * @param group
	 */
	public void resumeTrigger(String triggerName,String group);
	
	/**
	 * 删除Trigger
	 * @param triggerName
	 * @param group
	 */
	public boolean removeTrigger(String triggerName,String group);
	
	public void executeJob(String jobName, String groupName);
	
	public JobDetail getJobDetail(String jobName, String jobGroup);
	
	public Trigger getTrigger(String triggerName, String triggerGroup);
	
	/**
	 * 查询已在系统中注册过的Trigger
	 * @return
	 */
	public PageVo<SimpleJobClassVo> getRegisteredJob(int index, int size);
	
	/**
	 * 创建触发器
	 * @param type
	 * @param clazz
	 * @param triggerName
	 * @param jobName
	 * @param cronExpression
	 * @param startDelay
	 * @param loopDelay
	 * @param repeatCount
	 * @param description
	 * @param parms 
	 * @return
	 */
	public Date createTrigger(TriggerType type, Class<? extends SimpleJob> clazz, String triggerName, String jobName, 
			String cronExpression, Integer startDelay, Integer loopDelay, Integer repeatCount, String description, Map<String, String> parms) throws BizException;

	public Date updateTrigger(TriggerKey oldTriggerKey, JobKey oldJobKey, Trigger newTrigger, Class<? extends SimpleJob> clazz, String jobName, Map<String, String> parms) throws BizException;
}
