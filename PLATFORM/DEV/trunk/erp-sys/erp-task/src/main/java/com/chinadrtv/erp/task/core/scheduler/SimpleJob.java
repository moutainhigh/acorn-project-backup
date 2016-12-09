package com.chinadrtv.erp.task.core.scheduler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.chinadrtv.erp.task.service.MailConfigure;
import com.chinadrtv.erp.task.service.TaskConfigure;
import com.chinadrtv.erp.task.util.MailBean;
import com.chinadrtv.erp.task.util.MailUtil;

@PersistJobDataAfterExecution  
@DisallowConcurrentExecution 
public abstract class SimpleJob implements Job{

	protected final Log logger = LogFactory.getLog(getClass());

	public static final String JOB_EXCEPTION_KEY = "JOB_EXCEPTION_KEY";
	public static final String JOB_SUCCESS_COUNT_KEY = "JOB_SUCCESS_COUNT_KEY";
	public static final String JOB_FAIL_KEY = "JOB_FAIL_KEY";
	public static final String JOB_PARMS = "JOB_PARMS";//用户定义参数key
	
	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";
	
	protected final static String EMAIL_SUFFIX = ";";
	
	protected ApplicationContext applicationContext = null;
	protected TaskConfigure taskConfigure;
	protected MailUtil mailUtil;
	protected MailConfigure mailConfigure;
	
	public abstract void doExecute(JobExecutionContext context) throws JobExecutionException;
	
	private void initBefore(JobExecutionContext context){
		try {
			applicationContext = (ApplicationContext)context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
    		mailUtil = (MailUtil) applicationContext.getBean(MailUtil.class);
    		taskConfigure = (TaskConfigure) applicationContext.getBean(TaskConfigure.class);
    		mailConfigure = (MailConfigure) applicationContext.getBean(MailConfigure.class);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void init(JobExecutionContext context){
		initBefore(context);
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		try {
			init(context);
			doExecute(context);
			int successCount = jobDataMap.getInt(JOB_SUCCESS_COUNT_KEY);
			jobDataMap.put(JOB_SUCCESS_COUNT_KEY, ++successCount);
			jobDataMap.put(JOB_EXCEPTION_KEY, "");
		} catch (Exception e) {
			StringWriter stringWriter = new StringWriter();
			PrintWriter writer = new PrintWriter(stringWriter);
			e.printStackTrace(writer);
			StringBuffer buffer = stringWriter.getBuffer();
			jobDataMap.put(JOB_EXCEPTION_KEY, buffer.toString());
			int failCount = jobDataMap.getInt(JOB_FAIL_KEY);
			jobDataMap.put(JOB_FAIL_KEY, ++failCount);
			logger.info("执行任务时产生了异常：" + this.getName());
			emailException(context, buffer.toString());
			e.printStackTrace();
		}

	}
	
	private void emailException(JobExecutionContext context, String ex){
		
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String taskName = context.getTrigger().getJobKey().getName();
		
		//获取接收邮件列表
		Set<String> emails = new HashSet<String>();
		String emailStr = taskConfigure.getExceptionRemindEmails();
		if(emailStr!=null && !"".equals(emailStr)){
			String[] es = emailStr.split(EMAIL_SUFFIX);
			for(String str : es){
				str = str.trim();
				if(!"".equals(str)){
					emails.add(str);
				}
			}
		}
		
		if(emails.size()>0){
			String day = sdf.format(new Date());
	        //创建邮件  
	        MailBean mailBean = new MailBean();  
	        mailBean.setFrom(mailConfigure.getMailFrom());  
	        mailBean.setSubject("定时任务异常警报:" + taskName);  
	        mailBean.setToEmails(emails.toArray(new String[emails.size()]));  
	        mailBean.setTemplate("任务名：${taskName} <br/> 时间：${time} <br/> 服务器IP：${ip} <br/> 异常：${ex}");
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("taskName", taskName); 
	        map.put("time", day); 
	        map.put("ip", addr.getHostAddress().toString());  
	        map.put("ex", ex);
	        mailBean.setData(map);  
	          
	        //发送邮件  
	        try {  
	            mailUtil.send(mailBean);  
	        } catch (MessagingException e) {  
	            e.printStackTrace();  
	        }  
		}	
		
	}
	
	public String getName() {
		return this.getClass().getSimpleName();
	}

}
