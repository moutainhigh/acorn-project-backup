package com.chinadrtv.scheduler.service.quartz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.scheduler.service.SchedulerService;

/**
 * 
 * @author xieen
 * @version $Id: QuartzService.java, v 0.1 2013-7-23 下午8:52:07 xieen Exp $
 */
public class QuartzSchedulerService {

    //private Logger           logger              = LoggerFactory
    //                                                 .getLogger(LOG_TYPE.PAFF_SERVICE.val);

    //TOPIC运行状态码
    private final int        TOPIC_STATUS_START  = 1;
    private final int        TOPIC_STATUS_STOP   = 0;
    private final int        TOPIC_STATUS_REPEAT = 2;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private Scheduler        scheduler;

    /**
     * 获取服务器上运行job状态信息
     * 
     * @return
     */
    public List<HashMap<String, String>> checkQuartzJob() throws SchedulerException {
        List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

        com.chinadrtv.scheduler.service.model.Scheduler s = new com.chinadrtv.scheduler.service.model.Scheduler();
        s.setJobStatus(TOPIC_STATUS_START);
        List<com.chinadrtv.scheduler.service.model.Scheduler> skduList = this.schedulerService
            .getList(s);

        for (com.chinadrtv.scheduler.service.model.Scheduler skdu : skduList) {
            JobKey jobKey = new JobKey(String.valueOf(skdu.getId()));
            TriggerKey triggerKey = new TriggerKey(skdu.getJobTopic() + skdu.getId());

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", String.valueOf(skdu.getId()));
            map.put("jobName", skdu.getJobName());
            map.put("jobCronExpression", skdu.getJobCronExpression());
            map.put("jobTopic", skdu.getJobTopic());
            map.put("jobSystem", skdu.getJobSystem());
            map.put("jobStyle", skdu.getJobStyle());
            if (this.scheduler.checkExists(triggerKey) && this.scheduler.checkExists(jobKey)) {
                map.put("jobStatus", String.valueOf(TOPIC_STATUS_START));
            } else {
                map.put("jobStatus", String.valueOf(TOPIC_STATUS_STOP));
            }
            resultList.add(map);
        }

        return resultList;
    }

    /**
     * 接受配置页面消息处理
     * @param id
     * @param signal
     */
    public void operateMqSchedulerMassege(int id, int signal) {
        com.chinadrtv.scheduler.service.model.Scheduler skdu = (com.chinadrtv.scheduler.service.model.Scheduler) schedulerService
            .getObjectByPK(id);

        if (skdu == null) {
            //logger.warn("[id:" + id + ",signal:" + signal + "]"
            //            + "Scheduler info is not exists!!!! The command is invalid, drop it.");
            return;
        }

        if (TOPIC_STATUS_START == signal) { //start job
            if (createSchedulerJob(skdu)) {
                //logger.info("add quartz job success,[id:" + id + ",signal:" + signal + "]");
            } else {
                //logger.info("add quartz job failed,[id:" + id + ",signal:" + signal + "]");
            }
        } else if(TOPIC_STATUS_STOP == signal){ //stop job
            if (deleteSchedulerJob(skdu)) {
                //logger.info("stop quartz job success,[id:" + id + ",signal:" + signal + "]");
            } else {
                //logger.info("stop quartz job failed,[id:" + id + ",signal:" + signal + "]");
            }
        }else if(TOPIC_STATUS_REPEAT == signal){//repeat job
            if(repeatSchedulerJob(skdu)){
                //logger.info("repeat quartz job success,[id:" + id + ",signal:" + signal + "]");
            }else{
                //logger.info("repeat quartz job failed,[id:" + id + ",signal:" + signal + "]");
            }
        }
    }
    
    private boolean repeatSchedulerJob(com.chinadrtv.scheduler.service.model.Scheduler skdu){
        try{
            JobKey jobKey = new JobKey(String.valueOf(skdu.getId()));
            if(this.scheduler.checkExists(jobKey))
                this.scheduler.triggerJob(jobKey);
        }catch(Exception e){
            //logger.warn("Repeat scheduler job error", e);
            return false;
        }
        return true;
    }
    

    /**
     *  加载scheduler信息，新增JOB
     * 
     * @param skdu
     * @return
     */
    private boolean createSchedulerJob(com.chinadrtv.scheduler.service.model.Scheduler skdu) {
        try {
            JobDetail job = JobBuilder.newJob(QuartzJob.class)
                .withIdentity(String.valueOf(skdu.getId())).withDescription(skdu.getJobName())
                .build();

            CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
                .withIdentity(skdu.getJobTopic() + skdu.getId())
                .withSchedule(CronScheduleBuilder.cronSchedule(skdu.getJobCronExpression()))
                .build();

            if (!this.scheduler.checkExists(trigger.getKey())
                && !this.scheduler.checkExists(job.getKey()))
                this.scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            //logger.warn("Create scheduler job error", e);
            return false;
        }
        return true;
    }

    /**
     * 加载scheduler信息，删除运行JOB
     * 
     * @param skdu
     * @return
     */
    private boolean deleteSchedulerJob(com.chinadrtv.scheduler.service.model.Scheduler skdu) {
        try {
            String jobName = String.valueOf(skdu.getId());
            JobKey jobKey = new JobKey(jobName);
            JobDetail job = this.scheduler.getJobDetail(jobKey);
            if (job != null) {
                this.scheduler.deleteJob(jobKey);
            }
        } catch (Exception e) {
            //logger.warn("Delete scheduler job error", e);
            return false;
        }
        return true;
    }

    public void setSchedulerService(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
