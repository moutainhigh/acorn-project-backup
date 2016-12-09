package com.chinadrtv.erp.task.jobs.urgent;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.chinadrtv.erp.task.core.job.Task;
import com.chinadrtv.erp.task.core.scheduler.SimpleJob;
import com.chinadrtv.erp.task.entity.OrderUrgentApplication;
import com.chinadrtv.erp.task.entity.SysMessage;
import com.chinadrtv.erp.task.service.OrderUrgentApplicationService;
import com.chinadrtv.erp.task.service.SysMessageService;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
@Task(name="UrgentOrderJob", description="订单催送货定时处理")
public class UrgentOrderJob extends SimpleJob {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UrgentOrderJob.class);

    private OrderUrgentApplicationService orderUrgentApplicationService;

    private SysMessageService sysMessageService;

    @Override
    public void doExecute(JobExecutionContext context) throws JobExecutionException {
        //首先读取要检查的催送货订单（目前就只处理已读的消息）
        //然后读取这些订单的催送货是否完成，已经完成的，把相应的消息打成完成状态
        logger.info("begin urgent order message search...");
        List<SysMessage> sysMessageList=sysMessageService.queryUrgentMessage();
        if(sysMessageList!=null&&sysMessageList.size()>0)
        {
            logger.info("urgent order message search- message size:"+sysMessageList.size());

            List<String> orderIdList=new ArrayList<String>();
            for(SysMessage sysMessage:sysMessageList)
            {
                if(StringUtils.isNotEmpty(sysMessage.getContent()))
                {
                    if(!orderIdList.contains(sysMessage.getContent()))
                    {
                        orderIdList.add(sysMessage.getContent());
                    }
                }
            }
            if(orderIdList.size()>0)
            {
                logger.info("urgent order message search - order size:"+orderIdList.size());

                List<OrderUrgentApplication> orderUrgentApplicationList=orderUrgentApplicationService.getHandledByOrderIds(orderIdList);
                if(orderUrgentApplicationList!=null&&orderUrgentApplicationList.size()>0)
                {
                    logger.info("urgent order message search - urgent order size:"+orderUrgentApplicationList.size());

                    List<SysMessage> finishSysMessageList=sysMessageService.checkUrgentMessage(sysMessageList,orderUrgentApplicationList);
                    if(finishSysMessageList!=null&&finishSysMessageList.size()>0)
                    {
                        logger.info("match urgent order message size:"+finishSysMessageList.size());
                        sysMessageService.updateUrgentMessage(finishSysMessageList);
                        //获取用户，最后刷新
                        logger.info("updated match urgent order message");
                        List<String> usrIdList=this.getUsrIdsFromMessage(finishSysMessageList);
                        logger.info("match urgent order message user size:"+usrIdList.size());
                        sysMessageService.refreshUrgentMessage(usrIdList);
                        logger.info("updated user urgent order message cache");
                    }
                }
            }
        }
    }

    private List<String> getUsrIdsFromMessage(List<SysMessage> sysMessageList)
    {
        List<String> usrIdList=new ArrayList<String>();
        for(SysMessage sysMessage:sysMessageList)
        {
            if(StringUtils.isNotEmpty(sysMessage.getReceiverId()))
            {
                if(!usrIdList.contains(sysMessage.getReceiverId()))
                {
                    usrIdList.add(sysMessage.getReceiverId());
                }
            }
        }
        return usrIdList;
    }

    @Override
    public void init(JobExecutionContext context) {
        super.init(context);
        if(applicationContext!=null){
            orderUrgentApplicationService = (OrderUrgentApplicationService) applicationContext.getBean(OrderUrgentApplicationService.class);
            sysMessageService = (SysMessageService) applicationContext.getBean(SysMessageService.class);
        }
    }
}
