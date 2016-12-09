package com.chinadrtv.scheduler.service.quartz;

import java.text.MessageFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.plugins.history.LoggingJobHistoryPlugin;

import com.chinadrtv.common.spring.context.SpringContextHolder;
import com.chinadrtv.scheduler.service.JobHistoryService;
import com.chinadrtv.scheduler.service.SchedulerService;
import com.chinadrtv.scheduler.service.model.JobHistory;
import com.chinadrtv.scheduler.service.model.Scheduler;

/**
 * 
 * @author xieen
 * @version $Id: QuartzJobHistoryPlugin.java, v 0.1 2013-7-31 下午8:22:23 xieen Exp $
 */
public class QuartzJobHistoryPlugin extends LoggingJobHistoryPlugin {
    //JOB运行状态码
    private final int         JOB_STATUS_SUCCESS = 1;
    private final int         JOB_STATUS_FAILED  = 0;

    private JobHistoryService jobHistoryService;

    private SchedulerService  schedulerService;

    public QuartzJobHistoryPlugin() {
        super();
        init();
    }

    public void init() {
        jobHistoryService = (JobHistoryService) SpringContextHolder.getBean("jobHistoryService");
        schedulerService = (SchedulerService) SpringContextHolder.getBean("schedulerService");
    }

    /**
     * job 日志写库
     * 
     * @param context
     * @param message
     */
    private void createJobHistory(JobExecutionContext context, int status, String message) {
        String id = context.getJobDetail().getKey().getName();

        Scheduler skdu = (Scheduler) schedulerService.getObjectByPK(Integer.parseInt(id));

        JobHistory jobHistory = new JobHistory();
        jobHistory.setJobId(skdu.getId());
        jobHistory.setJobName(skdu.getJobName());
        jobHistory.setJobSystem(skdu.getJobSystem());
        jobHistory.setMessage(message);
        jobHistory.setJobStatus(status);
        jobHistory.setJobTime(new Date());

        this.jobHistoryService.addData(jobHistory);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

        Trigger trigger = context.getTrigger();

        Object[] args = null;

        if (jobException != null) {
            if (!getLog().isWarnEnabled()) {
                return;
            }

            String errMsg = jobException.getMessage();
            args = new Object[] { context.getJobDetail().getKey().getName(),
                    context.getJobDetail().getKey().getGroup(), new java.util.Date(),
                    trigger.getKey().getName(), trigger.getKey().getGroup(),
                    trigger.getPreviousFireTime(), trigger.getNextFireTime(),
                    Integer.valueOf(context.getRefireCount()), errMsg };

            getLog().warn(MessageFormat.format(getJobFailedMessage(), args), jobException);

            createJobHistory(context, JOB_STATUS_FAILED,
                MessageFormat.format(getJobFailedMessage(), args));
        } else {
            if (!getLog().isInfoEnabled()) {
                return;
            }

            String result = String.valueOf(context.getResult());
            args = new Object[] { context.getJobDetail().getKey().getName(),
                    context.getJobDetail().getKey().getGroup(), new java.util.Date(),
                    trigger.getKey().getName(), trigger.getKey().getGroup(),
                    trigger.getPreviousFireTime(), trigger.getNextFireTime(),
                    Integer.valueOf(context.getRefireCount()), result };

            getLog().info(MessageFormat.format(getJobSuccessMessage(), args));

            createJobHistory(context, JOB_STATUS_SUCCESS,
                MessageFormat.format(getJobSuccessMessage(), args));
        }
    }

}
