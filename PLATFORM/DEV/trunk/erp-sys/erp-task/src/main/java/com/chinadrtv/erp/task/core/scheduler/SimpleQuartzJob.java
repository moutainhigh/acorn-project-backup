package com.chinadrtv.erp.task.core.scheduler;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Deprecated
public abstract class SimpleQuartzJob extends QuartzJobBean{
	
	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected ApplicationContext applicationContext = null;

	private String ex = "";

	public abstract void doExecute(JobExecutionContext context);
	public void init(JobExecutionContext context){}
	
	private void initBefore(JobExecutionContext context){
		try {
			applicationContext = (ApplicationContext)context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		ex = "没有产生异常";
		try {
			initBefore(context);
			init(context);
			doExecute(context);
			logger.info("执行了任务：" + this.getName());
		} catch (Exception e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(stringWriter);
			e.printStackTrace(writer);
			StringBuffer buffer = stringWriter.getBuffer();
			ex = buffer.toString();
			logger.info("执行任务时产生了异常：" + this.getName());
			e.printStackTrace();
		}
//		context.getTrigger().getJobDataMap().put("successCount", successCount);
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	public String getEx() {
		return ex;
	}
	public void setEx(String ex) {
		this.ex = ex;
	}
	
}
