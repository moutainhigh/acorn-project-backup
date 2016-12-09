package com.chinadrtv.scheduler.service.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinadrtv.common.log.LOG_TYPE;
import com.chinadrtv.common.spring.context.SpringContextHolder;
import com.chinadrtv.runtime.jms.send.JmsQueueSender;
import com.chinadrtv.runtime.jms.send.JmsTopicSender;
import com.chinadrtv.scheduler.service.SchedulerService;
import com.chinadrtv.scheduler.service.model.Scheduler;
import com.chinadrtv.util.XmlUtil;

/**
 * 
 * @author xieen
 * @version $Id: QuartzJob.java, v 0.1 2013-7-23 下午8:51:29 xieen Exp $
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJob implements Job {

    //private Logger logger = LoggerFactory.getLogger(LOG_TYPE.PAFF_SERVICE.val);

    /** 
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        JmsTopicSender jmsTopicSender = (JmsTopicSender) SpringContextHolder
            .getBean("jmsTopicSender");
        JmsQueueSender jmsQueueSender = (JmsQueueSender) SpringContextHolder
            .getBean("jmsQueueSender");
        SchedulerService schedulerService = (SchedulerService) SpringContextHolder
            .getBean("schedulerService");

        String id = context.getJobDetail().getKey().getName();

        Scheduler skdu = (Scheduler) schedulerService.getObjectByPK(Integer.parseInt(id));

        if (skdu.getJobStyle().equals("0")) {
            jmsTopicSender.sendConvertMessage(skdu.getJobTopic(), XmlUtil.toXml(skdu));
        } else {
            jmsQueueSender.sendConvertMessage(skdu.getJobTopic(), XmlUtil.toXml(skdu));
        }
    }

}
