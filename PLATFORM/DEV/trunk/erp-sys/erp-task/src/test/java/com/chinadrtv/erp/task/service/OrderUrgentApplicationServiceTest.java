package com.chinadrtv.erp.task.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinadrtv.erp.task.core.test.TestSupport;
import com.chinadrtv.erp.task.entity.OrderUrgentApplication;
import com.chinadrtv.erp.task.entity.SysMessage;

/**
 * Created with (TC).
 * User: 徐志凯
 * Date: 13-8-28
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
public class OrderUrgentApplicationServiceTest extends TestSupport {
    @Autowired
    private OrderUrgentApplicationService orderUrgentApplicationService;

    @Autowired
    private SysMessageService sysMessageService;

   /* @Test
    public void testCache()
    {
        List<String> list=new ArrayList<String>();
        list.add("27430");
        List<String> list1=sysMessageService.getUsrUrgentMessage("27430");
        List<String> list2=sysMessageService.getUsrUrgentMessage("27430");
        System.out.println(list2.size());
        //sysMessageService.refreshUsrUrgentMessage("27430");
    }*/

    @Test
    //@Transactional
    public void testMok()
    {
        List<SysMessage> sysMessageList=new ArrayList<SysMessage>();
        SysMessage sysMessage=new SysMessage();
        sysMessage.setId(1L);
        sysMessageList.add(sysMessage);

        sysMessageService.updateUrgentMessage(sysMessageList);
    }

    @Test
    public void testReadUrgentMessage()
    {
        List<SysMessage> sysMessageList= sysMessageService.queryUrgentMessage();
        if(sysMessageList!=null)
        {
            System.out.println(sysMessageList.size());
        }
    }

    //@Test
    public void testService() {
        //首先读取要检查的催送货订单（目前就只处理已读的消息）
        //然后读取这些订单的催送货是否完成，已经完成的，把相应的消息打成完成状态
        List<SysMessage> sysMessageList=sysMessageService.queryUrgentMessage();
        if(sysMessageList!=null&&sysMessageList.size()>0)
        {
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
                List<OrderUrgentApplication> orderUrgentApplicationList=orderUrgentApplicationService.getHandledByOrderIds(orderIdList);
                if(orderUrgentApplicationList!=null&&orderUrgentApplicationList.size()>0)
                {
                    List<SysMessage> finishSysMessageList=sysMessageService.checkUrgentMessage(sysMessageList,orderUrgentApplicationList);
                    if(finishSysMessageList!=null&&finishSysMessageList.size()>0)
                    {
                        sysMessageService.updateUrgentMessage(finishSysMessageList);
                        //获取用户，最后刷新
                        List<String> usrIdList=this.getUsrIdsFromMessage(finishSysMessageList);
                        sysMessageService.refreshUrgentMessage(usrIdList);
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
}
