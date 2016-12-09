package com.chinadrtv.erp.task.jobs.demo1;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;

/**
 * 本实例为最简单的定时任务实例，模拟了发生异常时系统的处理
 * @author zhangguosheng
 */
@Task(name="Demo1QuartzJob", description="测试抛出异常")
public class Demo1QuartzJob extends SimpleJob{
    
    @Override
	public void doExecute(JobExecutionContext context) throws JobExecutionException{
    	logger.info("这是Demo1QuartzJob测试程序输出的。");
    	throw new JobExecutionException("测试抛出异常,此条为测试信息。");
	}

}
