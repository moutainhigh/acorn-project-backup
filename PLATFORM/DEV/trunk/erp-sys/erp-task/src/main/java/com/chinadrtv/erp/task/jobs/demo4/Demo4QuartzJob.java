package com.chinadrtv.erp.task.jobs.demo4;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;

/**
 * 本实例展示了quartz如何集成batch，然后使用batch去批处理操作数据。
 * 由于还没有发现其应用的有点，所以不建议使用，本实例仅供参考。
 * @author zhangguosheng
 */
@Task(name="Demo4QuartzJob", description="测试BATCH批处理（只查数据库，没有写数据库）")
public class Demo4QuartzJob extends SimpleJob{
	
    private JobLauncher jobLauncher;
    
    private Job demo4BatchJob;
    
    @Override
	public void init(JobExecutionContext context) {
    	super.init(context);
    	if(applicationContext!=null){
    		jobLauncher = (JobLauncher) applicationContext.getBean(JobLauncher.class);
    		demo4BatchJob = (Job) applicationContext.getBean(Job.class);
    	}
	}

	@Override
	public void doExecute(JobExecutionContext context) throws JobExecutionException {
		logger.info("测试batch批量处理");
		Map<String,JobParameter> parameters = new HashMap<String,JobParameter>();
		parameters.put("RUN_MONTH_KEY",new JobParameter(new Date())); 
		try {
			jobLauncher.run(demo4BatchJob, new JobParameters(parameters));
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}

}
